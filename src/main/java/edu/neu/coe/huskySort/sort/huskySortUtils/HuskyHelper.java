package edu.neu.coe.huskySort.sort.huskySortUtils;

import edu.neu.coe.huskySort.sort.BaseHelper;
import edu.neu.coe.huskySort.sort.Helper;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Helper class for sorting methods with special technique of HuskySort.
 * IF you want to count compares and swaps then you need to extend InstrumentingHelper.
 * <p>
 *
 * @param <X> the underlying type (must be Comparable).
 */
public class HuskyHelper<X extends Comparable<X>> implements Helper<X> {

    // Delegate methods on helper

    public boolean less(X v, X w) {
        return helper.less(v, w);
    }

    public void swap(X[] xs, int lo, int hi, int i, int j) {
        helper.swap(xs, lo, hi, i, j);
    }

    public boolean sorted(X[] xs) {
        return helper.sorted(xs);
    }

    public int inversions(X[] xs, int from, int to) {
        return helper.inversions(xs, from, to);
    }

    public void postProcess(X[] xs) {
        helper.postProcess(xs);
    }

    public X[] random(Class<X> clazz, Function<Random, X> f) {
        return helper.random(clazz, f);
    }

    public String getDescription() {
        return helper.getDescription();
    }

    public int getN() {
        return helper.getN();
    }

    public void close() {
        helper.close();
    }

    public boolean instrumented() {
        return helper.instrumented();
    }

    public int compare(X[] xs, int lo, int hi, int i, int j) {
        return helper.compare(xs, lo, hi, i, j);
    }

    /**
     * Method to perform xs stable swap, i.e. between xs[i] and xs[i-1]
     *
     * @param xs the array of X elements.
     * @param lo the low index of interest.
     * @param hi one above the high index of interest.
     * @param i  the index of the higher of the adjacent elements to be swapped.
     */
    public void swapStable(X[] xs, int lo, int hi, int i) {
        helper.swapStable(xs, lo, hi, i);
    }

    /**
     * Constructor to create a HuskyHelper
     *
     * @param helper     the Helper.
     * @param coder      the coder to be used.
     * @param postSorter the postSorter Consumer function.
     * @param makeCopy   explicit setting of the makeCopy value used in sort(X[] xs)
     */
    public HuskyHelper(Helper<X> helper, HuskyCoder<X> coder, Consumer<X[]> postSorter, boolean makeCopy) {
        this.helper = helper;
        this.coder = coder;
        longs = new long[helper.getN()];
        this.postSorter = postSorter;
        this.makeCopy = makeCopy;
    }

    /**
     * Constructor to create a Helper
     *
     * @param description the description of this Helper (for humans).
     * @param n           the number of elements expected to be sorted. The field n is mutable so can be set after the constructor.
     * @param seed        the seed for the random number generator
     * @param makeCopy    explicit setting of the makeCopy value used in sort(X[] xs)
     */
    public HuskyHelper(String description, int n, HuskyCoder<X> coder, Consumer<X[]> postSorter, long seed, boolean makeCopy) {
        this(new BaseHelper<>(description, n, seed), coder, postSorter, makeCopy);
    }

    /**
     * Constructor to create a Helper with random seed.
     *
     * @param description the description of this Helper (for humans).
     * @param n           the number of elements expected to be sorted. The field n is mutable so can be set after the constructor.
     */
    public HuskyHelper(String description, int n, HuskyCoder<X> coder, Consumer<X[]> postSorter) {
        this(description, n, coder, postSorter, System.currentTimeMillis(), false);
    }

    // TODO this needs to be unit-tested
    public HuskyCoder<X> getCoder() {
        return coder;
    }

    public void setN(int n) {
        if (n != getN()) longs = new long[n];
        helper.setN(n);
    }

    public void initLongArray(X[] array) {
        for (int i = 0; i < array.length; i++) longs[i] = coder.huskyEncode(array[i]);
    }

    // CONSIDER having a method less which compares the longs rather than having direct access to the longs array in sub-classes
    public void swap(X[] xs, int i, int j) {
        // Swap longs
        long temp1 = longs[i];
        longs[i] = longs[j];
        longs[j] = temp1;
        // Swap xs
        X temp2 = xs[i];
        xs[i] = xs[j];
        xs[j] = temp2;
    }

    public Consumer<X[]> getPostSorter() {
        return postSorter;
    }

    public boolean isMakeCopy() {
        return makeCopy;
    }

    public long[] getLongs() {
        return longs;
    }

    private final HuskyCoder<X> coder;
    protected long[] longs;
    private final Consumer<X[]> postSorter;
    private final boolean makeCopy;
    protected final Helper<X> helper;

}
