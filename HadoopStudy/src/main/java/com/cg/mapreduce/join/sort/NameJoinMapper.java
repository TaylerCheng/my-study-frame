package com.cg.mapreduce.join.sort;

import com.cg.mapreduce.join.sort.io.MyJoinPair;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.StringUtils;

import java.io.IOException;

/**
 * @author： Cheng Guang
 * @date： 2018/1/31.
 */
public class NameJoinMapper extends Mapper<LongWritable, Text, MyJoinPair, MyJoinPair> {

    public static final Text NAME_INDEX = new Text("1");

    private MyJoinPair outKey = new MyJoinPair(null, NAME_INDEX);
    private MyJoinPair outValue = new MyJoinPair(null, NAME_INDEX);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] split = StringUtils.split(value.toString(), ' ');
        if (split != null && split.length > 0) {
            outKey.setData(new Text(split[0]));
            outValue.setData(value);
            context.write(outKey, outValue);
        }
    }

}
