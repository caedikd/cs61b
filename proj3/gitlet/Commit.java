package gitlet;
import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;


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
 * @author caedi
 * */

public class Commit implements Serializable {

    /** The date formatted. */
    private static final SimpleDateFormat FORMAT =
            new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy Z");
    /** Message saved. */
    private String _message;

    /**The date saved. */
    private Date _commitDate;

    /** Passed to a doc. */
    private static LinkedHashMap previousMetaDataLL = new LinkedHashMap();

    /** Copy of a llhm. */
    private LinkedHashMap nonStatic;


    /**
     * Single file using the most recent file, or current commit.
     */
    private static LinkedHashMap _commitTree;

    /** name of file. */
    private static File _shaName;

    /** name of file. */
    private static File _metaSha;

    /** name of parentFile. */
    private static File _parentFile;

    /** thes sha1 of commit. */
    private String _sha1;

    /** name of parentFile. */
    private String _parent;


    /**
     * Commit Initialize.
     * @param parentSha parent sha
     * @param message message
     * @param blobRefs blob refrences
     */
    public Commit(String parentSha, String message,
                  LinkedHashMap<String, String> blobRefs) {
        _message = message;
        _parent = parentSha;
        if (_message.equals("Initial commit")) {
            _commitDate = new Date();
            _commitDate.setTime(0);
        } else {
            _commitDate = new Date();
        }

        _sha1 = Utils.sha1(_message, this._commitDate.toString());
        Head head = new Head(Init.getHead());
        head.setCurrentCommitSha(_sha1);

        String[] array = new String[] {"null", "null"};
        previousMetaDataLL.put(_message, array);
        _shaName = new File(Init.getCommits(), _sha1);
        _shaName.mkdir();
        _metaSha = new File(_shaName, _sha1);
        Utils.writeObject(_metaSha, previousMetaDataLL);

        _parentFile = new File(_shaName, "parent");
        Utils.writeContents(_parentFile, parentSha);
        nonStatic = previousMetaDataLL;

        _commitTree = new LinkedHashMap();
        _commitTree.put(this._sha1, this);
        Branch.updateTree(_commitTree);

    }

    /**
     * Get blobs from the staging area in add and commit
     * them and remove them
     * from the staging area.
     * @param message The message
     */
    public static void commit2(String message) {
        if (!Add.getStaging().exists() && !Add.getRmStaging().exists()) {
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }

    }

    /**
     * Split up.
     * @param message message
     * @return Head
     */
    public static Head commit1(String message) {
        Head parentHead = new Head(Init.getHead());
        File previousCommit = new File(Init.getCommits(),
                parentHead.getCurrentCommitSha());
        File previousMetaData = new File(previousCommit,
                parentHead.getCurrentCommitSha());
        File branchFile = new File(Init.getBranches(),
                parentHead.getCurrentBranch());

        LinkedHashMap commits = Utils.readObject(branchFile,
                LinkedHashMap.class);
        Set<String> keyed = commits.keySet();
        List<String> reversOrdered = new ArrayList<String>(keyed);
        Collections.reverse(reversOrdered);
        Commit prior = (Commit) commits.get(reversOrdered.get(0));
        String priorMessage = prior._message;

        if (previousMetaData.exists()) {
            previousMetaDataLL = Utils.readObject(previousMetaData,
                    LinkedHashMap.class);
            previousMetaDataLL.remove(priorMessage);
        } else {
            previousMetaDataLL = new LinkedHashMap();

        }
        return parentHead;

    }

    /**
     * Split up.
     *
     * @return File
     */
    public static File commit3() {
        Head parentHead = new Head(Init.getHead());
        File previousCommit = new File(Init.getCommits(),
                parentHead.getCurrentCommitSha());
        File previousMetaData = new File(previousCommit,
                parentHead.getCurrentCommitSha());
        File branchFile = new File(Init.getBranches(),
                parentHead.getCurrentBranch());

        LinkedHashMap commits = Utils.readObject(branchFile,
                LinkedHashMap.class);
        Set<String> keyed = commits.keySet();
        List<String> reversOrdered = new ArrayList<String>(keyed);
        Collections.reverse(reversOrdered);
        Commit prior = (Commit) commits.get(reversOrdered.get(0));
        String priorMessage = prior._message;

        if (previousMetaData.exists()) {
            previousMetaDataLL = Utils.readObject(previousMetaData,
                    LinkedHashMap.class);
            previousMetaDataLL.remove(priorMessage);
        } else {
            previousMetaDataLL = new LinkedHashMap();

        }
        return previousMetaData;
    }


    /**
     * Get blobs from the staging area in add and commit
     * them and remove them
     * from the staging area.
     * @param message The message
     */
    public static void commit(String message) {
        commit2(message);
        Head parentHead = commit1(message);
        File previousMetaData = commit3();


        if (Add.getStaging().exists()) {
            LinkedHashMap stagedMap = Utils.readObject(Add.getModified(),
                    LinkedHashMap.class);
            if (stagedMap.isEmpty()) {
                System.out.println("No changes added to the commit.");
                System.exit(0);
            }

            Commit newCommit = new Commit(parentHead.getCurrentCommitSha(),
                    message, stagedMap);

            Set<String> keys = stagedMap.keySet();
            for (String key: keys) {
                File temp = new File(Init.getCWD(), key);
                if (!temp.exists()) {
                    System.out.println("File does not exist.");
                    System.exit(0);
                }

                String sha1OfAdded = Utils.sha1(stagedMap.get(key));
                String[] pathSha = new String[] {sha1OfAdded, Init.getCommits()
                    + "/" + newCommit._sha1 + "/" + key};
                String[] empty = new String[] {"null", "null"};
                previousMetaDataLL.put(key, pathSha);
                previousMetaDataLL.put(newCommit._message, empty);

                File addedCopies = new File(newCommit._shaName, key);

                Utils.writeContents(addedCopies, stagedMap.get(key));
                Utils.writeObject(newCommit._metaSha, previousMetaDataLL);
            }
        } else if (Add.getRmStaging().exists()) {
            LinkedHashMap priorMap = Utils.readObject(previousMetaData,
                    LinkedHashMap.class);
            Commit newCommit = new Commit(parentHead.getCurrentCommitSha(),
                    message, priorMap);
            String[] empty = new String[] {"null", "null"};
            previousMetaDataLL.put(newCommit._message, empty);
            Utils.writeObject(newCommit._metaSha, previousMetaDataLL);
            Add.getRmStaging().delete();
        }

        delete();
    }


    /**
     *      * Should return the commit's sha1.
     *
     */
    public static void delete() {
        Add.getStaging().delete();
        Add.getModified().delete();
        Add.getRmStaging().delete();
    }

    /**
     *      * Should return the commit's sha1.
     *      * @return sha1
     */
    public String sha1() {
        return _sha1;
    }

    /**
     * Should return the previous metadata.
     * @return Linked.
     */
    public LinkedHashMap getPreviousMetaDataLL() {
        return nonStatic;
    }

    /** Displays information about the commits, used for
     * the log command.
     * @return String.
     */
    public String toString() {
        return "===\n" + "commit " + _sha1 + "\n"
                + "Date: " + FORMAT.format(_commitDate) + "\n"
                + _message + "\n";
    }




}
