/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package edu.neu.coe.huskySort.sort.simple;

import edu.neu.coe.huskySort.sort.BaseHelper;
import edu.neu.coe.huskySort.sort.Helper;
import edu.neu.coe.huskySort.sort.SortWithHelper;

public class InsertionSort<X extends Comparable<X>> extends SortWithHelper<X> {

    /**
     * Constructor for InsertionSort
     *
     * @param N            the number elements we expect to sort.
     * @param instrumented whether or not we want an instrumented helper class.
     */
    public InsertionSort(int N, boolean instrumented) {
        super(DESCRIPTION, N, instrumented);
    }

    public InsertionSort() {
        this(new BaseHelper<>(DESCRIPTION));
    }

    /**
     * Constructor for InsertionSort
     *
     * @param helper an explicit instance of Helper to be used.
     */
    public InsertionSort(BaseHelper<X> helper) {
        super(helper);
    }

    public void sort(X[] xs, int from, int to) {
        final Helper<X> helper = getHelper();
        for (int i = from + 1; i < to; i++) {
            for (int j = i; j > from && helper.less(xs[j], xs[j - 1]); j--) {
                helper.swap(xs, from, to, j, j - 1);
            }
        }
    }

    public static <Y extends Comparable<Y>> void mutatingInsertionSort(Y[] ys) {
        new InsertionSort<Y>().mutatingSort(ys);
    }

    public static final String DESCRIPTION = "Insertion sort";

}
