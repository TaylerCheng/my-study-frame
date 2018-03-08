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
public class LocationJoinMapper extends Mapper<LongWritable, Text, MyJoinPair, MyJoinPair> {

    public static final Text LOCATION_INDEX = new Text("2");

    private MyJoinPair outKey = new MyJoinPair(null, LOCATION_INDEX);
    private MyJoinPair outValue = new MyJoinPair(null, LOCATION_INDEX);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] split = StringUtils.split(value.toString(), ' ');
        if (split != null && split.length > 2) {
            outKey.setData(new Text(split[2]));
            outValue.setData(value);
            context.write(outKey, outValue);
        }
    }

}
