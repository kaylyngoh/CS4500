/**
 * Class representing the entry point to the program xyes that echos the command line arguments to
 * standard output.
 */
class Xyes {

    /**
     * Runs the program and prints the command line arguments depending if there are limitation on the
     * number of outputs. If the first command line argument is "-limit", the program composes the
     * output string from the remaining command line arguments and displays only 20 lines of this
     * string. If there are no command line arguments to form the output string, the program uses
     * "hello world" instead.
     */
    public static void main(String args[]) {
        String argString = "";
        boolean isLimited = false;
        if (args.length == 0) {
            argString = "Hello World";
        }
        else {
            isLimited = "-limit".equals(args[0]);

            int i = isLimited ? 1 : 0;
            for (; i < args.length; i++) {
                if (argString.length() == 0)
                    argString += args[i];
                else
                    argString += " " + args[i];
            }
        }

        if (isLimited) {
            for (int j = 0; j < 20; j++)
                System.out.println(argString);
        }
        else {
            while (true)
                System.out.println(argString);
        }
    }
}

