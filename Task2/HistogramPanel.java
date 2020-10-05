package Task2;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * A component class used to configure the histogram panel in the NetAnalyser_V3 frame.
 * It contains three fields titleLabel, intervalLabels, markPanels, in the purpose of providing interface for the frame configuration.
 * This panel is composed of a titleLabel and two sub-panel: intervalsPanel, frequencyPanel, both of which each self has three sub-components(intervalLabels and markPanels).
 * And the protected init methods are for expansibility.
 */
public class HistogramPanel extends JPanel {
    public JLabel titleLabel = new JLabel("Histogram",SwingConstants.CENTER);
    public JLabel[] intervalLabels = new JLabel[3];
    public JPanel[] markPanels = new JPanel[3];

    /**
     * Constructor of HistogramPanel
     */
    public HistogramPanel(){
        //initialize a JPanel to hold three RTT intervals vertically
        JPanel intervalsPanel = new JPanel(new GridLayout(3,1));
        for(int i=0;i<3;i++){
            intervalLabels[i] = new JLabel();
            intervalsPanel.add(intervalLabels[i]);
        }
        //initialize a JPanel to hold three marks-holder panel vertically
        JPanel frequencyPanel = new JPanel(new GridLayout(3,1));
        for(int i=0;i<3;i++){
            markPanels[i] = new JPanel();
            frequencyPanel.add(markPanels[i]);
        }
        //configure the font and size of the title and RTT intervals
        titleLabel.setFont(new Font("Helvetica", Font.BOLD,15));
        for(int i=0; i<3; i++){
            intervalLabels[i].setFont(new Font("Helvetica", Font.BOLD,13));
        }
        //BorderLayout for the panel
        this.setLayout(new BorderLayout(10,0));
        this.add(titleLabel, BorderLayout.NORTH);
        this.add(intervalsPanel,BorderLayout.WEST);

        this.add(frequencyPanel,BorderLayout.CENTER);
    }

    /**
     * API to set up Interval values in the histogramPanel
     * @param intervals RTT interval endpoits values
     */
    public void setUpIntervals(float[] intervals){
        //regulate the format to keep three decimal points
        DecimalFormat df = new DecimalFormat("#.000");
        this.intervalLabels[0].setText(df.format(intervals[0])+"<=RTT<"+df.format(intervals[1]));
        this.intervalLabels[1].setText(df.format(intervals[1])+"<=RTT<"+df.format(intervals[2]));
        this.intervalLabels[2].setText(df.format(intervals[2])+"<=RTT<="+df.format(intervals[3]));
    }

    /**
     * API to set up marks("X") for each intervals in the histogramPanel
     * @param columnNum the number of columns to set in the markPanel
     * @param markNumArr mark number for each intervals
     */
    public void setUpMarks(int columnNum, int[] markNumArr){
        for (int i=0;i<3;i++){
            //remove previous marks in this bar
            this.markPanels[i].removeAll();
            //configure the layout to best fit the current mark number
            this.markPanels[i].setLayout(new GridLayout(1,columnNum));
            //add marks to each bar
            for(int j=0; j<markNumArr[i]; j++){
                JLabel mark = new JLabel("X");
                mark.setForeground(Color.BLUE);
                this.markPanels[i].add(mark);
            }
            //add placeholder labels to complement the rest columns
            for(int j=0;j<columnNum-markNumArr[i];j++)
                this.markPanels[i].add(new JLabel());

        }

    }

}
