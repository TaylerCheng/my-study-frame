package com.cg.algorithm.sort;

import org.python.antlr.ast.Break;

/**
 * @author： Cheng Guang
 * @date： 2018/1/3.
 */
public class SortUtils {

    public static int count = 0;

    public static void bubbleSort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = a.length - 1; j > i; j--) {
                if (a[j] < a[j - 1]) {
                    swap(a, j, j - 1);
                }
            }
        }
    }

    public static void insertSort(int[] a) {
        int temp;
        int i, j;
        for (i = 1; i < a.length; i++) {
            temp = a[i];
            for (j = i; j > 0 && temp < a[j - 1]; j--) {
                a[j] = a[j - 1];
            }
            a[j] = temp;
        }
    }

    public static void selectSort(int[] a) {
        int tempMinIndex;
        int i, j;
        for (i = 0; i < a.length - 1; i++) {
            tempMinIndex = i;
            for (j = i + 1; j < a.length; j++) {
                if (a[j] < a[tempMinIndex]) {
                    tempMinIndex = j;
                }
            }
            swap(a, i, tempMinIndex);
        }
    }

    public static void quickSort(int[] a) {
        partition(a, 0, a.length - 1);
    }

    private static void partition(int[] a, int start, int end) {
        if (start < 0 || start >= end) {
            return;
        }
        //选取基点
        int baseIndex = getBase(a, start, end);
        int base = a[baseIndex];
        swap(a, start, baseIndex);
        int left = start;
        int right = end;
        while (left < right) {
            while (a[right] > base && left < right) {
                right--;
            }
            if (left < right) {
                a[left++] = a[right];
            }
            while (a[left] <= base && left < right) {
                left++;
            }
            if (left < right) {
                a[right--] = a[left];
            }
            a[left] = base;
        }

        partition(a, start, left - 1);
        partition(a, left + 1, end);
    }

    private static int getBase(int[] a, int start, int end) {
        int middle = (start + end) / 2;
        return middle;
    }

    public static void shellSort(int[] a) {
        int temp;
        int increment, i, j;
        for (increment = a.length / 2; increment > 0; increment = increment / 2) {
            for (i = increment; i < a.length; i++) {
                temp = a[i];
                for (j = i; j >= increment && temp < a[j - increment]; j -= increment) {
                    a[j] = a[j - increment];
                }
                a[j] = temp;
            }
        }

    }

    public static void mergeSort(int[] a) {
        mergeSort(a, 0, a.length - 1);
    }

    public static void main(String[] args) {
        int[] a = new int[] { 6, 5, 2, 9, 10, 8, 3, 4, 7, 1 };

        //       int[] a = new int[] { 6, 5, 5, 5, 10, 8, 3, 4, 7, 1 };
//        int[] a = new int[] { 5 };
        printArray(a);
        //冒泡排序
        //        bubbleSort(a);
        //插入排序
        //        insertSort(a);
        //选择排序
        //        selectSort(a);
        //快速排序
        //        quickSort(a);
        //希尔排序
        //        shellSort(a);
        //归并排序
//        mergeSort(a);
        //堆排序
        heapSort(a);

        printArray(a);
    }

    private static void heapSort(int[] a) {
        for (int end = a.length - 1; end >= 0; end--) {
            buildHeap(a, end);
            swap(a, 0, end);
        }
    }

    private static void buildHeap(int[] a, int end) {
        for (int node = (end - 1) / 2; node >= 0; node--) {
            int leftChild = node * 2 + 1;
            int rightChild = node * 2 + 2;
            if (leftChild <= end) {
                if (rightChild <= end) {
                    if (a[rightChild] >= a[leftChild]) {
                        swap(a, rightChild, leftChild);
                    }
                }
                if (a[leftChild] >= a[node]) {
                    swap(a, leftChild, node);
                }
            }
        }
    }

    private static void mergeSort(int[] a, int start, int end) {
        if (start < end) {
            int mid = (start + end) / 2;
            mergeSort(a, start, mid);
            mergeSort(a, mid + 1, end);
            merge(a, start, mid, end);
        }
    }

    private static void merge(int[] a, int start, int middle, int end) {
        int[] tempArr = new int[end - start + 1];
        int tempIndex = 0;

        int left = start;
        int right = middle + 1;
        while (left <= middle && right <= end) {
            if (a[left] <= a[right]) {
                tempArr[tempIndex++] = a[left++];
            } else if (a[left] > a[right]) {
                tempArr[tempIndex++] = a[right++];
            }
        }
        while (left <= middle) {
            tempArr[tempIndex++] = a[left++];
        }
        while (right <= end) {
            tempArr[tempIndex++] = a[right++];
        }
        for (int i = 0; i < tempArr.length; i++) {
            a[start++] = tempArr[i];
        }
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static void printArray(int[] a) {
        for (int i : a) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

}
