package com.cg.mapreduce.join.sort;

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
public class JoinReducer extends Reducer<MyJoinPair, MyJoinPair, Text, Text> {

    private List<String> nameList = new ArrayList<String>();

    @Override
    protected void reduce(MyJoinPair key, Iterable<MyJoinPair> values, Context context)
            throws IOException, InterruptedException {
        nameList.clear();
        for (MyJoinPair value : values) {
            //这里随着Value的迭代，Value的值其实也会变
//            System.out.println("key:"+key.getIndex().toString());
            String data = value.getData().toString();
            if (value.getIndex().toString().equals("1")) {
                nameList.add(data);
            } else {
                if (nameList.size() > 0) {
                    for (String name : nameList) {
                        System.out.println(data + " " + name);
                    }
                } else {
                    System.out.println(data + " null");
                }
            }
        }

    }

}
