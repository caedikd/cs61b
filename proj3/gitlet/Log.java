package gitlet;

import java.io.File;
import java.util.*;

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
        LinkedHashMap commits = Utils.readObject(Commit._commitTree, LinkedHashMap.class);
        Set<String> keys = commits.keySet();
        List<String> reversOrdered = new ArrayList<String>(keys);
        Collections.reverse(reversOrdered);
        for (String key : reversOrdered) {
            System.out.println(commits.get(key));;
        }
    }

    /**
     * Prints out the status of the files.
     */
    public static void status() {
        String branches = "=== Branches ===" + "\n" + Branch._head + "\n";
        System.out.println(branches);

        String staged = "=== Staged Files ===" + "\n";
        if (Add.staging.exists()) {
            LinkedHashMap stagedMap = Utils.readObject(Add.staging, LinkedHashMap.class);
            Set<String> keys = stagedMap.keySet();
            for (String key : keys) {
                staged += key + "\n";
            }
        }
        System.out.println(staged);
    }

    /**
     * Checkout, will take the version of the file name as it exists
     * in the head commit, the front of the current branch, and puts
     * it in the working directory, overwriting the version of the
     * file that's already there if there is one. The new version
     * of the file is not staged.
     */
    public static void checkout1(String fileName) {
        File commitFile = new File(String.valueOf(Commit._head));
        LinkedHashMap committed = Utils.readObject(commitFile, LinkedHashMap.class);
        if ((committed == null) || !committed.containsKey(fileName)) {
            throw new GitletException("File does not exist in that commit.");
        }
        File overwriting = new File(CWD, fileName);

        if (overwriting.exists()) {
            
        }



    }

    /**
     * Takes the version of the file as it exists in the commit
     * with the given id, and puts it in the working directory,
     * overwriting the version of the file that's already there
     * if there is one. The new version of the file isn't staged.
     */
    public static void checkout2() {

    }
}
