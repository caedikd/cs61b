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

    static File staging = new File(init.add, "staging");


    static LinkedHashMap staged;
    /** Adds the file to the staging directory given the fileName. */
    public static void add(String fileName) throws IOException {
        staging.createNewFile();

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


        File commitFile = new File(String.valueOf(Commit._head));
        LinkedHashMap committed = Utils.readObject(commitFile, LinkedHashMap.class);
        if (!(committed == null) && committed.containsKey(fileName)) {
            if (committed.get(fileName).equals(id)) {
                System.out.print("No changes added to the commit.");
                System.exit(0);
            }
        }
            //the sha id is different so add to the staging area, then in commit
            //you will commit everything in the staging area?
            //committing clears everything in the staging area and probably adds the
            //commit to a data structure
            //whether or not the file is the same as one already added happens here in add
                File blobs = new File(init.blobs, id);
                Blob blob = new Blob(fileName, inside, id);
                Utils.writeObject(blobs, blob);
                if (staging.length() == 0 && staged == null) {
                    staged = new LinkedHashMap();
                }
                else {
                    staged = Utils.readObject(staging, LinkedHashMap.class);
                }
                //putting the file name and shaid into a hashmap
                staged.put(fileName, id);
                Utils.writeObject(staging, staged);
                //staging should map the file contents to a file name in the form of a hashmap

                //File temp2 = new File(staging, fileName);
                //System.out.println("sha" + id);
//                Utils.writeContents(temp2, id);
//                System.out.println((Utils.readContents(temp2)));
                //temp.delete();
                //Utils.writeObject(staging, staged);





    }
    /**
     * If the current working version of the file is identical to
     * the version in the current commit, do not stage it to be added,
     * and remove it from the staging area if it is already there (as can
     * happen when a file is changed, added, and then changed back). The
     * file will no longer be staged for removal (see gitlet rm), if
     * it was at the time of the command.
     */

}
