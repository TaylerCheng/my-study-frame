package com.cg.temp.wordcount;

public class HeapUtil {

    /**
     * 创建k个节点的小根堆
     *
     * @param wordNodes
     * @param k
     * @return
     */
    public static WordNode[] buildHeap(WordNode[] wordNodes, int k) {
        for (int i = 1; i < k; i++) {
            int child = i;
            int parent = (i - 1) / 2;
            WordNode temp = wordNodes[i];
            while (parent >= 0 && child != 0 && wordNodes[parent].compareTo(temp) > 0) {
                wordNodes[child] = wordNodes[parent];
                child = parent;
                parent = (parent - 1) / 2;
            }
            wordNodes[child] = temp;
        }
        return wordNodes;

    }

    public static void swapHead(WordNode[] a, WordNode value) {
        a[0] = value;
        int parent = 0;
        while (parent < a.length) {
            int lchild = 2 * parent + 1;
            int rchild = 2 * parent + 2;
            int minIndex = parent;
            if (lchild < a.length && a[parent].compareTo(a[lchild]) > 0) {
                minIndex = lchild;
            }
            if (rchild < a.length && a[minIndex].compareTo(a[rchild]) > 0) {
                minIndex = rchild;
            }
            if (minIndex == parent) {
                break;
            } else {
                WordNode temp = a[parent];
                a[parent] = a[minIndex];
                a[minIndex] = temp;
                parent = minIndex;
            }
        }
    }

    public static WordNode popHead(WordNode[] nodes, int end) {
        WordNode pop = nodes[0];
        WordNode temp = nodes[end];
        int k = 0;
        for (int i = 2 * k + 1; i < end; i = 2 * i + 1) {
            if (i < end && nodes[i].compareTo(nodes[i + 1]) > 0) {
                i++;
            }
            if (temp.compareTo(nodes[i]) < 0) {
                break;
            } else {
                nodes[k] = nodes[i];
                k = i;
            }
        }
        nodes[k] = temp;
        return pop;
    }

}
