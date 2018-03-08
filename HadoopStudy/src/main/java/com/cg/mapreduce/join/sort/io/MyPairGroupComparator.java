package com.cg.mapreduce.join.sort.io;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @author： Cheng Guang
 * @date： 2018/1/31.
 */
public class MyPairGroupComparator extends WritableComparator  {

    public MyPairGroupComparator() {
        super(MyJoinPair.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        a = (MyJoinPair) a;
        b = (MyJoinPair) b;
        return ((MyJoinPair) a).getData().compareTo(((MyJoinPair) b).getData());
    }

}
