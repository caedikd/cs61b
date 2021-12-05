package gitlet;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.io.File;
import java.util.HashMap;

public class Branch implements Serializable {

    static File _activeBranch = new File(init.branches, "master");
    static LinkedHashMap<String, Branch> _activePointer;
    private String _head;

    static LinkedHashMap<String, Commit> branches;

    public Branch(String name, LinkedHashMap commitTree) {
//        _activeBranch = new File(init.branches,  name);
        _head = "*" + name;
        if (!_activeBranch.exists()) {
            _activePointer = new LinkedHashMap<>();
        }
        _activePointer.put(_head, this);
        Utils.writeObject(_activeBranch, _activePointer);
    }

    public static void change() {

    }

    public String toString() {
        return _head;
    }
}
