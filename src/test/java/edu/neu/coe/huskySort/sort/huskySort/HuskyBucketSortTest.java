/*
 * Copyright (c) 2017. Phasmid Software
 */

package edu.neu.coe.huskySort.sort.huskySort;

import edu.neu.coe.huskySort.sort.Helper;
import edu.neu.coe.huskySort.sort.huskySortUtils.HuskySortHelper;
import edu.neu.coe.huskySort.sort.simple.BucketSort;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertTrue;

@SuppressWarnings("ALL")
public class HuskyBucketSortTest {

    @Test
    public void sort4() throws Exception {
        final List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(2);
        list.add(1);
        Integer[] xs = list.toArray(new Integer[0]);
        HuskyBucketSort<Integer> sorter = new HuskyBucketSort<>(2, HuskySortHelper.integerCoder);
        Integer[] ys = sorter.sort(xs);
        assertTrue(sorter.getHelper().sorted(ys));
        System.out.println(sorter.toString());
    }

    @Test
    public void sortN() throws Exception {
        int N = 10000;
        Integer[] xs = new Integer[N];
        Random random = new Random();
        for (int i = 0; i < N; i++) xs[i] = random.nextInt(10000);
        HuskyBucketSort<Integer> sorter = new HuskyBucketSort<>(100, HuskySortHelper.integerCoder);
        Integer[] ys = sorter.sort(xs);
        assertTrue(sorter.getHelper().sorted(ys));
        System.out.println(sorter.toString());
    }

    @Test
    public void doubleSortN() throws Exception {
        int N = 10000;
        Integer[] xs = new Integer[N];
        Random random = new Random();
        for (int i = 0; i < N; i++) xs[i] = random.nextInt(10000);
        HuskyBucketSort<Integer> sorter = new HuskyBucketSort<>(100, HuskySortHelper.integerCoder);
        Integer[] ys1 = sorter.sort(xs);
        assertTrue(sorter.getHelper().sorted(ys1));
        Integer[] ys2 = sorter.sort(xs);
        assertTrue(sorter.getHelper().sorted(ys2));
    }


}