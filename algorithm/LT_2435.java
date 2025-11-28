import java.util.*;

public class LT_2435 {
    public static void main(String[] args) {
        class Solution {
            private int mod = 1000000007;

            public int numberOfPaths(int[][] grid, int k) {
                int n = grid.length;
                int m = grid[0].length;
                int[][][] map = new int[n + 1][m + 1][k];
                map[1][1][grid[0][0] % k] = 1;
                System.out.println(map[1][1][0]);
                for (int i = 1; i <= n; i++) {
                    for (int j = 1; j <= m; j++) {
                        int gridVal = grid[i - 1][j - 1];
                        for (int t = 0; t < k; t++) {
                            int newUTIndex = (t + gridVal) % k;
                            int newRTIndex = (map[i][j - 1][t] + gridVal) % k;
                            map[i][j][newUTIndex] = (map[i][j][newUTIndex] + map[i - 1][j][t]) % mod;
                            map[i][j][newRTIndex] = (map[i][j][newRTIndex] + map[i][j - 1][t]) % mod;
                        }
                    }
                }
                return map[n][m][0];
            }
        }

        Solution s = new Solution();
        s.numberOfPaths(new int[][] { { 0, 0 } }, 5);
    }
}
