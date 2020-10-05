package Task1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * A listener class used to trigger the btnClickHandler method in the NetAnalyserFrame.
 */
public class BtnListener implements ActionListener {
    public NetAnalyserFrameV1 frame;
    public BtnListener(NetAnalyserFrameV1 frame){
        this.frame = frame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            frame.btnClickHandler();
        } catch (IOException ex) {//catch a IOException, possibly raised by reading action
            System.out.println("Error reading from command line");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame,"Error reading from command line","WARNING", JOptionPane.WARNING_MESSAGE);
        } catch (InterruptedException ex) {//catch a InterruptedException, possibly raised by thread error
            System.out.println("Thread interrupted, please try again");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame,"Thread interrupted, please try again","WARNING", JOptionPane.WARNING_MESSAGE);
        }
    }
}
