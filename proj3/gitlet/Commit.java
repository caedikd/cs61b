package gitlet;
import sun.rmi.rmic.Util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.text.SimpleDateFormat;


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
    private static LinkedHashMap previousMetaDataLL = new LinkedHashMap();


    /**
     * Single file using the most recent file, or current commit.
     */
    static LinkedHashMap _commitTree;

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

        _sha1 = Utils.sha1(_message, this._commitDate.toString());
        Head head = new Head(init.head);
        head.setCurrentCommitSha(_sha1);

        String[] array = new String[] {"null", "null"};
        previousMetaDataLL.put(_message, array);
        _shaName = new File(init.commits, _sha1);
        _shaName.mkdir();
        _metaSha = new File(_shaName, _sha1);
        Utils.writeObject(_metaSha, previousMetaDataLL);


        _commitTree = new LinkedHashMap();
        _commitTree.put(this._sha1, this);
        Branch.updateTree(_commitTree);

    }

    /**
     * Get blobs from the staging area in add and commit them and remove them
     * from the staging area.
     * @param message
     */
    public static void commit(String message) {
        if (!Add.staging.exists() && !Add.rmStaging.exists()) {
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }

        Head parentHead = new Head(init.head);
        File previousCommit = new File(init.commits, parentHead.getCurrentCommitSha());
        File previousMetaData = new File(previousCommit, parentHead.getCurrentCommitSha());
        File branchFile = new File(init.branches, parentHead.getCurrentBranch());
        LinkedHashMap commits = Utils.readObject(branchFile, LinkedHashMap.class);
        Set<String> keyed = commits.keySet();
        List<String> reversOrdered = new ArrayList<String>(keyed);
        Collections.reverse(reversOrdered);
        Commit prior = (Commit) commits.get(reversOrdered.get(0));
        String priorMessage = prior._message;


        if (previousMetaData.exists()) {
            previousMetaDataLL = Utils.readObject(previousMetaData, LinkedHashMap.class);
            previousMetaDataLL.remove(priorMessage);
        }
        else {
            previousMetaDataLL = new LinkedHashMap();

        }

        if (Add.staging.exists()) {
            //filename and id in a llhm from addstaging area
            LinkedHashMap stagedMap = Utils.readObject(Add.modified, LinkedHashMap.class);
            if (stagedMap.isEmpty()) {
                System.out.println("No changes added to the commit.");
                System.exit(0);
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
                String sha1OfAdded = Utils.sha1(stagedMap.get(key));
                String[] pathSha = new String[] {sha1OfAdded, init.commits + "/" + newCommit._sha1 + "/" + key};
                previousMetaDataLL.put(key, pathSha);
                previousMetaDataLL.put(newCommit._message, pathSha);

                //create new Files
                File addedCopies = new File(newCommit._shaName, key);

                //writing the byte arrays to the pointing files in the commit directory
                Utils.writeContents(addedCopies, stagedMap.get(key));
                Utils.writeObject(newCommit._metaSha, previousMetaDataLL);
            }
        }
        else if (Add.rmStaging.exists()) {
            LinkedHashMap priorMap = Utils.readObject(previousMetaData, LinkedHashMap.class);
            Commit newCommit = new Commit(parentHead.getCurrentCommitSha(), message, priorMap);
            String[] empty = new String[] {"null", "null"};
            previousMetaDataLL.put(newCommit._message, empty);
            Utils.writeObject(newCommit._metaSha, previousMetaDataLL);
            Add.rmStaging.delete();

        }


        //clear staging area
        Add.staging.delete();
        Add.modified.delete();
        Add.rmStaging.delete();

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
