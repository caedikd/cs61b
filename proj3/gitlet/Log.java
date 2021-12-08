package gitlet;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Printing places.
 * @author caedi
 */
public class Log {
    /**
     * Current working directory.
     */
    private static File cWD = new File(System.getProperty("user.dir"));

    /**
     * Does the basic form of log where it ignores all merged parents and
     * just starts on the current head and goes down to the Initial.
     */
    public static void basic() {
        Head head = new Head(Init.getHead());
        File branchFile =
                new File(Init.getBranches(), head.getCurrentBranch());
        LinkedHashMap commits =
                Utils.readObject(branchFile, LinkedHashMap.class);
        Set<String> keys = commits.keySet();
        List<String> reversOrdered = new ArrayList<String>(keys);
        Collections.reverse(reversOrdered);
        for (String key : reversOrdered) {
            System.out.println(commits.get(key).toString());
        }
    }

    /**
     * Puts in alphabetical order.
     *
     * @param file fm
     * @return List
     */
    public static List<String> alphabetical(File file) {
        LinkedHashMap stagedMap = Utils.readObject(file, LinkedHashMap.class);
        Set<String> keys1 = stagedMap.keySet();
        List<String> keysLexo = keys1.stream().collect(Collectors.toList());
        java.util.Collections.sort(keysLexo);
        return keysLexo;
    }

    /**
     * Status p1.
     */
    public static void status1() {
        File initialized = new File(String.valueOf(Init.getGitletDir()));
        if (!initialized.exists()) {
            System.out.println("Not in an Initialized Gitlet directory.");
            System.exit(0);
        }
    }

    /**
     * Prints out the status of the files.
     */
    public static void status() {
        status1();

        String branches2 = "=== Branches ===" + "\n";
        Head head = new Head(Init.getHead());
        branches2 += "*" + head.getCurrentBranch() + "\n";
        List<String> allNames = Utils.plainFilenamesIn(Init.getBranches());
        for (String name : allNames) {
            if (!name.equals(head.getCurrentBranch())) {
                branches2 += name + "\n";
            }
        }
        System.out.println(branches2);

        String staged = "=== Staged Files ===" + "\n";
        if (Add.getModified().exists()) {
            List<String> x = alphabetical(Add.getModified());
            for (String a : x) {
                staged += a + "\n";
            }
        }
        System.out.println(staged);

        String removed = "=== Removed Files ===" + "\n";
        if (Add.getRmStaging().exists()) {
            List<String> x = alphabetical(Add.getRmStaging());
            for (String a : x) {
                removed += a + "\n";
            }
        }
        System.out.println(removed);


        String modif = "=== Modifications Not Staged For Commit ===" + "\n";
        if (Add.getModified().exists()) {
            LinkedHashMap stagedMap =
                    Utils.readObject(Add.getModified(), LinkedHashMap.class);
            List<String> x = alphabetical(Add.getModified());
            for (String a : x) {
                File newFile = new File(Init.getCWD(), a);
                byte[] inside = Utils.readContents(newFile);
                String id = Utils.sha1(inside);
                if (!newFile.exists()) {
                    modif += a + "(deleted)" + "\n";
                } else if (newFile.exists()) {
                    if (stagedMap.get(a).equals(inside)) {
                        modif += a + "(modified)" + "\n";
                    }
                }
            }
        }
        System.out.println(modif);
        String untracked = "=== Untracked Files ===";
        System.out.println(untracked);

    }

    /**
     * Checkout, will take the version of the file name as it exists
     * in the head commit, the front of the current branch, and puts
     * it in the working directory, overwriting the version of the
     * file that's already there if there is one. The new version
     * of the file is not staged.
     *
     * @param fileName fn
     */
    public static void checkout1(String fileName) {
        File newFile = new File(Init.getCWD(), fileName);
        Head head = new Head(Init.getHead());
        String shaCommit = head.getCurrentCommitSha();
        File commitFile = new File(Init.getCommits(), shaCommit);
        File fileInCommit = new File(commitFile, shaCommit);

        if (fileInCommit.exists()) {
            LinkedHashMap allFilesinCommit =
                    Utils.readObject(fileInCommit, LinkedHashMap.class);
            if (allFilesinCommit.containsKey(fileName)) {
                String[] pathSha = (String[]) allFilesinCommit.get(fileName);
                File path = new File(pathSha[1]);
                Utils.writeContents(newFile, Utils.readContents(path));
            } else {
                System.out.println("File does not exist in that commit.");
                System.exit(0);
            }
        } else {
            System.out.println("File does not exist in that commit.");
            System.exit(0);
        }
    }

    /**
     * Takes the version of the file as it exists in the commit
     * with the given id, and puts it in the working directory,
     * overwriting the version of the file that's already there
     * if there is one. The new version of the file isn't staged.
     *
     * @param fileName fn
     * @param commitID cm
     */
    public static void checkout2(String commitID, String fileName) {
        File newFile = new File(Init.getCWD(), fileName);
        File commitFile = new File(Init.getCommits(),
                commitID + "/" + commitID);
        if (!commitFile.exists()) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        } else {
            LinkedHashMap allFilesinCommit =
                    Utils.readObject(commitFile, LinkedHashMap.class);
            if (allFilesinCommit.containsKey(fileName)) {
                String[] pathSha = (String[]) allFilesinCommit.get(fileName);
                File path = new File(pathSha[1]);
                Utils.writeContents(newFile, Utils.readContents(path));
            } else {
                System.out.println("File does not exist in that commit.");
                System.exit(0);
            }
        }

    }

    /**
     * Takes all files in the commit at the head of the given branch,
     * and puts them in the working directory.
     *
     * @param branchName bn
     */
    public static void checkout3(String branchName) {
        File newFile = new File(Init.getBranches(), branchName);
        if (!newFile.exists()) {
            System.out.println("No such branch exists. ");
            System.exit(0);
        }
        Head head = new Head((Init.getHead()));
        if (head.getCurrentBranch().equals(branchName)) {
            System.out.println("No need to checkout the current branch.");
            System.exit(0);
        }

        head.setCurrentBranch(branchName);

    }

}
