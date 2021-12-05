package gitlet;
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
    private static String _parentId;
    private Date _commitDate;
    private Blob _pointing;

    /** LinkedHashMap filled with the SHA1 ID's of the blobs, these get filled
     *  with the help of add's staging area, each commit set the blobRef's to
     *  the staging area, then clear the staging area.
     */
    private static LinkedHashMap<String, String> blobsTracked;

    /**
     * Single file using the most recent file, or current commit.
     */
    static File _head = new File(init.commits, "currentCommit");
    static File _commitTree = new File(init.commits, "commitTree");

    /** Each commit is identified by its sha-1 id, which includes
     * the files (blob) refs of its files, parent refs,
     * log message and commit time.
     *
     * may want to put blobs in a linkedhashmap?
     */
    static LinkedHashMap<String, Commit> commitTree;
    private String _sha1;


    //maybe a linkedhashset of blobs and their respective sha1 ids
    //this would probably only be for the initial commit
    public Commit(String message, LinkedHashMap<String, String> blobRefs) {
        //initial commit has no associated files and parent, sha-1 id only contains
        //log message and commit time
        _message = message;
        //_parentId = parent;
        if (_message.equals("initial commit")) {
            _commitDate = new Date();
            _commitDate.setTime(0);
        }
        else {
            _commitDate = new Date();
        }
        this.blobsTracked = blobRefs;
        _sha1 = Utils.sha1(_message, this._commitDate.toString());

        if (_commitTree.exists()) {
            commitTree = Utils.readObject(_commitTree, LinkedHashMap.class);
        }
        else {
            commitTree = new LinkedHashMap<>();
        }
        commitTree.put(_sha1, this);
        Utils.writeObject(_commitTree, commitTree);
        Utils.writeObject(_head, blobsTracked);

    }

    /**
     * Get blobs from the staging area in add and commit them and remove them
     * from the staging area.
     * @param message
     */
    public static void commit(String message) {
        if (_head.exists()) {
            _head.delete();
        }
        if (message.length() == 0) {
            throw new GitletException("Please enter a commit message");
        }
        LinkedHashMap stagedMap = Utils.readObject(Add.staging, LinkedHashMap.class);
        if (stagedMap.isEmpty()) {
            throw new GitletException("No changes added to the commit.");
        }
        Set<String> keys = stagedMap.keySet();
        if (blobsTracked == null) {
            blobsTracked = new LinkedHashMap<>();
        }

        for (String key : keys) {
            blobsTracked.put(key, (String) stagedMap.get(key));
        }
        //_parentId = _message;
        Commit newCommit = new Commit(message, stagedMap);

        //clearing the staging area
        Add.staging.delete();
        File newlyCommitted = new File(init.commits, newCommit.sha1());
        Utils.writeObject(newlyCommitted, newCommit);
        Utils.writeObject(_head, newCommit.blobsTracked);
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
