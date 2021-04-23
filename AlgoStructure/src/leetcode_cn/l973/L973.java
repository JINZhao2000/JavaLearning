package leetcode_cn.l973;

import java.util.Arrays;

/**
 * 我们有一个由平面上的点组成的列表 points。需要从中找出 K 个距离原点 (0, 0) 最近的点。
 *
 * （这里，平面上两点之间的距离是欧几里德距离。）
 *
 * 你可以按任何顺序返回答案。除了点坐标的顺序之外，答案确保是唯一的。
 *
 * 需要改进的 ： 排序算法问题，可以用大根堆
 */

public class L973 {
    public static final int VALUE = Integer.MAX_VALUE;
    public static void main(String[] args) {
        int[][] a = new int[][]{{1,3},{-2,2}};
        int[][] b = new int[][]{{3,3},{5,-1},{-2,4}};
        System.out.println(Arrays.toString(kClosest(a, 1)[0]));
        System.out.println(Arrays.toString(kClosest(b, 2)[0]));
        System.out.println(Arrays.toString(kClosest(b, 2)[1]));
    }

    public static int[][] kClosest(int[][] points, int k) {
        int[] distances = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            distances[i] = distance(points[i]);
        }
        int[][] res = new int[k][2];
        for (int i = 0; i < k; i++) {
            int max = max(distances);
            if(max >= 0) {
                res[i] = points[max];
                distances[max] = VALUE;
            }
        }
        return res;
    }

    public static int distance(int[] point){
        return point[0]*point[0] + point[1]*point[1];
    }

    public static int max(int[] distances){
        int res = -1;
        int tmp = VALUE;
        for (int i = 0; i < distances.length; i++) {
            if (distances[i] < tmp) {
                tmp = distances[i];
                res = i;
            }
        }
        return res;
    }
}
