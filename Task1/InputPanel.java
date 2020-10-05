package Task1;

import javax.swing.*;
import java.awt.*;

/**
 * A component class used to configure the input section panel in the NetAnalyserFrame.
 * It contains three fields urlText, probeNumBox, processBtn, in the purpose of providing interface for the frame configuration.
 * This panel is composed of four sub-panel: infoPanel, urlPanel, probePanel, btnPanel.
 * And the protected init methods are for expansibility.
 */
public class InputPanel extends JPanel {
    public JTextField urlText = new JTextField(20);
    public JComboBox<Integer> probeNumBox = new JComboBox<>();
    public JButton processBtn = new JButton("Process");

    /**
     * Initialize a title information panel.
     */
    protected void init_infoPanel(){
        //build up a JPanel with FlowLayout configuration
        JPanel infoPanel = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("\nEnter Test URL & no. of probes and click on Process");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD,15));
        infoPanel.add(titleLabel);

        this.add(infoPanel);
    }
    /**
     * Initialize a url input panel.
     * Containing a JLabel and a JTextField.
     */
    protected void init_urlPanel(){
        //build up a JPanel with FlowLayout configuration
        JPanel urlPanel = new JPanel(new FlowLayout());
        JLabel l = new JLabel("Text URL: ");
        l.setFont(new Font("Helvetica", Font.BOLD,13));
        urlPanel.add(l);
        urlPanel.add(urlText);

        this.add(urlPanel);
    }

    /**
     * Initialize a probe number selection panel.
     * Containing a JLabel and a JComboBox.
     * @param maxProbeNum the maximum value to show in the comboBox.
     */
    protected void init_probePanel(int maxProbeNum){
        JPanel probePanel = new JPanel(new FlowLayout());
        //configure the comboBox with range 1 - maxProbeNum
        for(int i=1; i<=maxProbeNum; i++){
            probeNumBox.addItem(i);
        }
        //set a default selected number 1 for the case where users click the btn without triggering a selecting event
        probeNumBox.setSelectedItem(1);
        JLabel l = new JLabel("No. of probes ");
        l.setFont(new Font("Helvetica", Font.BOLD,13));
        probePanel.add(l);
        probePanel.add(probeNumBox);

        this.add(probePanel);
    }

    /**
     * Initialize a panel to hold the processBtn.
     */
    protected void init_btnPanel(){
        JPanel btnPanel = new JPanel(new FlowLayout());
        processBtn.setFont(new Font("Helvetica", Font.BOLD,13));
        btnPanel.add(processBtn);

        this.add(btnPanel);
    }

    /**
     * Constructor of InputPanel
     * @param maxProbeNum the maximum value to show in the comboBox.
     */
    public InputPanel(int maxProbeNum){
        init_infoPanel();
        init_urlPanel();
        init_probePanel(maxProbeNum);
        init_btnPanel();
        //set the panel to be vertically laid
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    }
}
