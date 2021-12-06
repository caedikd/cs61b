package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashMap;

/** Adds a copy of the file as it currently exists to the staging area
 * Staging an already-staged file overwrites the previous
 * entry in the staging area with the new contents. The
 * staging area should be somewhere in .gitlet.
 */

/**
 * use the add directory as the place that will hold the file. there will be a hashmap of blobs that will be serialized
 * into this file that are the things that are supposed to be added
 * in status we will unserialize from this file and get the names of things that will be added
 */

public class Add implements Serializable {

    static File staging = new File(init.add, "addStaging");
    static File rmStaging = new File(init.add, "rmStaging");
    static File modified =  new File(init.add, "staged");


    static LinkedHashMap stagedAdd;
    static LinkedHashMap stagedRem;
    static LinkedHashMap stagedMod;

    /** Adds the file to the staging directory given the fileName. */
    public static void add(String fileName) throws IOException {
        File temp = new File(init.CWD, fileName);
        if (!temp.exists()) {
            System.out.println("File does not exist.");
            System.exit(0);
        }

        //getting the sha1 id of the file that is dependent of contents of file
        //we should probably have something in commit that keeps track of sha - ids
        //for each committed file
        byte[] inside = Utils.readContents(temp);
        String id = Utils.sha1(inside);

        Head currHead = new Head(init.head);
        String currentSha = currHead.getCurrentCommitSha();
        File commitMetadata = new File(init.commits, currentSha +"/" +currentSha);

        if (commitMetadata.exists()) {
            stagedAdd = Utils.readObject(commitMetadata, LinkedHashMap.class);
            if (stagedAdd.containsKey(fileName)) {
                String[] pathSha = (String[]) stagedAdd.get(fileName);
                if (pathSha[0].equals(id)) {
                    //System.out.println("No changes added to the commit.");
                    System.exit(0);
                }

            }
            if (modified.exists()) {
                stagedMod = Utils.readObject(modified, LinkedHashMap.class);
            }
            else {
                stagedMod = new LinkedHashMap();
            }
            stagedMod.put(fileName, inside);
            stagedAdd.put(fileName, inside);
        }
        else {
            if (modified.exists()) {
                stagedMod = Utils.readObject(modified, LinkedHashMap.class);
            }
            else {
                stagedMod = new LinkedHashMap();
            }
            stagedAdd = new LinkedHashMap();
            stagedAdd.put(fileName, inside);
            stagedMod.put(fileName, inside);
        }

        Utils.writeObject(modified, stagedMod);
        Utils.writeObject(staging, stagedAdd);

    }

    /**
     * If the current working version of the file is identical to
     * the version in the current commit, do not stage it to be added,
     * and remove it from the staging area if it is already there (as can
     * happen when a file is changed, added, and then changed back). The
     * file will no longer be staged for removal (see gitlet rm), if
     * it was at the time of the command.
     */
    public static void rm(String fileName) {
        File temp = new File(init.CWD, fileName);
        //staging only exists if there are files written to it in a hashmap
        int i = 0;
        if (rmStaging.exists()) {
            stagedRem = Utils.readObject(rmStaging, LinkedHashMap.class);
        }
        else {
            stagedRem = new LinkedHashMap();
        }

        if (modified.exists()) {
            LinkedHashMap stagedAdd = Utils.readObject(modified, LinkedHashMap.class);
            if (stagedAdd.containsKey(fileName)) {
                stagedRem.put(fileName, stagedAdd.get(fileName));
                Utils.writeObject(rmStaging, stagedRem);
                stagedAdd.remove(fileName);
                i += 1;
            }
            Utils.writeObject(modified, stagedAdd);
        }
        else {
            Head currHead = new Head(init.head);
            String currentSha = currHead.getCurrentCommitSha();
            File commitMetadata = new File(init.commits, currentSha +"/" +currentSha);
            if (commitMetadata.exists()) {
                LinkedHashMap trackedFiles = Utils.readObject(commitMetadata, LinkedHashMap.class);
                if (trackedFiles.containsKey(fileName)) {
                    stagedRem.put(fileName, trackedFiles.get(fileName));
                    i += 1;
                    temp.delete();
                }
                Utils.writeObject(rmStaging, stagedRem);
            }
        }

        if (i == 0) {
            System.out.println("No reason to remove the file.");
            System.exit(0);
        }


    }

}
