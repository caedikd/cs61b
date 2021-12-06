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
        LinkedHashMap commits = Utils.readObject(Commit._commitTree, LinkedHashMap.class);
        Set<String> keys = commits.keySet();
        List<String> reversOrdered = new ArrayList<String>(keys);
        Collections.reverse(reversOrdered);
        for (String key : reversOrdered) {
            Commit commitObj = (Commit) commits.get(key);
            System.out.println(commitObj.toString());
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

        String branches2 = "=== Branches ===" + "\n";
        Head head = new Head(init.head);
        branches2 += "*" + head.getCurrentBranch() + "\n";
//        LinkedHashMap branches = Utils.readObject(Branch._activeBranch, LinkedHashMap.class);
//        Set<String> keys = branches.keySet();
//        List<String> reversOrdered = new ArrayList<String>(keys);
//        Collections.reverse(reversOrdered);
//        for (String key : reversOrdered) {
//            branches2 += (key + "\n");
//        }
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
//        File commitFile = new File(String.valueOf(Commit._head));
//        LinkedHashMap committed = Utils.readObject(commitFile, LinkedHashMap.class);
//        if (!(committed == null) && !committed.containsKey(fileName)) {
//            System.out.println("File does not exist in that commit.");
//            System.exit(0);
//        }
//        File overwriting = new File(CWD, fileName);
//
//        //get the byte array of the blob with the file name
//        //what if two files from init blobs have the same name?
//        File blobFile = new File(init.blobs, fileName);
//        Blob blobFromFile = Utils.readObject(blobFile, Blob.class);
//        Utils.writeContents(overwriting, blobFromFile.codeGetter());
    }

    /**
     * Takes the version of the file as it exists in the commit
     * with the given id, and puts it in the working directory,
     * overwriting the version of the file that's already there
     * if there is one. The new version of the file isn't staged.
     */
    public static void checkout2(String commitID, String FileName) {
//        LinkedHashMap commits = Utils.readObject(Commit._commitTree, LinkedHashMap.class);
//        Set<String> keys = commits.keySet();
//        List<String> reversOrdered = new ArrayList<String>(keys);
//        Collections.reverse(reversOrdered);
//        int i = 0;
//        for (String key : reversOrdered) {
//            Commit commitObj = (Commit) commits.get(key);
//            if (commitObj.sha1().equals(commitID)) {
//                LinkedHashMap blobs = (LinkedHashMap) commits.get(commitID).blobs();
//
//                i = 1;
//                break;
//            }
//        }
//        if (i == 0) {
//            System.out.println("No commit with that id exists.");
//            System.exit(0);
//        }


    }
}
