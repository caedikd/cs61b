package gitlet;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.io.File;

public class Branch implements Serializable {

    static File _activeBranch = new File(String.valueOf(init.branches));
    static LinkedHashMap<String, Branch> _activePointer;
    static ArrayList<String> _log;
//    private String _head;

    static LinkedHashMap<String, Commit> branches;

    public Branch(String name, ArrayList<String> log) throws IOException {
        _activeBranch = new File(_activeBranch,  name);
        _log = log;
        //_head = "*" + name;
        //somewhere to save the name?
//        if (!_activeBranch.exists()) {
//            _activePointer = new LinkedHashMap<>();
//        }
//        _activePointer.put(_head, this);
//        Utils.writeObject(_activeBranch, _activePointer);
        _activeBranch.createNewFile();
        if (_log != null) {
            Utils.writeContents(_activeBranch, log);
        }
    }

    public void checkoutBranch(String branchName) {
        File branchNamed = new File(init.branches, branchName);
        if (branchNamed.exists()) {
            Head newHead = new Head(init.head);
            newHead.setCurrentBranch(branchName);
            File currentBranchFile = new File(init.branches, branchName);
            //currentBranch = currentBranchFile.getName();
            ArrayList<String> shas = Utils.readObject(currentBranchFile, ArrayList.class);
        }
    }

    public static void updateLog() {
    }

//    public String toString() {
//        //return _head;
//    }
}
