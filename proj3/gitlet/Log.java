package gitlet;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class Log {
    /**
     * Current working directory.
     */
    static File CWD = new File(System.getProperty("user.dir"));

    /**
     * Does the basic form of log where it ignores all merged parents and
     * just starts on the current head and goes down to the initial.
     */
    public static void basic() {
        Head head = new Head(init.head);
        File branchFile = new File(init.branches, head.getCurrentBranch());
        LinkedHashMap commits = Utils.readObject(branchFile, LinkedHashMap.class);
        Set<String> keys = commits.keySet();
        List<String> reversOrdered = new ArrayList<String>(keys);
        Collections.reverse(reversOrdered);
        for (String key : reversOrdered) {
            System.out.println(commits.get(key));
        }
    }

    public static List<String> alphabetical(File file) {
        LinkedHashMap stagedMap = Utils.readObject(file, LinkedHashMap.class);
        Set<String> keys1 = stagedMap.keySet();
        List<String> keysLexo = keys1.stream().collect(Collectors.toList());
        java.util.Collections.sort(keysLexo);
        return keysLexo;
    }

    /**
     * Prints out the status of the files.
     */
    public static void status() {
        File initialized = new File(String.valueOf(init.GITLET_DIR));
        if (!initialized.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
            System.exit(0);
        }

        String branches2 = "=== Branches ===" + "\n";
        Head head = new Head(init.head);
        branches2 += "*" + head.getCurrentBranch() + "\n";

        System.out.println(branches2);

        String staged = "=== Staged Files ===" + "\n";
        if (Add.modified.exists()) {
            List<String> x = alphabetical(Add.modified);
            for (String a: x) {
                staged += a + "\n";
            }
        }
        System.out.println(staged);

        String removed = "=== Removed Files ===" + "\n";
        if (Add.rmStaging.exists()) {
            List<String> x = alphabetical(Add.rmStaging);
            for (String a: x) {
                removed += a + "\n";
            }
        }
        System.out.println(removed);


        String modif = "=== Modifications Not Staged For Commit ===" + "\n";
        //check for deleted files
        if (Add.modified.exists()) {
            LinkedHashMap stagedMap = Utils.readObject(Add.modified, LinkedHashMap.class);
            List<String> x = alphabetical(Add.modified);
            for (String a: x) {
                File newFile = new File(init.CWD, a);
                byte[] inside = Utils.readContents(newFile);
                String id = Utils.sha1(inside);
                if (!newFile.exists()) {
                    modif += a + "(deleted)" + "\n";
                }
                else if(newFile.exists()) {
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
     */
    public static void checkout1(String fileName) {
        File newFile = new File(init.CWD, fileName);
        Head head = new Head(init.head);
        String shaCommit = head.getCurrentCommitSha();
        File commitFile = new File(init.commits, shaCommit);
        File fileInCommit = new File(commitFile, shaCommit);

        //deserialize
        if (fileInCommit.exists()) {
            LinkedHashMap allFilesinCommit = Utils.readObject(fileInCommit, LinkedHashMap.class);
            if (allFilesinCommit.containsKey(fileName)) {
                String[] pathSha = (String[]) allFilesinCommit.get(fileName);
                File path = new File(pathSha[1]);
                Utils.writeContents(newFile, Utils.readContents(path));
            }
            else {
                System.out.println("File does not exist in that commit.");
                System.exit(0);
            }
        }
        else {
            System.out.println("File does not exist in that commit.");
            System.exit(0);
        }
    }

    /**
     * Takes the version of the file as it exists in the commit
     * with the given id, and puts it in the working directory,
     * overwriting the version of the file that's already there
     * if there is one. The new version of the file isn't staged.
     */
    public static void checkout2(String commitID, String fileName) {
        File newFile = new File(init.CWD, fileName);
        File commitFile = new File(init.commits, commitID + "/" + commitID);
        if (!commitFile.exists()) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }
        else {
            LinkedHashMap allFilesinCommit = Utils.readObject(commitFile, LinkedHashMap.class);
            if (allFilesinCommit.containsKey(fileName)) {
                String[] pathSha = (String[]) allFilesinCommit.get(fileName);
                File path = new File(pathSha[1]);
                Utils.writeContents(newFile, Utils.readContents(path));
            }
            else {
                System.out.println("File does not exist in that commit.");
                System.exit(0);
            }
        }

    }

    public static void checkout3(String branchName) {
        File newFile = new File(init.branches, branchName);
        if (!newFile.exists()) {
            System.out.println("No such branch exists. ");
            System.exit(0);
        }
        Head head = new Head((init.head));
        if (head.getCurrentBranch().equals(branchName)) {
            System.out.println("No need to checkout the current branch.");
            System.exit(0);
        }
    }
}
