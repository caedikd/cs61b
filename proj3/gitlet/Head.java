package gitlet;

import sun.rmi.rmic.Util;

import java.io.File;
import java.io.Serializable;
import java.util.*;

public class Head implements Serializable {

    String currentBranch;
    String currentCommitSha;

    public Head(String branchName) {
        File currentBranchFile = new File(init.branches, branchName);
        currentBranch = branchName;
        if (currentBranchFile.exists()) {
            if (currentBranchFile.length() != 0) {
                ArrayList<String> shas = Utils.readObject(currentBranchFile, ArrayList.class);
                if (shas != null) {
                    currentCommitSha = shas.get(shas.size()-1);
                }
            }

        }

        update(init.head);
        //File commitFile1 = new File(init.commits, currentCommitSha);
    }

    public Head(File file) {
        Head head = Utils.readObject(init.head, Head.class);
        currentBranch = head.currentBranch;
        currentCommitSha = head.currentCommitSha;
    }

    public void update(File file) {
        Utils.writeObject(file, this);
    }

    public String getCurrentCommitSha() {
        return currentCommitSha;
    }

    public String getCurrentBranch() {
        return currentBranch;
    }


    public void setCurrentBranch(String currentBranch) {
        this.currentBranch = currentBranch;
        update(init.head);
    }

    public void setCurrentCommitSha(String currentCommitSha) {
        this.currentCommitSha = currentCommitSha;
        update(init.head);
    }
}
