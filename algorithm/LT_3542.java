import java.util.Arrays;

public class LT_3542 {
    public static void main(String[] args) {
        class Solution {
            private int output = 0;

            public int minOperations(int[] nums) {
                dfs(nums);
                return output;
            }

            private void dfs(int[] arr) {
                if (arr.length <= 0 || (arr.length == 1 && arr[0] == 0))
                    return;
                int n = arr.length;
                int min = Integer.MAX_VALUE;
                int zeroIndex = -1;

                for (int i = 0; i < n; i++) {
                    if (arr[i] == 0) {
                        zeroIndex = i;
                        break;
                    }
                    min = Math.min(min, arr[i]);
                }
                int lastSub = 0;
                if (zeroIndex == -1) {
                    output += 1;
                    for (int i = 0; i <= n; i++) {
                        if (i == n  || arr[i] == min) {
                            dfs(Arrays.copyOfRange(arr, lastSub, i));
                            lastSub = i + 1;
                        }
                    }
                } else {
                    dfs(Arrays.copyOfRange(arr, 0, zeroIndex));
                    dfs(Arrays.copyOfRange(arr, zeroIndex + 1, n));
                }

            }

        }
        Solution s = new Solution();
        s.minOperations(new int[] { 1, 2, 1, 2, 1, 2});
    }
}
