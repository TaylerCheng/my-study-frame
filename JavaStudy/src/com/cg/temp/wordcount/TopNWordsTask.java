package com.cg.temp.wordcount;

import java.util.Map;
import java.util.concurrent.Callable;

public class TopNWordsTask implements Callable<WordNode[]> {

    private Map<String, Integer> words;
    private int topN;


    public TopNWordsTask(Map<String, Integer> words, int topN) {
        this.words = words;
        this.topN = topN;
    }

    @Override
    public WordNode[] call() {
        WordNode[] wordNodes = new WordNode[words.size()];
        int i = 0;
        for (Map.Entry<String, Integer> entry : words.entrySet()) {
            wordNodes[i++] = new WordNode(entry.getKey(), entry.getValue());
        }
        WordNode[] wordHeap;
        if (words.size() > topN) {
            wordHeap = getTopKByHeap(wordNodes, topN);
        } else {
            wordHeap = getTopKByHeap(wordNodes, words.size());
        }
        return wordHeap;
    }

    public WordNode[] getTopKByHeap(WordNode input[], int k) {
        WordNode[] wordHeap = HeapUtil.buildHeap(input, k);
        for (int i = k; i < input.length; i++) {
            if (input[i].compareTo(wordHeap[0]) > 0) {
                HeapUtil.swapHead(wordHeap, input[i]);
            }
        }
        return wordHeap;
    }


}
