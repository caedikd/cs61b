package gitlet;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;


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

    static final File COMMIT_FOLDER = new File(String.valueOf(init.commits));

    private static final SimpleDateFormat format =
            new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy z");
    private final String _message;
    private final String _parentId;
    private Date _commitDate;
    private Blob _pointing;

    static LinkedHashMap<String, Commit> commitTree;

    /** Each commit is identified by its sha-1 id, which includes
     * the files (blob) refs of its files, parent refs,
     * log message and commit time.
     */
    private String _sha1;

    public Commit(String parent, String message, Blob pointing) {
        //initial commit has no associated files and parent, sha-1 id only contains
        //log message and commit time
        _message = message;
        _parentId = parent;
        if (_parentId.equals("null")) {
            _commitDate = new Date();
            _commitDate.setTime(0);
        }
        else {
            _commitDate = new Date();
        }
        _pointing = pointing;

        _sha1 = Utils.sha1(Utils.serialize(this));
        commitTree = new LinkedHashMap<>();
        commitTree.put(_sha1, this);

//        Utils.writeObject(outfile, this);

        //create new file
        /* idea: save the sha1 as the commit message and then save the
         details of the commit
        in a linkedlisthashset ?

        or Serialize the commit object, which will have a saved
        linkedhashset, then when you deserialize, look

        Serialization: the process of translating an object to a
        series of bytes that can then be stored in the file,
        then we can deserialize the bytes and get the
        original object back.
         */
    }

    public static void commit(String message) {
//        try {
//            if (message.length() == 0) {
//                System.out.println("Please enter a commit message");
//                return;
//            }
//            File history = new File(COMMIT_FOLDER, String.valueOf(init.branches));
//            if (history != null) {
//                File parent = new File("commits.dir", history);
//
//            }
//
//        }
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
                + _message;
    }


}
