package com.cg.mapreduce.join.simple;

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
public class SimpleNameJoinMapper extends Mapper<LongWritable, Text, Text, MyJoinPair> {

    public static final String NAME_INDEX = "1";

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] split = StringUtils.split(value.toString(),' ');
        if (split != null && split.length > 0) {
            context.write(new Text(split[0]), new MyJoinPair(value, new Text(NAME_INDEX)));
        }
    }

}
