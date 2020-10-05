package Task2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A listener class used to record the no. of probes that users choose.
 */
public class ProbeNumListener implements ActionListener {
    NetAnalyserFrameV2 frame;
    public ProbeNumListener(NetAnalyserFrameV2 frame){
        this.frame = frame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        frame.probeNum = (int)frame.inputPanel.probeNumBox.getSelectedItem();
    }
}
