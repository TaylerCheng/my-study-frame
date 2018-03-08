package com.cg.mapreduce.join.sort.io;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author： Cheng Guang
 * @date： 2018/1/31.
 */
public class MyJoinPair implements WritableComparable<MyJoinPair> {

    private Text data;
    private Text index;

    public MyJoinPair() {
        data = new Text();
        index = new Text();
    }

    public MyJoinPair(String first, String second) {
        this.data = new Text(first);
        this.index = new Text(second);
    }

    public MyJoinPair(Text first, Text second) {
        this.data = first;
        this.index = second;
    }

    public Text getData() {
        return data;
    }

    public void setData(Text data) {
        this.data = data;
    }

    public Text getIndex() {
        return index;
    }

    public void setIndex(Text index) {
        this.index = index;
    }

    @Override
    public int compareTo(MyJoinPair o) {
        int compareFirst = data.compareTo(o.data);
        return compareFirst != 0 ? compareFirst : index.compareTo(o.index);
    }

    @Override
    public void write(DataOutput out) throws IOException {
        data.write(out);
        index.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        data.readFields(in);
        index.readFields(in);
    }

}
