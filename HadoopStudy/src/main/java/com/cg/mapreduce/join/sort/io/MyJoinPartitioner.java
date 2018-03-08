package com.cg.mapreduce.join.sort.io;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @author： Cheng Guang
 * @date： 2018/1/31.
 */
public class MyJoinPartitioner extends Partitioner<MyJoinPair, MyJoinPair> {

    @Override
    public int getPartition(MyJoinPair myJoinPair, MyJoinPair value, int numPartitions) {
        return (myJoinPair.getData().hashCode() & Integer.MAX_VALUE) % numPartitions;
    }

}
