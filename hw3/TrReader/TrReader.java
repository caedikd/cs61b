import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;


/** Translating Reader: a stream that is a translation of an
*  existing reader.
*  @author Caedi Seim
*
*  NOTE: Until you fill in the right methods, the compiler will
*        reject this file, saying that you must declare TrReader
* 	     abstract.  Don't do that; define the right methods instead!
*/
public class TrReader extends Reader {
    /** A new TrReader that produces the stream of characters produced
     *  by STR, converting all characters that occur in FROM to the
     *  corresponding characters in TO.  That is, change occurrences of
     *  FROM.charAt(i) to TO.charAt(i), for all i, leaving other characters
     *  in STR unchanged.  FROM and TO must have the same length. */
    public TrReader(Reader str, String from, String to) {
        // a TrReader's source of characters is some other Reader passed into
        //the TrReaders constructor: that is this
        super(str);
        this.to = to;
        this.from = from;
        this.strs = str;
    }

    public Reader strs;
    public String from;
    public String to;

    @Override
    public void close() throws IOException {
        strs.close();
    }


    @Override
    public int read(char[] chars, int i, int i1) throws IOException {
        int j = i;
        int act = strs.read(chars, i, i1);
        for (; j < i + i1; j++) {
            int in = from.indexOf(chars[j]);
            if (in == -1) {
                chars[j] = chars[j];
            }
            else {
                chars[j] = to.charAt(in);
            }

        }
        return act;

    }


        //int red = 0;
        //int index;


        // Information goes from the reader supplied
        // gets translated, and stored in cbuf[off] through
        // cbuf[off + len]

        // say off were 2, and len were 10
        // e.g. c[2] = translated 0th character
        // c[3] = translated 1st character



    /*
    use your Reader str to read into the char[], then using the
     appropriate indices, make the conversions necessary from
      _from to _to.

    public int read(char[] cbuf, int off, int len) throws IOException {
                Objects.checkFromIndexSize(off, len, cbuf.length);
                this.ensureOpen();
                return len == 0 ? 0 : -1;
                    or if len == 0 return 0 else return -1
            }
     */



}
