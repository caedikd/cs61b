package gitlet;

import java.io.File;
import java.io.Serializable;

/** File contents that are serializable.
 * @author caedi
 * */
public class Blob implements Serializable {

    /** This is the SHA ID. */
    private static File newBlob;

    /** This is the SHA ID. */
    private static String _fileName;

    /** This is the SHA ID. */
    private byte[] _code;

    /** This is the SHA ID. */
    private static String _iD;

    /** gets cod.
     * @return byte.
     * */
    public byte[] codeGetter() {
        return _code;
    }

    /**
     * Bruh.
     * @param fileName fn
     * @param code cd
     * @param iD id
     */
    public Blob(String fileName, byte[] code, String iD) {
        _fileName = fileName;
        _code = code;
        _iD = iD;
    }

}
