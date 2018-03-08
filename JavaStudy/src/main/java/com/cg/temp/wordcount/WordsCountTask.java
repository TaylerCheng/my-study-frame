package com.cg.temp.wordcount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;

public class WordsCountTask implements Callable<Map<Integer, Map<String,Integer>>> {

    private Map<Integer, Map<String,Integer>> wordsCountMap = new HashMap<>();

    private  ArrayList<File> files;
    private int threadNum;

    public WordsCountTask(ArrayList<File> files, int threadNum) {
        this.files = files;
        this.threadNum = threadNum;
    }

    @Override
    public Map<Integer, Map<String, Integer>> call() throws Exception {
        BufferedReader reader = null;
        String line;
        for (File file : files) {
            try {
                reader = new BufferedReader(new FileReader(file));
                while ((line = reader.readLine()) != null) {
                    StringTokenizer token = new StringTokenizer(line);
                    String word = null;
                    while (token.hasMoreTokens()) {
                        // 将处理结果放到一个HashMap中
                        word = token.nextToken().toString().trim();
                        putWord(word);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return wordsCountMap;
    }

    private void putWord(String word) {
        Integer index = Math.abs(word.hashCode()) % threadNum;
        Map<String,Integer> hashMap = wordsCountMap.get(index);
        if (null == hashMap) {
            hashMap=new HashMap();
            wordsCountMap.put(index,hashMap);
        }
        if (null != hashMap.get(word)) {
            hashMap.put(word, hashMap.get(word) + 1);
        } else {
            hashMap.put(word, 1);
        }
    }

    //获取当前线程的执行结果
    public Map<Integer, Map<String, Integer>> getWordsCountMap() {
        return wordsCountMap;
    }

}


