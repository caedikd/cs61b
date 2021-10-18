import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Implementation of a BST based String Set.
 * @author Caedi Seim
 */
public class BSTStringSet implements StringSet, Iterable<String> {
    /** Creates a new empty set. */
    public BSTStringSet() {
        _root = null;
    }

    //implement a set of string stored in a bst


    @Override
    public void put(String s) {
        //adds the string S to the string set
        //we are provided with private inner class Node
        /*
            if something is already in there, do nothing
            else check the alphabetically where it belongs
            if there is an empty space for it add it
         */
        if (_root == null) {
           _root = new Node(s);
        }
        else {
            Node start = helper(s);
            if (start != null) {
                if (s.compareTo(start.s) < 0) {
                    start.left = new Node(s);
                }
                else if (s.compareTo(start.s) > 0) {
                    start.right = new Node(s);
                }
            }
        }

    }

    @Override
    public boolean contains(String s) {
        /*
           first start at the root, check alphabetically
           where it goes, before the root goes left, after
           goes right

           no recursion
           use private helper methods
         */

        if (_root != null) {
            return true;
        }
        else {
            Node start = helper(s);
            if (s.compareTo(start.s) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> asList() {
        //this is in the skeleton
        ArrayList<String> list = new ArrayList<>();
        BSTIterator node = new BSTIterator(_root);
        while (node.hasNext()) {
            list.add(node.next());
        }
        return list;
    }

    public Node helper(String s) {
        Node start = _root;
        boolean go = true;
        while (go) {
            Node newNode;
            if (s.compareTo(start.s) < 0) {
                newNode = start.left;
            }
            else if (s.compareTo(start.s) > 0) {
                newNode = start.right;
            }
            else {
                return start;
            }
            if (newNode == null) {
                return start;
            }
            else {
                start = newNode;
            }
        }
        return null;
    }


    /** Represents a single Node of the tree. */
    private static class Node {
        /** String stored in this Node. */
        private String s;
        /** Left child of this Node. */
        private Node left;
        /** Right child of this Node. */
        private Node right;

        /** Creates a Node containing SP. */
        Node(String sp) {
            s = sp;
        }
    }

    /** An iterator over BSTs. */
    private static class BSTIterator implements Iterator<String> {
        /** Stack of nodes to be delivered.  The values to be delivered
         *  are (a) the label of the top of the stack, then (b)
         *  the labels of the right child of the top of the stack inorder,
         *  then (c) the nodes in the rest of the stack (i.e., the result
         *  of recursively applying this rule to the result of popping
         *  the stack. */
        private Stack<Node> _toDo = new Stack<>();

        /** A new iterator over the labels in NODE. */
        BSTIterator(Node node) {
            addTree(node);
        }

        @Override
        public boolean hasNext() {
            return !_toDo.empty();
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Node node = _toDo.pop();
            addTree(node.right);
            return node.s;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /** Add the relevant subtrees of the tree rooted at NODE. */
        private void addTree(Node node) {
            while (node != null) {
                _toDo.push(node);
                node = node.left;
            }
        }
    }

    @Override
    public Iterator<String> iterator() {
        return new BSTIterator(_root);
    }

    // FIXME: UNCOMMENT THE NEXT LINE FOR PART B
    // @Override
    public Iterator<String> iterator(String low, String high) {
        return null;  // FIXME: PART B (OPTIONAL)
    }


    /** Root node of the tree. */
    private Node _root;
}
