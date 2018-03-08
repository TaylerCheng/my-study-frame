package com.cg.algorithm.search;

/**
 * @author： Cheng Guang
 * @date： 2018/1/3.
 */
public class SearchUtils {

    public static int count = 0;

    public static void main(String[] args) {
        int[] a = new int[] { 1, 1, 4, 5, 5, 5, 7, 7 };
//        int[] a = new int[] { 4};
        printArray(a);
        /**
         * 二分查找
         */
        //        int index = binarySearchTarget(a, 2);
        //        System.out.println(index);
        /**
         * 二分查找变形
         */
        int[] ints = binarySearchTargetRange(a, 5);
        printArray(ints);
    }


    public static int binarySearchTarget(int[] a, int target) {
        /**
         * 非递归版本
         */
        int start = 0;
        int end = a.length - 1;
        int middle;
        while (start <= end) {
            middle = (start + end) / 2;
            if (a[middle] > target) {
                end = middle - 1;
            } else if (a[middle] < target) {
                start = middle + 1;
            } else {
                return middle;
            }
        }
        return -1;

        /**
         * 递归版本
         */
        //        return binarySearch(a, 0, a.length - 1, target);
    }

    private static int binarySearch(int[] a, int start, int end, int target) {
        if (start <= end) {
            int middle = (start + end) / 2;
            if (a[middle] == target) {
                return middle;
            }
            if (a[middle] > target) {
                return binarySearch(a, start, middle - 1, target);
            }
            if (a[middle] < target) {
                return binarySearch(a, middle + 1, end, target);
            }
        }
        return -1;
    }

    private static int[] binarySearchTargetRange(int[] a, int target) {
        if (a == null || a.length == 0) {
            return new int[] { -1, -1 };
        }
        int[] result = new int[2];
        result[0] = binarySearchRangeByLoop(a, target,0);
        result[1] = binarySearchRangeByLoop(a, target,1);

//        result = binarySearchRange(a, target, 0, a.length - 1, result);
        return result;
    }

    private static int binarySearchRangeByLoop(int[] a, int target,int flag) {
        int low = 0;
        int high = a.length - 1;
        int mid;
        int temp = -1;
        while (low <= high) {
            mid = (low + high) / 2;
            if (a[mid] > target) {
                high = mid - 1;
            } else if (a[mid] < target) {
                low = mid + 1;
            } else {
                temp = mid;
                if (flag == 0) {
                    high = mid - 1;
                }
                if (flag == 1) {
                    low = mid + 1;
                }
            }
        }
        return temp;
    }

    private static int[] binarySearchRange(int[] a, int target, int start, int end, int[] result) {
        if (start <= end) {
            int middle = (start + end) / 2;
            if (a[middle] == target) {
                if (result[0] == -1 || middle < result[0]) {
                    result[0] = middle;
                }
                if (middle > result[1]) {
                    result[1] = middle;
                }
                binarySearchRange(a, target, start, middle - 1, result);
                binarySearchRange(a, target, middle + 1, end, result);
                return result;
            } else if (a[middle] > target) {
                return binarySearchRange(a, target, start, middle - 1, result);
            } else if (a[middle] < target) {
                return binarySearchRange(a, target, middle + 1, end, result);
            }
        }
        return new int[] { -1, -1 };
    }

    private static void printArray(int[] a) {
        for (int i : a) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

}
