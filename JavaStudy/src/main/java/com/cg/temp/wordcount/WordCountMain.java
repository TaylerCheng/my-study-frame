package com.cg.temp.wordcount;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WordCountMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //设置取[topN]个频率最高的单词
        int topN = 100;
        //设置线程数量
        int threadNum = 5;
        ExecutorService threadPool = Executors.newFixedThreadPool(threadNum);

        File path = new File("C:\\Users\\Administrator\\Desktop\\files");
        if (!path.isDirectory()) {
            return;
        }
        /**
         * 1、将所有需要读取的文件均匀分配到[threadNum]个线程并行读取
         */
        Map<Integer, ArrayList<File>> filesMap = new HashMap<>();
        File[] allFiles = path.listFiles();
        for (int i = 0; i < allFiles.length; i++) {
            int index = i % threadNum;
            ArrayList<File> fileList = filesMap.get(index);
            if (fileList == null) {
                fileList = new ArrayList<>();
                filesMap.put(index, fileList);
            }
            fileList.add(allFiles[i]);
        }

        /**
         * 2、使用多线程对多个文件进行并行读取，且统计单词时对单词做hash处理
         */
        List<Future<Map<Integer, Map<String, Integer>>>> countFutureList = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            ArrayList<File> files = filesMap.get(i);
            if (files != null && files.size() > 0) {
                WordsCountTask wordsCountTask = new WordsCountTask(files, threadNum);
                countFutureList.add(threadPool.submit(wordsCountTask));
            }
        }
        //保存每个线程所读取的文件的单词统计情况，每个线程统计时会对单词进行分区，以便后面的并行topN
        List<Map<Integer, Map<String, Integer>>> wordCountMapList = new ArrayList<>();
        for (Future<Map<Integer, Map<String, Integer>>> future : countFutureList) {
            wordCountMapList.add(future.get());
        }

        /**
         * 3、使用分治思想，多线程对每一部分进行TopN算法
         */
        List<Future<WordNode[]>> topNfutureList = new ArrayList<>();
        for (int i = 0; i <threadNum; i++) {
            Map<String, Integer> wordSubMap = mergeMap(wordCountMapList, i);
            if (wordSubMap.size() > 0) {
                TopNWordsTask topNWordsTask = new TopNWordsTask(wordSubMap, topN);
                topNfutureList.add(threadPool.submit(topNWordsTask));
            }
        }
        //保存每个线程的topN堆结果
        List<WordNode[]> heapList = new ArrayList<>();
        for (Future<WordNode[]> future : topNfutureList) {
            heapList.add(future.get());
        }

        /**
         * 4、合并多个线程TopN结果，得到最终结果
         */
        WordNode[] result = mergeHeap(heapList, topN);
        for (int i = result.length - 1; i >= 0; i--) {
            WordNode temp = HeapUtil.popHead(result, i);
            if (temp.getCount() != -1) {
                System.out.println(temp);
            }
        }
        threadPool.shutdown();
    }

    private static Map<String, Integer> mergeMap(List<Map<Integer, Map<String, Integer>>> wordCountMapList, int index) {
        Map<String, Integer> mergeMap = new HashMap<>();
        for (Map<Integer, Map<String, Integer>> wordCountMap : wordCountMapList) {
            Map<String, Integer> subMap = wordCountMap.get(index);
            if (subMap != null) {
                Set<String> keySet = subMap.keySet();
                for (String word : keySet) {
                    if (mergeMap.containsKey(word)) {
                        mergeMap.put(word, mergeMap.get(word) + subMap.get(word));
                    } else {
                        mergeMap.put(word, subMap.get(word));
                    }
                }
            }
        }
        return mergeMap;
    }

    private static WordNode[] mergeHeap(List<WordNode[]> heapList, int topN) {
        WordNode[] result = new WordNode[topN];
        for (int i = 0; i < topN; i++) {
            result[i] = new WordNode("", -1);
        }
        for (WordNode[] heap : heapList) {
            for (int i = 0; i < heap.length; i++) {
                if (heap[i].compareTo(result[0]) > 0) {
                    HeapUtil.swapHead(result, heap[i]);
                }
            }
        }
        return result;
    }

}