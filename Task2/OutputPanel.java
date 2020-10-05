package Task2;

import javax.swing.*;
import java.awt.*;

/**
 * A component class used to configure the output panel in the NetAnalyserFrame.
 * It contains one field: outputText, in the purpose of providing interface for the frame configuration.
 */
public class OutputPanel extends JPanel {
    public JTextArea outputText = new JTextArea("Your output will appear here..");
    public OutputPanel(){
        this.outputText.setEditable(false);//set the text area to be not editable
        this.add(outputText);
    }
}
