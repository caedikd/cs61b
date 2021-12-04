package gitlet;
import com.sun.java.accessibility.util.GUIInitializedListener;

import java.io.File;
import java.io.IOException;

/**
 * Creates a new gitlet vcs in the current directory.
 * System will automatically start with one commit; that has no
 * files and has the commit message "initial commit". It has
 * a single branch: master, which initially points to this initial
 * commit, and master will be the current branch.
 */
public class init {
    static File CWD = new File(System.getProperty("user.dir"));

    /** Main folder that the data will be held in. */
    static File GITLET_DIR = new File(CWD, ".gitlet");

    /** Folder for commits, will keep and change the pointers. */
    static File commits = new File(GITLET_DIR, "commits");

    /** Folders holding the user's inputted data and files. */
    static File branches = new File(GITLET_DIR, "branches");
    static File blobs = new File(GITLET_DIR, "blobs");
    static File master = new File(branches, "master");
    static File remove = new File(GITLET_DIR, "remove");
    static File add = new File(GITLET_DIR, "add");
    static File head = new File(GITLET_DIR, "head");

    /** Constructor that initalizes all the directories if there isn't
     * a cwd already there.
     */
    public init() throws IOException {
        if (GITLET_DIR.exists()) {
            throw new GitletException("A Gitlet version-control system already exists in the current directory.");
        }
        else {
            GITLET_DIR.mkdir();
            commits.mkdir();
            branches.mkdir();
            blobs.mkdir();

            //staging file
            add.mkdir();
            /* Write in and serialize the empty first commit object
                 */
            Commit commit = new Commit("initial commit", null);
            Branch branch = new Branch("master", null);
            System.out.println(Branch._head);
//            File initialCommit = new File(commits, commit.sha1());
//            Utils.writeObject(initialCommit, commit);
//            Utils.writeObject(master, commit.sha1());
//            Utils.writeObject(head, commit.sha1());
        }
    }

}
