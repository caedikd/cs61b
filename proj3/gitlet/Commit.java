package gitlet;
import sun.rmi.rmic.Util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;


/** Track the commits.
 * Each commit is identified by its SHA-1 id,
 * which must include the file (blob) references
 * of its files, parent reference, log message,
 * and commit time.
 *
 *
 *  A commit, therefore, will consist of a log message,
 *  timestamp, a mapping of file names to blob
 *  references, a parent reference, and (for merges)
 *  a second parent reference.
 *
 * */

public class Commit implements Serializable {


    private static final SimpleDateFormat format =
            new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy Z");
    private String _message;
    private Date _commitDate;

//    /** LinkedHashMap filled with the SHA1 ID's of the blobs, these get filled
//     *  with the help of add's staging area, each commit set the blobRef's to
//     *  the staging area, then clear the staging area.
//     */
//    private static LinkedHashMap<String, String> blobsTracked;
    private static LinkedHashMap previousMetaDataLL;

    private static byte[] contentsOfFile;


    /**
     * Single file using the most recent file, or current commit.
     */
    //static File _head = new File(init.commits, "currentCommit");
    static File _commitTree = new File(init.commits, "commitTree");

    static File _shaName;
    static File _metaSha;


    private String _sha1;
    private String _parent;



    //maybe a linkedhashset of blobs and their respective sha1 ids
    //this would probably only be for the initial commit
    public Commit(String parentSha, String message, LinkedHashMap<String, String> blobRefs) {
        _message = message;
        _parent = parentSha;
        if (_message.equals("initial commit")) {
            _commitDate = new Date();
            _commitDate.setTime(0);
        }
        else {
            _commitDate = new Date();
        }

//        this.blobsTracked = blobRefs;
        _sha1 = Utils.sha1(_message, this._commitDate.toString());
        Head head = new Head(init.head);
        head.setCurrentCommitSha(_sha1);

        _shaName = new File(init.commits, _sha1);
        _shaName.mkdir();
        _metaSha = new File(_shaName, _sha1);

        /*
        Add the sha to the current branch if it exists
         */
//        if (_commitTree.exists()) {
//            commitTree = Utils.readObject(_commitTree, LinkedHashMap.class);
//        }
//
//        else {
//            commitTree = new LinkedHashMap<>();
//        }
//        blobsTrackedCopy = blobRefs;
//        commitTree.put(_sha1, this);
//        Utils.writeObject(_commitTree, commitTree);
//        Utils.writeObject(_head, blobsTracked);

    }

    /**
     * Get blobs from the staging area in add and commit them and remove them
     * from the staging area.
     * @param message
     */
    public static void commit(String message) {
        if (!Add.staging.exists()) {
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }

        //filename and id in a llhm from addstaging area
        LinkedHashMap stagedMap = Utils.readObject(Add.modified, LinkedHashMap.class);
        if (stagedMap.isEmpty()) {
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }

        Head parentHead = new Head(init.head);

        //getting the previous commit's metadata hashmap, if this is the second commit after init, create a new
        //LHM to store in the metadata file
        File previousCommit = new File(init.commits, parentHead.getCurrentCommitSha());
        File previousMetaData = new File(previousCommit, parentHead.getCurrentCommitSha());
        if (previousMetaData.exists()) {
            previousMetaDataLL = Utils.readObject(previousMetaData, LinkedHashMap.class);
        }
        else {
            previousMetaDataLL = new LinkedHashMap();
        }

        Commit newCommit = new Commit(parentHead.getCurrentCommitSha(), message, stagedMap);

        //for each new file added in the staging area add their filename and SHAID (by changing the byte array given)
        Set<String> keys = stagedMap.keySet();
        for (String key: keys) {
            File temp = new File(init.CWD, key);
            if (!temp.exists()) {
                System.out.println("File does not exist.");
                System.exit(0);
            }
            //add this to the meta data, if a file with the same name is already there, it will be overwritten
            //otherwise this will just contain the parent's data
            previousMetaDataLL.put(key, Utils.sha1(stagedMap.get(key)));
            //create new Files
            File addedCopies = new File(newCommit._shaName, key);

            //writing the byte arrays to the pointing files in the commit directory
            Utils.writeContents(addedCopies, stagedMap.get(key));
        }

        //writing the updated metadata object to the current commit
        Utils.writeObject(newCommit._metaSha, previousMetaDataLL);

        //clear staging area
        Add.staging.delete();
        Add.modified.delete();

//        //change the head
//        parentHead.setCurrentCommitSha(newCommit.sha1());
    }

    public String sha1() {
        return _sha1;
    }

    /** Displays information about the commits, used for
     * the log command.
     * @return String.
     */
    public String toString() {
        return "===\n" + "commit " + _sha1 + "\n"
                + "Date: " + format.format(_commitDate) + "\n"
                + _message + "\n";
    }




}
