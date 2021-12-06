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
            exitWithError("Please enter a command.");
        }
        switch (args[0])
        {
            case "init":
                new init();
                break;
            case "commit":
                if (args.length == 1 || args[1].isBlank()) {
                    System.out.println("Please enter a commit message.");
                    System.exit(0);
                }
                Commit.commit(args[1]);
                break;
            case "add":
                Add.add(args[1]);
                break;
            case "rm":
                Add.rm(args[1]);
                break;
            case "checkout":
                if (args.length == 1) {
                    throw new GitletException("Nothing entered to checkout.");
                }
                if (args.length == 2) {
                    Log.checkout3(args[1]);
                }
                if (args[1].equals("--")) {
                    Log.checkout1(args[2]);
                }
                if (args.length == 4) {
                    if (!args[2].equals("--")) {
                        System.out.println("Incorrect operands.");
                        System.exit(0);
                    }
                    Log.checkout2(args[1], args[3]);
                }
                break;
            case "log":
                Log.basic();
                break;
            case "global-log":
                Log.basic();
                break;
            case "branch":
                break;
            case "status":
                Log.status();
                break;
            default:
                exitWithError("No command with that name exists.");
        }
        return;

}

    public static void exitWithError(String message) {
        if (message != null && !message.equals("")) {
            System.out.println(message);
        }
        System.exit(0);
    }

}
