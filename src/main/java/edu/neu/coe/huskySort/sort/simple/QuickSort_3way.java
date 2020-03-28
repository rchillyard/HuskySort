package edu.neu.coe.huskySort.sort.simple;

import edu.neu.coe.huskySort.sort.BaseHelper;
import edu.neu.coe.huskySort.sort.Helper;
import edu.neu.coe.huskySort.sort.SortWithHelper;

import java.util.Arrays;

public class QuickSort_3way<X extends Comparable<X>> extends SortWithHelper<X> {
    /**
     * Constructor for QuickSort_3way
     *
     * @param helper an explicit instance of Helper to be used.
     */
    public QuickSort_3way(BaseHelper<X> helper) {
        super(helper);
    }

    /**
     * Constructor for QuickSort_3way
     *
     * @param N            the number elements we expect to sort.
     * @param instrumented whether or not we want an instrumented helper class.
     */
    public QuickSort_3way(int N, boolean instrumented) {
        super(DESCRIPTION, N, instrumented);
    }

    public QuickSort_3way() {
        this(new BaseHelper<>(DESCRIPTION));
    }

    static class Partition {
        final int lt;
        final int gt;

        Partition(int lt, int gt) {
            this.lt = lt;
            this.gt = gt;
        }
    }

    public X[] sort(X[] xs, boolean makeCopy) {
        getHelper().setN(xs.length);
        X[] result = makeCopy ? Arrays.copyOf(xs, xs.length) : xs;
        // TODO make this consistent with other uses of sort where the upper limit of the range is result.length
        sort(result, 0, result.length - 1);
        return result;
    }

    public void sort(X[] a, int from, int to) {
        @SuppressWarnings("UnnecessaryLocalVariable") int lo = from;
        int hi = to;
        if (hi <= lo) return;
        Partition partition = partition(a, lo, hi);
        sort(a, lo, partition.lt - 1);
        sort(a, partition.gt + 1, hi);
    }

    public Partition partition(X[] xs, int lo, int hi) {
        final Partitioner partitioner = new Partitioner(getHelper(), lo, hi);
        return partitioner.getPartition(xs, lo, hi);
    }

    class Partitioner {
        private final Helper<X> helper;
        private final int lo;
        private final int hi;

        Partitioner(Helper<X> helper, int lo, int hi) {
            this.helper = helper;
            this.lo = lo;
            this.hi = hi;
        }

        // CONSIDER inlining this
        void conditionalSwap(X[] a, int lo, int hi) {
            if (a[lo].compareTo(a[hi]) > 0) swap(a, lo, hi);
        }

        public Partition getPartition(X[] xs, int lo, int hi) {
            conditionalSwap(xs, lo, hi);
            X v = xs[lo];
            int i = lo + 1;
            int lt = lo, gt = hi;
            // NOTE: we are trying to avoid checking on instrumented for every time in the inner loop for performance reasons.
            if (helper.instrumented())
                while (i <= gt) {
                    int cmp = helper.compare(xs, lo, hi, i, lo);
                    if (cmp < 0) helper.swap(xs, lo, hi, lt++, i++);
                    else if (cmp > 0) helper.swap(xs, lo, hi, i, gt--);
                    else i++;
                }
            else
                while (i <= gt) {
                    int cmp = xs[i].compareTo(v);
                    if (cmp < 0) swap(xs, lt++, i++);
                    else if (cmp > 0) swap(xs, i, gt--);
                    else i++;
                }
            return new Partition(lt, gt);
        }

        private void swap(X[] a, int i, int j) {
            if (helper != null) helper.swap(a, lo, hi, i, j);
            else {
                X temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }
    }

    public static final String DESCRIPTION = "QuickSort 3 way";

    // This is for faster sorting (no instrumentation option)

}

