import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/** A set of String values.
 *  @author Caedi Seim
 */
class ECHashStringSet implements StringSet {

    public LinkedList<String>[] _buckets;

    public int _defaultBucket;

    public ECHashStringSet() {
    }

    public ECHashStringSet(int _defaultBucket) {
        _buckets = (LinkedList<String>[]) new LinkedList[_defaultBucket];
        for (int i = 0; i < _buckets.length; i++) {
            _buckets[i] = new LinkedList<String>();
        }
    }

    // resize the hash table as soon as the load factor exceeds 5.
    @Override
    public void put(String s) {
        //first compute the hashcode using wrap
        //figure out which index i bucket to go to
        //get linked list at buckets and add it to that
        //if we've reached the load limit, we should resize
        int i = whichBucket(s);
        if (!_buckets[i].contains(s)) {
            _buckets[i].add(s);
        }

    }

    @Override
    public boolean contains(String s) {
        //figure out which index i bucket s would be in
        // get linked list at buckets[i]
        // find out if that linked list contains S
        int i = whichBucket(s);
        if (_buckets[i].contains(s)) {
            return true;
        }
        return false;
    }

    private int whichBucket(String s) {
        //returns which bucket i the string s should be in
        // call the default string hashcode, as in s.hashCode()
        //figure out a way to make that default hashcode wrap to fit within
        // the range of 0 to num buckets
        if (loadFactor() >= 5) {
            resized();
        }
        int wrapped = (s.hashCode() & 0x7fffffff) % _buckets.length;
        return wrapped;
    }

    private void resized() {
        int size = _buckets.length * 2;
        _defaultBucket = size;
        ECHashStringSet copy = new ECHashStringSet();

        for (int i = 0; i < _buckets.length; i++) {
            for (String s: _buckets[i]) {
                copy.put(s);
            }
        }
        _buckets = copy._buckets;
    }

    private int loadFactor() {
        int count = 0;
        for (int i = 0; i < _buckets.length; i++) {
            count += _buckets[i].size();
        }
        return count / _buckets.length;
    }

    @Override
    public List<String> asList() {
        ArrayList<String> end = new ArrayList<>();
        for (int i = 0; i < _buckets.length; i++) {
            if (_buckets[i] != null) {
                for (int j = 0; j < _buckets[i].size(); j++) {
                    end.add(_buckets[i].get(j));
                }
            }
        }
        return end;
    }


}
