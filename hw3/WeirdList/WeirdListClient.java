/** Functions to increment and sum the elements of a WeirdList. */
class WeirdListClient {

    /** Return the result of adding N to each element of L. */
    static WeirdList add(WeirdList L, int n) {
        return L.map(x -> n + x);
    }

    /** Return the sum of all the elements in L. */
    static int sum(WeirdList L) {
        //want to apply some sort of sum function and return the
        //resulting number. Create a new class that extents unary function
        Summer summr = new Summer();
        L.map(summr);
        return summr.sum; // TODO: REPLACE THIS LINE
    }

    /* IMPORTANT: YOU ARE NOT ALLOWED TO USE RECURSION IN ADD AND SUM
     *
     * As with WeirdList, you'll need to add an additional class or
     * perhaps more for WeirdListClient to work. Again, you may put
     * those classes either inside WeirdListClient as private static
     * classes, or in their own separate files.

     * You are still forbidden to use any of the following:
     *       if, switch, while, for, do, try, or the ?: operator.
     *
     * HINT: Try checking out the IntUnaryFunction interface.
     *       Can we use it somehow?
     */
    private static class Summer implements IntUnaryFunction{
        public int sum = 0;

        @Override
        public int apply(int x) {
            sum += x;
            return x;
        }

    }
}
