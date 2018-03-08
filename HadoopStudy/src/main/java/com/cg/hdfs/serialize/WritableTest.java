package com.cg.hdfs.serialize;

import java.util.Comparator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.junit.Test;

public class WritableTest {

	private final static String[] STRS = new String[] { "one", "two", "three", "four", "five" };
	private Configuration conf = new Configuration();

	public static void main(String[] args) {
//		Text text = new Text();
//		//text.set("hadoop");
//		text.set(new Text("txt"));
//		System.out.println(text.getLength());
//		System.out.println(text.getBytes().length);
	}

	@Test
	public void commonWrite() throws Exception {
		Path textFile = new Path("C:/Users/Administrator/Desktop/textFile.txt");
		FileSystem fs = FileSystem.getLocal(conf);
		FSDataOutputStream fos = fs.create(textFile);
		for (int i = 0; i < 100; i++) {
			fos.writeInt(i);
			fos.writeChars(STRS[i % 5]);
		}
		fos.close();
	}

	@Test
	public void writableWrite() throws Exception {
		Path seqFile = new Path("C:/Users/Administrator/Desktop/textFile.txt");
		FileSystem fs = FileSystem.getLocal(conf);
		FSDataOutputStream fos = fs.create(seqFile);
		IntWritable key = new IntWritable();
		Text value = new Text();
		for (int i = 0; i < 100; i++) {
			key.set(i);
			value.set(STRS[i % 5]);
			key.write(fos);
			value.write(fos);
		}
		fos.close();
	}

}
