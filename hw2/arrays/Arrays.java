package arrays;
import java.util.ArrayList;
import java.util.*;
import java.util.Collections;
import java.util.List;



/* NOTE: The file Arrays/Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2 */

/** Array utilities.
 *  @author
 */
class Arrays {

    /* C1. */
    /** Returns a new array consisting of the elements of A followed by the
     *  the elements of B. */
    static int[] catenate(int[] A, int[] B) {
        int[] newArr = new int[A.length + B.length];
        for(int i = 0; i < A.length; i++){
            newArr[i] = A[i];
        }
        for(int i = A.length; i < newArr.length; i++){
            newArr[i] = B[i - A.length];
        }
        return newArr;
    }

    /* C2. */
    /** Returns the array formed by removing LEN items from A,
     *  beginning with item #START. If the start + len is out of bounds for our array, you
     *  can return null.
     *  Example: if A is [0, 1, 2, 3] and start is 1 and len is 2, the
     *  result should be [0, 3]. */
    static int[] remove(int[] A, int start, int len) {
        int[] newArr = new int[A.length - len];
        if((start + len) > A.length){
            return null;
        }
        int i = 0;
        for(; i < start; i++){
            newArr[i] = A[i];
        }
        i += len;
        for(; i < A.length; i++){
            newArr[i - len] = A[i];
        }
        return newArr;
    }

    /* C3. */
    /** Returns the array of arrays formed by breaking up A into
     *  maximal ascending lists, without reordering.
     *  For example, if A is {1, 3, 7, 5, 4, 6, 9, 10}, then
     *  returns the three-element array
     *  {{1, 3, 7}, {5}, {4, 6, 9, 10}}. */
    static int[][] naturalRuns(int[] A) {
        //few first cases maybe?
        ArrayList<Integer> finalArr = new ArrayList<Integer>();
        for (int i = 0; i < A.length; i++)
            finalArr.add(A[i]);
        int lists = 0;

        if (A.length == 0){
            return new int[0][0];
        }

        if (A.length == 1){
            return new int[][] {{A[0]}};
        }

        //count the amount of elements in the array
        for(int i = 1; i < A.length; i++){
            if(A[i] <= A[i - 1]){
                lists++;
            }
        }
        lists++;
        ArrayList<List> newList = new ArrayList<List>();

        int j = 0;
        for (int i = 1; i < A.length; i += 1) {
            if (finalArr.get(i) <= finalArr.get(i-1)) {
                newList.add(finalArr.subList(j, i));
                j = i;
            }
            if (i == A.length - 1){
                newList.add(finalArr.subList(j, i+1));
            }
        }

        int[] arr = finalArr.stream().mapToInt(i -> i).toArray();
        int [][] arr2 = new int[lists][];
        for (int i = 0; i < newList.size(); i += 1) {
            arr2[i] = newList.get(i).stream().mapToInt(b-> (int) b).toArray();
        }
        System.out.println(finalArr);

        System.out.println(newList);
        return arr2;


    }
}
