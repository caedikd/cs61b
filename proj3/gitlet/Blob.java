package gitlet;

import java.io.File;
import java.io.Serializable;

/** File contents that are serializable. */
public class Blob implements Serializable {

    static File newBlob;
    static String _fileName;
    public byte[] _code;

    /** This is the SHA ID */
    static String _ID;

    public Blob(String fileName, byte[] code, String ID) {
        _fileName = fileName;
        _code = code;
        _ID = ID;
    }

}
