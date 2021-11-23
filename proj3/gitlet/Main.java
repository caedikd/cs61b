package gitlet;
import java.io.IOException;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Caedi Seim
 */
public class Main {



    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String... args) throws IOException {
        if (args.length == 0) {
            exitWithError("Must have at least one argument");
        }
        switch (args[0])
        {
            case "init":
                new init();
                break;
            case "commit":
                break;
            case "add":
                break;
            case "rm":
                break;
            case "log":
                Log.basic();
                break;
            default:
                exitWithError(String.format("Unknown command: %s", args[0]));
        }
        return;

}

    public static void exitWithError(String message) {
        if (message != null && !message.equals("")) {
            System.out.println(message);
        }
        System.exit(-1);
    }

}
