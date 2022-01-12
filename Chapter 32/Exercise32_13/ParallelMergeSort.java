import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

/**
 * <h1>ParallelMergeSort</h1>
 *
 * <p>This class will create two lists one of which will be sorted by a MergeSort and the other
 * by a ParallelMergeSort. The program will then print how long each sort took. The class has
 * been edited to use Generic object arrays to be able to sort generically.</p>
 *
 * <p>Created: Unknown   Edited: 01/12/2022</p>
 *
 * @author Unknown, Rhett Boatright
 */
public class ParallelMergeSort {
    public static void main(String[] args) {
        final int SIZE = 7000000;
        Integer[] list1 = new Integer[SIZE]; //Changed to be Integer class from int
        Integer[] list2 = new Integer[SIZE]; //Changed to be Integer class from int

        for (int i = 0; i < list1.length; i++)
            list1[i] = list2[i] = (int)(Math.random() * 10000000); //Set each list with random integers

        long startTime = System.currentTimeMillis(); //Start time
        parallelMergeSort(list1); // Invoke parallel merge sort
        long endTime = System.currentTimeMillis(); //End time
        System.out.println("\nParallel time with "
                + Runtime.getRuntime().availableProcessors() +
                " processors is " + (endTime - startTime) + " milliseconds");

        startTime = System.currentTimeMillis(); //Start time
        MergeSort.mergeSort(list2); // MergeSort is in Listing 24.5
        endTime = System.currentTimeMillis(); //End time
        System.out.println("\nSequential time is " +
                (endTime - startTime) + " milliseconds");
    }

    /**
     * This method is a generic parallelMergeSort that will allow for other Object types.
     * @param list (E; Object array from the main method)
     * @param <E> (Comparable; generic value to allow for other types of objects)
     */
    public static<E extends Comparable<E>> void parallelMergeSort(E[] list){
        RecursiveAction mainTask = new SortTask<E>(list); //Modified to be generic
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(mainTask);
    }

    /**
     * <h1>SortTask</h1>
     *
     * <p>This class is a generic version of SortTask which allows for other object types to be thrown into it.</p>
     *
     * <p>Created: 01/12/2022</p>
     *
     * @author Rhett Boatright
     * @param <E> (Comparable; generic value)
     */
    private static class SortTask<E extends Comparable<E>> extends RecursiveAction {
        private final int THRESHOLD = 500; //Sort normally if less than 500
        private E[] list, firstHalf, secondHalf; //Generic lists

        SortTask(E[] list) {
            this.list = list;
        }

        @Override
        /**
         * This method has been modified to allow for the use of generic values.
         */
        protected void compute() {
            if (list.length < THRESHOLD)
                java.util.Arrays.sort(list);
            else {
                // Obtain the first half
                firstHalf = (E[])(new Comparable[list.length / 2]);
                System.arraycopy(list, 0, firstHalf, 0, list.length / 2);

                // Obtain the second half
                int secondHalfLength = list.length - list.length / 2;
                secondHalf = (E[])(new Comparable[secondHalfLength]);
                System.arraycopy(list, list.length / 2,
                        secondHalf, 0, secondHalfLength);

                // Recursively sort the two halves
                invokeAll(new SortTask(firstHalf),
                        new SortTask(secondHalf));

                // Merge firstHalf with secondHalf into list
                MergeSort.merge(firstHalf, secondHalf, list);
            }
        }
    }
}