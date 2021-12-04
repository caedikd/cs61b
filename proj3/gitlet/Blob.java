package gitlet;

import java.io.Serializable;

/** File contents that are serializable. */
public class Blob implements Serializable {

    static String _fileName;
    static byte[] _code;
    static String _ID;

    public Blob(String fileName, byte[] code, String ID) {
        _fileName = fileName;
        _code = code;
        _ID = ID;
    }

}
