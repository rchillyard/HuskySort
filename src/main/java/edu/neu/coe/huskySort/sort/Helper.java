package edu.neu.coe.huskySort.sort;

import java.util.Random;
import java.util.function.Function;

/**
 * CONSIDER having the concept of current sub-array, then we could dispense with the lo, hi parameters.
 *
 * @param <X>
 */
public interface Helper<X extends Comparable<X>> {

    /**
     * @return true if this is an instrumented Helper.
     */
    boolean instrumented();

    /**
     * Compare elements i and j of xs within the subarray lo..hi
     *
     * @param xs the array.
     * @param lo the lowest index of interest (only used for checking).
     * @param hi one more than the highest index of interest (only used for checking).
     * @param i  one of the indices.
     * @param j  the other index.
     * @return the result of comparing xs[i] to xs[j]
     */
    int compare(X[] xs, int lo, int hi, int i, int j);

    /**
     * Compare values v and w and return true if v is less than w.
     *
     * @param v the first value.
     * @param w the second value.
     * @return true if v is less than w.
     */
    boolean less(X v, X w);

    /**
     * Method to perform a general swap, i.e. between xs[i] and xs[j]
     *
     * @param xs the array of X elements.
     * @param lo the low index of interest.
     * @param hi one above the high index of interest.
     * @param i  the index of the lower of the elements to be swapped.
     * @param j  the index of the higher of the elements to be swapped.
     */
    void swap(X[] xs, int lo, int hi, int i, int j);

    /**
     * Method to perform xs stable swap, i.e. between xs[i] and xs[i-1]
     *
     * @param xs the array of X elements.
     * @param lo the low index of interest.
     * @param hi one above the high index of interest.
     * @param i  the index of the higher of the adjacent elements to be swapped.
     */
    default void swapStable(X[] xs, int lo, int hi, int i) {
        swap(xs, lo, hi, i - 1, i);
    }

    /**
     * Method to fix a potentially unstable inversion.
     *
     * @param xs the array of X elements.
     * @param lo the low index of interest.
     * @param hi one above the high index of interest.
     * @param i  the index of the lower of the elements to be swapped.
     * @param j  the index of the higher of the elements to be swapped.
     */
    default void fixInversion(X[] xs, int lo, int hi, int i, int j) {
        if (less(xs[j], xs[i])) swap(xs, lo, hi, i, j);
    }

    /**
     * Method to fix a stable inversion.
     *
     * @param xs the array of X elements.
     * @param lo the low index of interest.
     * @param hi one above the high index of interest.
     * @param i  the index of the higher of the adjacent elements to be swapped.
     */
    default void fixInversion(X[] xs, int lo, int hi, int i) {
        if (less(xs[i], xs[i - 1])) swapStable(xs, lo, hi, i);
    }

    /**
     * Return true if xs is sorted, i.e. has no inversions.
     *
     * @param xs an array of Xs.
     * @return true if there are no inversions, else false.
     */
    boolean sorted(X[] xs);

    /**
     * Count the number of inversions of this array.
     *
     * @param xs   an array of Xs.
     * @param from the lower bound of the sub-array.
     * @param to   the higher bound of the sub-array (i.e. the first element NOT included).
     * @return the number of inversions.
     */
    int inversions(X[] xs, int from, int to);

    /**
     * Post-process the given array, i.e. after sorting has been completed.
     *
     * @param xs an array of Xs.
     */
    void postProcess(X[] xs);

    /**
     * Method to generate an array of randomly chosen X elements.
     * @param clazz the class of X.
     * @param f a function which takes a Random and generates a random value of X.
     * @return an array of X of length determined by the current value according to setN.
     */
    X[] random(Class<X> clazz, Function<Random, X> f);

    /**
     * @return the description of this Helper.
     */
    String getDescription();

    /**
     * Set the size of the array to be managed by this Helper.
     * @param n the size to be managed.
     * @throws RuntimeException if the size hasn't been set.
     */
    void setN(int n);

    /**
     * Get the current value of N.
     * @return the value of N.
     */
    int getN();

    /**
     * Close this Helper, freeing up any resources used.
     */
    void close();
}
