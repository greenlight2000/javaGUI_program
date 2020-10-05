package Task1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * A listener class used to record the no. of probes that users choose.
 */
public class ProbeNumListener implements ActionListener {
    NetAnalyserFrameV1 frame;
    public ProbeNumListener(NetAnalyserFrameV1 frame){
        this.frame = frame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        frame.probeNum = (int)frame.inputPanel.probeNumBox.getSelectedItem();
    }
}
