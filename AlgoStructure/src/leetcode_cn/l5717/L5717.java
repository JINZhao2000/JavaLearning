package leetcode_cn.l5717;

/**
 * 给你一个整数数组 nums（下标从 0 开始）。每一次操作中，你可以选择数组中一个元素，并将它增加 1 。
 *
 * 比方说，如果 nums = [1,2,3] ，你可以选择增加 nums[1] 得到 nums = [1,3,3]。
 * 请你返回使 nums 严格递增的最少操作次数。
 *
 * 我们称数组 nums 是 严格递增的 ，当它满足对于所有的 0 <= i < nums.length - 1 都有 nums[i] < nums[i+1] 。
 * 一个长度为 1 的数组是严格递增的一种特殊情况。
 */

public class L5717 {
    public static void main(String[] args) {
        System.out.println(minOperations(new int[]{1,1,1}));
        System.out.println(minOperations(new int[]{1,5,2,4,1}));
        System.out.println(minOperations(new int[]{8}));
    }

    public static int minOperations(int[] nums) {
        if(nums.length == 1){
            return 0;
        }
        int current = nums[0];
        int count = 0;
        for (int i = 1; i < nums.length; i++) {
            if(current >= nums[i]) {
                count += current - nums[i] + 1;
                nums[i] = current+1;
            }
            current = nums[i];
        }
        return count;
    }
}
