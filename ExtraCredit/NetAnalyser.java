package ExtraCredit;

public class NetAnalyser {
    /**
     * Check the validation of param (if given) from the command line, then build a NetAnalyserFrame Object.
     * @param args a given max value of the probe number in the selection box
     */
    public static void main(String[] args) {

        if(args.length==0){//if no param is added in command line, use the default constructor
            NetAnalyserFrameV3 netAnalyser = new NetAnalyserFrameV3();

        }else if(args.length==1){//valid length of input
            //use regular expression to check if arg[0] is numeric
            if(args[0].matches("^[-+]?[\\d]*$")){//it is a negative/positive numeric string
                int maxProbeNum = Integer.parseInt(args[0]);
                if (maxProbeNum>=10 && maxProbeNum<=20){//it is in the range of 10-20, construct a NetAnalyser with this param
                    NetAnalyserFrameV3 netAnalyser = new NetAnalyserFrameV3(maxProbeNum);

                }else//the number is out of range
                    System.out.println("max number is out of range, you should input a number from 10 to 20");

            }else//not numeric
                System.out.println("the input should be numeric string");

        }else{//invalid format of input (there is space in between inputs)
            System.out.println("invalid args format");
        }
    }
}
