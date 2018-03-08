package com.cg.mapreduce.join.sort.io;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @author： Cheng Guang
 * @date： 2018/1/31.
 */
public class MyPairSortComparator extends WritableComparator {

    public MyPairSortComparator() {
        super(MyJoinPair.class, true);
    }

    @Override
    public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
        return super.compare(b1, s1, l1, b2, s2, l2);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        return a.compareTo(b);
    }

}
