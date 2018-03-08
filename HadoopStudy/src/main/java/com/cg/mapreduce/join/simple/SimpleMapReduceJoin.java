package com.cg.mapreduce.join.simple;

import com.cg.mapreduce.join.sort.io.MyJoinPair;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.net.URI;

/**
 * @author： Cheng Guang
 * @date： 2018/1/31.
 */
public class SimpleMapReduceJoin {

    public static final String BASE_URL="hdfs://master.hadoop:9000/test/join/";

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.set("mapreduce.framework.name", "local");
        Job job = Job.getInstance(conf, "SimpleMapReduceJoin");
        job.setJarByClass(SimpleMapReduceJoin.class);

        /**
         * 设置处理的处理Mapper和Reducer
         */
        MultipleInputs.addInputPath(job, new Path(BASE_URL + "input/name.txt"), TextInputFormat.class, SimpleNameJoinMapper.class);
        MultipleInputs.addInputPath(job, new Path(BASE_URL + "input/location.txt"), TextInputFormat.class, SimpleLocationJoinMapper.class);
        job.setReducerClass(SimpleJoinReducer.class);

        /**
         * 这是分区以及
         */
//        job.setPartitionerClass(MyJoinPartitioner.class);
//        job.setSortComparatorClass(MyPairSortComparator.class);

        /**
         * 设置输出数据格式
         */
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(MyJoinPair.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        /**
         * 设置输出路径
         */
        Path outputPath = new Path(BASE_URL + "output");
        FileSystem fileSystem = FileSystem.get(URI.create(outputPath.toString()), conf);
        if (fileSystem.exists(outputPath)) {
            fileSystem.delete(outputPath, true);
        }
        TextOutputFormat.setOutputPath(job, outputPath);
        job.setOutputFormatClass(TextOutputFormat.class);

        job.waitForCompletion(false)
;    }

}
