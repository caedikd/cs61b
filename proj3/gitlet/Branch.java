package gitlet;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.io.File;
import java.util.List;

/**
 * Branch class.
 * @author caedi
 */
public class Branch implements Serializable {
    /**
     * Bruh.
     */
    private static File _activeBranch = new
            File(String.valueOf(Init.getBranches()));

    /**
     * Bruh.
     */
    private static LinkedHashMap<String, Commit> _commitTree;

    /**
     * Bruh.
     * @param commitTree ccT
     * @param name name
     */
    public Branch(String name, LinkedHashMap commitTree) throws IOException {
        _activeBranch = new File(_activeBranch,  name);
        if (commitTree == null) {
            _commitTree = new LinkedHashMap<>();
        } else {
            _commitTree = commitTree;
        }
        Utils.writeObject(_activeBranch, _commitTree);

    }

    /**
     * Bruh.
     * @param commitTree bname
     *
     */
    public static void updateTree(LinkedHashMap commitTree) {
        List<String> lKeys = new ArrayList<String>(commitTree.keySet());
        Head head = Utils.readObject(Init.getHead(), Head.class);
        File branchFile = new File(Init.getBranches(), head.getCurrentBranch());
        LinkedHashMap commitTreed = Utils.readObject(branchFile,
                LinkedHashMap.class);
        commitTreed.put(lKeys.get(0), commitTree.get(lKeys.get(0)));
        Utils.writeObject(branchFile, commitTreed);
    }

    /**
     * Bruh.
     * @param branchName bname
     * @throws IOException
     */
    public static void newBranch(String branchName) throws IOException {
        File newBranch = new File(Init.getBranches(), branchName);
        Head currentHead = new Head(Init.getHead());
        File branchFile = new File(Init.getBranches(),
                currentHead.getCurrentBranch());
        LinkedHashMap commitTreed = Utils.readObject(branchFile,
                LinkedHashMap.class);
        if (newBranch.exists()) {
            System.out.println("A branch with that name"
                    + " already exists.");
        }
        Branch newbranchVar = new Branch(branchName, commitTreed);

    }


}
