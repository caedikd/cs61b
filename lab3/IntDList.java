/**
 * Scheme-like pairs that can be used to form a list of integers.
 *
 * @author P. N. Hilfinger; updated by Linda Deng (9/1/2021)
 */
public class IntDList {

    /**
     * First and last nodes of list.
     */
    protected DNode _front, _back;

    /**
     * An empty list.
     */
    public IntDList() {
        _front = _back = null;
    }

    /**
     * @param values the ints to be placed in the IntDList.
     */
    public IntDList(Integer... values) {
        _front = _back = null;
        for (int val : values) {
            insertBack(val);
        }
    }

    /**
     * @return The first value in this list.
     * Throws a NullPointerException if the list is empty.
     */
    public int getFront() {
        return _front._val;
    }

    /**
     * @return The last value in this list.
     * Throws a NullPointerException if the list is empty.
     */
    public int getBack() {
        return _back._val;
    }

    /**
     * @return The number of elements in this list.
     */
    public int size() {
        DNode node = _front;
        int count = 0; //parse through the nodes and count?
        if (node == null && _back == null) {
            return count;
        }
        else {
            while (node != null) {
                node = node._next;
                count += 1;
            }
        }
       /* else {
            node = _back;
            while(node != null) {
                node = node._prev;
                count += 1;
            }*/
        //}
        return count;
    }

    /**
     * @param index index of node to return,
     *          where index = 0 returns the first node,
     *          index = 1 returns the second node,
     *          index = -1 returns the last node,
     *          index = -2 returns the second to last node, and so on.
     *          You can assume index will always be a valid index,
     *              i.e 0 <= index < size for positive indices
     *          and -size <= index <= -1 for negative indices.
     * @return The node at index index
     */
    private DNode getNode(int index) {
        DNode node = _front;
        if (index >= 0) {
            node = _front;
            while (node != null && node._next != null && index-- != 0){
                node = node._next;
            }
        }
        else if (index < 0){
            node = _back;
            while (node != null && node._prev != null && index++ != -1){
                node = node._prev;
            }
        }
        return node;
    }

    /**
     * @param index index of element to return,
     *          where index = 0 returns the first element,
     *          index = 1 returns the second element,
     *          index = -1 returns the last element,
     *          index = -2 returns the second to last element, and so on.
     *          You can assume index will always be a valid index,
     *              i.e 0 <= index < size for positive indices
     *          and -size <= index <= -1 for negative indices.
     * @return The integer value at index index
     */
    public int get(int index) {
        DNode node = getNode(index);
        if (node != null){
            return node._val;
        }
        return -1;
    }

    /**
     * @param d value to be inserted in the front
     */
    public void insertFront(int d) {
        //create new Dnode, then add it to back, then update back
        DNode newNode = new DNode(d);
        /*if (_back != null && _front != null){
            newNode._next = _front;
            _front._prev = newNode;
            _front = newNode;
        }
        else{
            _front = _back = newNode;
        }*/
        if (_front == null){
            _front = _back = newNode;
        }
        else {
            newNode._next = _front;
            _front._prev = newNode;
            _front = newNode;
        }

    }

    /**
     * @param d value to be inserted in the back
     */
    public void insertBack(int d) {
        //create new Dnode, then add it to back, then update back
        DNode newNode = new DNode(d);
        if (_back != null && _front != null){
            newNode._prev = _back;
            _back._next = newNode;
            _back = newNode;
        }
        else{
            _front = _back = newNode;
        }

    }

    /**
     * @param d     value to be inserted
     * @param index index at which the value should be inserted
     *              where index = 0 inserts at the front,
     *              index = 1 inserts at the second position,
     *              index = -1 inserts at the back,
     *              index = -2 inserts at the second to last position, etc.
     *              You can assume index will always be a valid index,
     *              i.e 0 <= index <= size for positive indices
     *              and -(size+1) <= index <= -1 for negative indices.
     */
    public void insertAtIndex(int d, int index) {
        /* inserts the new element at specified index ->
        get thing at index and add the new element at the previous of that and
        at the next of index - 1
         */
        DNode newNode = new DNode(d);
        if (index == 0){
            insertFront(d);
        }
        else if (index == -1 || index == size()){
            insertBack(d);
        }
        else if (index > 0){
            DNode oldNode = getNode(index);
            DNode prevNode = getNode(index - 1);
            prevNode = oldNode._prev;
            newNode._prev = prevNode;
            newNode._next = oldNode;
            oldNode._prev = newNode;
            prevNode._next = newNode;

        }
        else{
            if (Math.abs(index) >= size()){
                insertFront(d);
            }
            else{
                DNode oldNode = getNode(index);
                DNode prevNode = getNode(index + 1);
                prevNode = oldNode._next;
                newNode._next = prevNode;
                newNode._prev = oldNode;
                oldNode._next = newNode;
                prevNode._prev = newNode;
            }

        }

         /*
        } */
        //else if (index == 0) {
         //   insertFront(d);
       // }
        /*
        else if (index == -1){
            insertBack(d);
        }
        else{
            newNode = getNode(index)._next;
            getNode(index + 1)._prev = newNode;
        }*/

    }

    /**
     * Removes the first item in the IntDList and returns it.
     * Assume `deleteFront` is never called on an empty IntDList.
     *
     * @return the item that was deleted
     */
    public int deleteFront() {
        int val = get(0);
        if (_front._next == null) {
            _front = _back = null;
        } else {
            _front = _front._next;
            _front._prev = null;
        }

        return val;
    }

    /**
     * Removes the last item in the IntDList and returns it.
     * Assume `deleteBack` is never called on an empty IntDList.
     *
     * @return the item that was deleted
     */
    public int deleteBack() {
        int val = get(-1);
        if (_back._prev == null){
            _front = _back = null;
        }
        else {
            _back = _back._prev;
            _back._next = null;
        }

        return val;
    }

    /**
     * @param index index of element to be deleted,
     *          where index = 0 returns the first element,
     *          index = 1 will delete the second element,
     *          index = -1 will delete the last element,
     *          index = -2 will delete the second to last element, and so on.
     *          You can assume index will always be a valid index,
     *              i.e 0 <= index < size for positive indices
     *              and -size <= index <= -1 for negative indices.
     * @return the item that was deleted
     */
    public int deleteAtIndex(int index) {
        // TODO: Implement this method and return correct value
        return 0;
    }

    /**
     * @return a string representation of the IntDList in the form
     * [] (empty list) or [1, 2], etc.
     * Hint:
     * String a = "a";
     * a += "b";
     * System.out.println(a); //prints ab
     */
    public String toString() {
        if (size() == 0) {
            return "[]";
        }
        String str = "[";
        DNode curr = _front;
        for (; curr._next != null; curr = curr._next) {
            str += curr._val + ", ";
        }
        str += curr._val +"]";
        return str;
    }

    /**
     * DNode is a "static nested class", because we're only using it inside
     * IntDList, so there's no need to put it outside (and "pollute the
     * namespace" with it. This is also referred to as encapsulation.
     * Look it up for more information!
     */
    static class DNode {
        /** Previous DNode. */
        protected DNode _prev;
        /** Next DNode. */
        protected DNode _next;
        /** Value contained in DNode. */
        protected int _val;

        /**
         * @param val the int to be placed in DNode.
         */
        protected DNode(int val) {
            this(null, val, null);
        }

        /**
         * @param prev previous DNode.
         * @param val  value to be stored in DNode.
         * @param next next DNode.
         */
        protected DNode(DNode prev, int val, DNode next) {
            _prev = prev;
            _val = val;
            _next = next;
        }
    }

}
