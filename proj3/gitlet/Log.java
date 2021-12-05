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
            Commit commitObj = (Commit) commits.get(key);
            System.out.println(commitObj.toString());
        }
    }

    /**
     * Prints out the status of the files.
     */
    public static void status() {
        String branches2 = "=== Branches ===" + "\n";
        LinkedHashMap branches = Utils.readObject(Branch._activeBranch, LinkedHashMap.class);
        Set<String> keys = branches.keySet();
        List<String> reversOrdered = new ArrayList<String>(keys);
        Collections.reverse(reversOrdered);
        for (String key : reversOrdered) {
            branches2 += (key + "\n");
        }
        System.out.println(branches2);



        String staged = "=== Staged Files ===" + "\n";
        if (Add.staging.exists()) {
            LinkedHashMap stagedMap = Utils.readObject(Add.staging, LinkedHashMap.class);
            Set<String> keys1 = stagedMap.keySet();
            for (String key : keys1) {
                staged += key + "\n";
            }
        }
        System.out.println(staged);

        String removed = "=== Removed Files ===" + "\n";
        System.out.println(removed);

        String modif = "=== Modifications Not Staged For Commit ===" + "\n";
        System.out.println(modif);

        String untracked = "=== Untracked Files ===" + "\n";
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
        File commitFile = new File(String.valueOf(Commit._head));
        LinkedHashMap committed = Utils.readObject(commitFile, LinkedHashMap.class);
        if (!(committed == null) && !committed.containsKey(fileName)) {
            System.out.println("File does not exist in that commit.");
            System.exit(0);
        }
        File overwriting = new File(CWD, fileName);

        //get the byte array of the blob with the file name
        File blobFile = new File(init.blobs, fileName);
        Blob blobFromFile = Utils.readObject(blobFile, Blob.class);
        Utils.writeContents(overwriting, blobFromFile._code);
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
