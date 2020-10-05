package Task2;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * A mediator class used to assemble a NetAnalyser and conduct communications between three panel classes: inputPanel, outputPanel, histogramPanel.
 * Two public fields: url and probeNum, are both for internal configuration and available for external access (for expansibility).
 * Communication methods and event handler methods are encapsulated in this class.
 * @author wangyunkun
 * @version 2.0
 */
public class NetAnalyserFrameV2 extends JFrame {
    protected InputPanel inputPanel;//initializing a inputPanel requires a param of maxProbeNum, so it is initialized later in the constructor.
    protected OutputPanel outputPanel = new OutputPanel();
    protected HistogramPanel histogramPanel = new HistogramPanel();
    public String url;
    public int probeNum;

    /**
     * Default constructor that construct a NetAnalyserFrameV2 with default maxProbeNum=10.
     */
    public NetAnalyserFrameV2(){
        this(10);
    }

    /**
     * Construct a NetAnalyserFrameV2.
     * @param maxProbeNum max value of probe number.
     */
    public NetAnalyserFrameV2(int maxProbeNum){
        this.setTitle("NetAnalyser V2.0");
        this.setPreferredSize(new Dimension(1400,240));
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new GridLayout(1,3,10,10));
        //construct a inputPanel Object with given maxProbeNum
        this.inputPanel = new InputPanel(maxProbeNum);
        //give probeNum a default value 1, just in case user uses the combobox's default value 1 and doesn't trigger the action listener
        this.probeNum = (int)inputPanel.probeNumBox.getSelectedItem();
        //add listeners to each input components
        addUrlListener();
        addProbeNumListener();
        addBtnListener();
        //put these panels into the frame
        contentPane.add(inputPanel);
        contentPane.add(outputPanel);
        contentPane.add(histogramPanel);
        //configure the frame
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(getOwner());
        this.setVisible(true);
    }

    /**
     * Add a listener implementing DocumentListener into the url JTextField.
     * Any insert,remove,change events in the text will trigger an assignment of the current value to the url field.
     */
    protected void addUrlListener(){
        this.inputPanel.urlText.getDocument().addDocumentListener(new UrlListener(this));
    }

    /**
     * Add a listener implementing ActionListener into the probeNumBox comboBox.
     * Any selection event will trigger an assignment of selected number value to the probeNum field.
     */
    protected void addProbeNumListener(){
        this.inputPanel.probeNumBox.addActionListener(new ProbeNumListener(this));
    }

    /**
     * Add a listener implementing ActionListener into the processBtn JButton.
     * Any clicking button event will trigger a series of event handler.
     */
    protected void addBtnListener(){
        this.inputPanel.processBtn.addActionListener(new BtnListener(this));
    }

    /**
     * Handle the clicking button events.
     * Collect and analyze the input, run Ping and print the outputs if inputs are valid.
     * @throws IOException Error reading the raw output from command line and writing the records into txt files.
     * @throws InterruptedException Thread interrupted.
     */
    protected void btnClickHandler() throws IOException, InterruptedException {
        //run Ping
        BufferedReader reader = runPing();
        //analyze the raw output and show results in outputPanel and histogramPanel
        showOutputNHistogram(reader);
    }

    /**
     * Run the Ping request.
     * @return A BufferedReader that contains all the raw output.
     * @throws IOException Error reading the raw output from command line.
     * @throws InterruptedException Thread interrupted.
     */
    private BufferedReader runPing() throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec("ping -c "+this.probeNum+" "+this.url);
        p.waitFor();
        return new BufferedReader(new InputStreamReader(p.getInputStream()));
    }

    /**
     * Clear the outputPanel.
     */
    private void clearOutputText(){
        this.outputPanel.outputText.setText(null);
    }

    /**
     * Show raw output and histogram on corresponding panels.
     * @param reader BufferedReader that contains the raw output
     * @throws IOException Error writing the records into txt files
     */
    private void showOutputNHistogram(BufferedReader reader) throws IOException {
        //clear the previous text on the panel
        clearOutputText();
        //used to record each packets' time value
        ArrayList<Float> timeList = new ArrayList<>();
        //used to records the calculated RTT intervals values
        float[] intervalArr = new float[4];
        //start to read the raw output line by line
        String str = reader.readLine();
        while(str!=null){
            //locate the received packets' results lines, locate the time value, and extract the value, store them in timeList
            if(str.contains("time=")){
                int startPos = str.indexOf("time=")+5;
                timeList.add(Float.parseFloat(str.substring(startPos,str.indexOf(" ",startPos))));
            }
            //locate the last line where max and min RTT values are at, and extract min and max time value，calculate the binSize
            if(str.contains("round-trip")){
                String[] strings = str.split(" ");
                String[] values = strings[3].split("/");
                float minVal = Float.parseFloat(values[0]);
                float maxVal = Float.parseFloat(values[2]);
                float binSize = (maxVal-minVal)/3;
                binSize = (float) (Math.round(binSize*1000)*0.001);
                //store the intervals data in intervalArr
                intervalArr[0] = minVal;
                intervalArr[1] = minVal+binSize;
                intervalArr[2] = minVal+binSize*2;
                intervalArr[3] = maxVal;
            }
            //print the raw output on outputPanel line by line
            String prevText = this.outputPanel.outputText.getText();
            this.outputPanel.outputText.setText(prevText+"\n"+str);
            str = reader.readLine();
        }
        this.histogramPanel.setUpIntervals(intervalArr);
        //calculate number of RTT mark to show in each interval in the histogramPanel
        int[] markNumArr = calMarkNum(intervalArr,timeList);
        showMark(markNumArr);
        exportToFile(intervalArr,markNumArr);

    }

    /**
     * Calculate how many packets marks to insert into each intervals
     * @param intervalArr contains each interval's endpoints
     * @param timeList contains all the received packets time
     * @return an array containing marks number for each RTT intervals
     */
    private int[] calMarkNum(float[] intervalArr,ArrayList<Float> timeList){
        int[] markNumArr = new int[3];
        for(float RTT : timeList){
            for(int i=0; i<3; i++){
                if(RTT<intervalArr[i+1]){
                    markNumArr[i]++;
                    break;
                }
            }
        }
        //also count the max value RTT mark to the third interval, which is not covered in the above cycle.
        if(!timeList.isEmpty())
            markNumArr[2]++;

        return markNumArr;
    }

    /**
     * Configure the RTT marks in the histogram.
     * @param markNumArr contains marks to add in each RTT interval.
     */
    private void showMark(int[] markNumArr){
        this.histogramPanel.setUpMarks(maxVal(markNumArr),markNumArr);
    }

    /**
     * Calculate the column number in the histogram
     * @param arr a set of integer values.
     * @return the maximum value among the items.
     */
    private int maxVal(int[] arr){
        int max=0;
        for(int e : arr)
            if(e>max)
                max=e;
        return max;
    }

    /**
     * Export the records of the outcome to a txt file.
     * The file name is in the format of "url-year-month-day-hour-minute-second".
     * @param intervalArr contains RTT intervals endpoints values.
     * @param markNumArr contains the number of packets received in each interval.
     * @throws IOException Error writing the records into txt files.
     */
    private void exportToFile(float[] intervalArr, int[] markNumArr) throws IOException {
        //transfer the url to a valid format for a file name
        String urlStr = this.url.replace(".","-");
        String timeStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss"));
        //open a stream to the txt file
        File file = new File(""+urlStr+"-"+timeStr+".txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        //start writing content into the file
        bw.write(file.getName()+"\n"+"\n");
        bw.write("RTT(ms) histogram"+"\n");
        //regulate the format of time endpoints to keep three decimal places
        DecimalFormat df = new DecimalFormat("#.000");
        for(int i=0;i<3;i++){
            bw.write(""+df.format(intervalArr[i]) + "-" + df.format(intervalArr[i+1]) + ":" + markNumArr[i] + "\n");
        }
        bw.close();
    }
}
