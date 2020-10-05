package Task1;

/**
 * entrance class for the program
 * @author wangyunkun
 * @version 1.0
 */
public class NetAnalyser {
    /**
     * check the validation of param (if given) from the command line, then build a NetAnalyserV3 Object
     * @param args not used
     */
    public static void main(String[] args) {
        //the default constructor generate a NetAnalyserFrame with default max probeNum = 10
        NetAnalyserFrameV1 netAnalyser = new NetAnalyserFrameV1();
    }
}
