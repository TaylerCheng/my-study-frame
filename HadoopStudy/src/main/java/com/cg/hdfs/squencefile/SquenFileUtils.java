package com.cg.hdfs.squencefile;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.util.ReflectionUtils;
import org.junit.Test;

public class SquenFileUtils {

    private final static String[] STRS = new String[] { "one", "two", "three",
            "four", "five" };
    private Configuration conf = new Configuration();

    @Test
    public void localSquenceFileWrite() throws Exception {
        Path seqFile = new Path("C:/Users/Administrator/Desktop/seqFile.seq");
        FileSystem fs = FileSystem.getLocal(conf);
        IntWritable key = new IntWritable();
        Text value = new Text();
        SequenceFile.Writer writer = SequenceFile.createWriter(fs, conf,
                seqFile, IntWritable.class, Text.class);
        for (int i = 0; i < 100; i++) {
            key.set(i);
            value.set(STRS[i % 5]);
            writer.append(key, value);
        }
        writer.close();
    }

    public void squenceFileWrite() {
        String[] data = new String[] { "first", "second", "third", "fourth",
                "fifth" };
        String uri = "hdfs://192.168.99.156:9000/output/test.seq";
        Configuration conf = new Configuration();
        SequenceFile.Writer writer = null;
        try {
            FileSystem fs = FileSystem.get(URI.create(uri), conf);
            Path path = new Path(uri);
            IntWritable key = new IntWritable();
            Text value = new Text();
            writer = SequenceFile.createWriter(fs, conf, path, key.getClass(),
                    value.getClass());
            for (int i = 0; i < 100; i++) {
                key.set(i + 1);
                value.set(data[i % 5]);
                writer.append(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeStream(writer);
        }
    }

    @Test
    public void squenceFileRead() {
        String uri = "file:///C:/Users/Administrator/Desktop/seqFile.seq";
        Configuration conf = new Configuration();
        SequenceFile.Reader reader = null;
        try {
            FileSystem fs = FileSystem.get(URI.create(uri), conf);
            Path path = new Path(uri);
            reader = new SequenceFile.Reader(fs, path, conf);
            Writable key = (Writable) ReflectionUtils.newInstance(reader.getKeyClass(), conf);
            Writable value = (Writable) ReflectionUtils.newInstance(reader.getValueClass(), conf);
            reader.sync(2);
            System.out.println(reader.getPosition());
            while (reader.next(key, value)) {
                System.out.print(key + "\t" + value);
                System.out.println("\t" + reader.getPosition());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeStream(reader);
        }
    }

    @Test
    public void mapFileWrite() {
        String uri = "hdfs://192.168.99.156:9000/output/test.map";
        uri = "file:///C:/Users/Administrator/Desktop/test.map";
        Configuration conf = new Configuration();
        MapFile.Writer writer = null;
        try {
            FileSystem fs = FileSystem.get(URI.create(uri), conf);
            IntWritable key = new IntWritable();
            Text value = new Text();
            writer = new MapFile.Writer(conf, fs, uri, key.getClass(),
                    value.getClass());
            for (int i = 0; i < 1024; i++) {
                key.set(i);
                value.set("data" + i);
                writer.append(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeStream(writer);
        }
    }

}
