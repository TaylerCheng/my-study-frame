package com.cg.mapreduce.join.simple;

import com.cg.mapreduce.join.sort.io.MyJoinPair;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author： Cheng Guang
 * @date： 2018/1/31.
 */
public class SimpleJoinReducer extends Reducer<Text, MyJoinPair, Text, Text> {

    private List<String> nameList = new ArrayList<String>();
    private List<String> locationList = new ArrayList<String>();

    @Override
    protected void reduce(Text key, Iterable<MyJoinPair> values, Context context)
            throws IOException, InterruptedException {
        nameList.clear();
        locationList.clear();
        for (MyJoinPair value : values) {
            if (value.getIndex().toString().equals("1")) {
                nameList.add(value.getData().toString());
            } else if (value.getIndex().toString().equals("2")) {
                locationList.add(value.getData().toString());
            }
        }
        for (String location : locationList) {
            if (nameList.size() > 0) {
                for (String name : nameList) {
                    System.out.println(location + " " + name);
                }
            } else {
                System.out.println(location + " null");
            }
        }
    }


}
