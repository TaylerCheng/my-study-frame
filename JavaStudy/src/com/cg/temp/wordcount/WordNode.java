package com.cg.temp.wordcount;

public class WordNode implements Comparable<WordNode> {

    private String word;
    private Integer count;

    public WordNode(String word, Integer count) {
        this.word = word;
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public int compareTo(WordNode o) {
        if (null == o) {
            return 1;
        }
        return count > ((WordNode) o).count ? 1 : count == ((WordNode) o).count ? 0 : -1;
    }

    @Override
    public String toString() {
        return "WordNode{" +
                "word='" + word + '\'' +
                ", count=" + count +
                '}';
    }
}
