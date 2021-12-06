package gitlet;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.io.File;
import java.util.List;

public class Branch implements Serializable {

    static File _activeBranch = new File(String.valueOf(init.branches));
    static LinkedHashMap<String, Commit> _commitTree;
    static ArrayList<String> _log;

    public Branch(String name, LinkedHashMap commitTree) throws IOException {
        _activeBranch = new File(_activeBranch,  name);
        if (commitTree == null) {
            _commitTree = new LinkedHashMap<>();
        }
        else {
            _commitTree = commitTree;
        }
        Utils.writeObject(_activeBranch, _commitTree);

    }

    public static void updateTree(LinkedHashMap commitTree) {
        List<String> lKeys = new ArrayList<String>(commitTree.keySet());
        Head head = Utils.readObject(init.head, Head.class);
        File branchFile = new File(init.branches, head.getCurrentBranch());
        LinkedHashMap commitTreed = Utils.readObject(branchFile, LinkedHashMap.class);
//        if (commitTreed != null) {
            commitTreed.put(lKeys.get(0), commitTree.get(lKeys.get(0)));
            Utils.writeObject(branchFile, commitTreed);
    }

    public static void newBranch(String branchName) throws IOException {
        File newBranch = new File(init.branches, branchName);
        Head currentHead = new Head(init.head);
        File branchFile = new File(init.branches, currentHead.getCurrentBranch());
        LinkedHashMap commitTreed = Utils.readObject(branchFile, LinkedHashMap.class);
        if (newBranch.exists()) {
            System.out.println("A branch with that name already exists.");
        }
        Branch newbranchVar = new Branch(branchName, commitTreed);

    }


}
