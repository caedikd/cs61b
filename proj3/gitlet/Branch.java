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
//    private String _head;

    static LinkedHashMap<String, Commit> branches;

    public Branch(String name, LinkedHashMap commitTree) throws IOException {
        _activeBranch = new File(_activeBranch,  name);
        _commitTree = new LinkedHashMap<>();
        Utils.writeObject(_activeBranch, _commitTree);

    }

//    public void checkoutBranch(String branchName) {
//        File branchNamed = new File(init.branches, branchName);
//        if (branchNamed.exists()) {
//            Head newHead = new Head(init.head);
//            newHead.setCurrentBranch(branchName);
//            File currentBranchFile = new File(init.branches, branchName);
//            //currentBranch = currentBranchFile.getName();
//            ArrayList<String> shas = Utils.readObject(currentBranchFile, ArrayList.class);
//        }
//    }

    public static void updateTree(LinkedHashMap commitTree) {
        List<String> lKeys = new ArrayList<String>(commitTree.keySet());
        Head head = Utils.readObject(init.head, Head.class);
        File branchFile = new File(init.branches, head.getCurrentBranch());
        LinkedHashMap commitTreed = Utils.readObject(branchFile, LinkedHashMap.class);
//        if (commitTreed != null) {
            commitTreed.put(lKeys.get(0), commitTree.get(lKeys.get(0)));
            Utils.writeObject(branchFile, commitTreed);





    }

//    public String toString() {
//        //return _head;
//    }
}
