package Task2;

/**
 * An entrance class for this program
 */
public class NetAnalyser {
    /**
     * check the validation of param (if given) from the command line, then build a NetAnalyserFrame Object
     * @param args a given max value of the probe number in the selection box
     */
    public static void main(String[] args) {
        if(args.length==0){//if no param is added in command line, use the default constructor
            NetAnalyserFrameV2 netAnalyser = new NetAnalyserFrameV2();

        }else if(args.length==1){//valid length of input
            //use regular expression to check if arg[0] is numeric
            if(args[0].matches("^[-+]?[\\d]*$")){//it is a negative/positive numeric string
                int maxProbeNum = Integer.parseInt(args[0]);
                if (maxProbeNum>=10 && maxProbeNum<=20){//it is in the range of 10-20, construct a NetAnalyser with this param
                    NetAnalyserFrameV2 netAnalyser = new NetAnalyserFrameV2(maxProbeNum);

                }else//the number is out of range
                    System.out.println("max number is out of range, you should input a number from 10 to 20");

            }else//not numeric
                System.out.println("the input should be numeric string");

        }else{//invalid format of input (there is space between two input)
            System.out.println("invalid args format");
        }
    }
}
