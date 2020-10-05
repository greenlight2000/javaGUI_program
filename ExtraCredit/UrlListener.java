package ExtraCredit;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * A listener class used to record users' input in the urlText
 */
public class UrlListener implements DocumentListener {
    NetAnalyserFrameV3 frame;
    public UrlListener(NetAnalyserFrameV3 frame){
        this.frame = frame;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        frame.url = frame.inputPanel.urlText.getText();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        frame.url = frame.inputPanel.urlText.getText();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        frame.url = frame.inputPanel.urlText.getText();
    }
}
