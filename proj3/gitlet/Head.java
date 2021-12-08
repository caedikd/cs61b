package gitlet;


import java.io.File;
import java.io.Serializable;

/**
 * Head class.
 * @author caedi
 */
public class Head implements Serializable {

    /** String. */
    private String currentBranch;

    /** String. */
    private String currentCommitSha;

    /** Head constructor.
     * @param branchName bn
     */
    public Head(String branchName) {
        File currentBranchFile = new File(Init.getBranches(), branchName);
        currentBranch = branchName;

        update(Init.getHead());
    }

    /** Head constructor.
     * @param file file
     */
    public Head(File file) {
        Head head = Utils.readObject(Init.getHead(), Head.class);
        currentBranch = head.currentBranch;
        currentCommitSha = head.currentCommitSha;
    }

    /**
     * Gets current.
     * @param file file
     *
     * */
    public void update(File file) {
        Utils.writeObject(file, this);
    }

    /**
     * Gets current.
     * @return currentB
     *
     * */
    public String getCurrentCommitSha() {
        return currentCommitSha;
    }

    /**
     * Gets current.
     * @return currentB
     *
     * */
    public String getCurrentBranch() {
        return currentBranch;
    }

    /**
     * Sets current.
     * @param currentBranch1 curr
     */
    public void setCurrentBranch(String currentBranch1) {
        this.currentBranch = currentBranch1;
        update(Init.getHead());
    }

    /**
     * Sets current.
     * @param currentCommitSha1 curr
     */
    public void setCurrentCommitSha(String currentCommitSha1) {
        this.currentCommitSha = currentCommitSha1;
        update(Init.getHead());
    }
}
