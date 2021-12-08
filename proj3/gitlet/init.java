package gitlet;

import java.io.File;
import java.io.IOException;

/**
 * Creates a new gitlet vcs in the current directory.
 * System will automatically start with one commit; that has no
 * files and has the commit message "initial commit". It has
 * a single branch: master, which initially points to this initial
 * commit, and master will be the current branch.
 * @author caedi
 */
class Init {
    /** Folders holding the user's inputted data and files. */
    private static File cWD = new File(System.getProperty("user.dir"));

    /** Main folder that the data will be held in. */
    private static File gitletDir = new File(cWD, ".gitlet");

    /** Folder for commits, will keep and change the pointers. */
    private static File commits = new File(gitletDir, "commits");

    /** Folders holding the user's inputted data and files. */
    private static File branches = new File(gitletDir, "branches");

    /** Folders holding the user's inputted data and files. */
    private static File blobs = new File(gitletDir, "blobs");


    /** Folders holding the user's inputted data and files. */
    private static File remove = new File(gitletDir, "remove");

    /** Folders holding the user's inputted data and files. */
    private static File add = new File(gitletDir, "add");

    /** Folders holding the user's inputted data and files. */
    private static File head = new File(gitletDir, "head");

    /** Constructor that initalizes all the directories if there isn't
     * a cwd already there.
     */
    Init() throws IOException {
        if (gitletDir.exists()) {
            System.out.println("A Gitlet version-control "
                + "system already exists in the current directory.");
        } else {
            gitletDir.mkdir();
            commits.mkdir();
            branches.mkdir();
            blobs.mkdir();

            add.mkdir();
            head.createNewFile();

            Branch branch = new Branch("master", null);
            Head head1 = new Head("master");
            Commit commit = new Commit("", "initial commit", null);
        }
    }

    /** Folders holding the user's inputted data and files.
     * @return file
     * */
    public static File getAdd() {
        return add;
    }

    /** Folders holding the user's inputted data and files.
     * @return file
     * */
    public static File getCommits() {
        return commits;
    }

    /** Folders holding the user's inputted data and files.
     * @return file
     * */
    public static File getBlobs() {
        return blobs;
    }

    /** Folders holding the user's inputted data and files.
     * @return file
     * */
    public static File getCWD() {
        return cWD;
    }

    /** Folders holding the user's inputted data and files.
     * @return file
     * */
    public static File getBranches() {
        return branches;
    }

    /** Folders holding the user's inputted data and files.
     * @return file
     *
     * */
    public static File getGitletDir() {
        return gitletDir;
    }

    /** Folders holding the user's inputted data and files.
     * @return file
     * */
    public static File getHead() {
        return head;
    }

    /** Folders holding the user's inputted data and files.
     * @return file
     * */
    public static File getRemove() {
        return remove;
    }


}
