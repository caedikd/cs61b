package gitlet;
import java.util.LinkedHashMap;
import java.io.File;
import java.util.HashMap;

public class Branch {

    static File _newBranch;
    static LinkedHashMap _activePointer;
    static String _head;

    static LinkedHashMap<String, Commit> branches;

    public Branch(String name, LinkedHashMap commitTree) {
        _newBranch = new File(init.branches, name);
        _head = "*" + name;
        _activePointer = commitTree;
        Utils.writeObject(_newBranch, commitTree);
    }

    public static void change() {

    }
}
