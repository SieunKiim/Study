import java.util.*;
import java.io.*;

public class LT_2257 {
    public static void main(String[] args) throws IOException {

        class Solution {

            private int tn;
            private int tm;

            public int countUnguarded(int m, int n, int[][] guards, int[][] walls) {
                int[][] D = new int[][] { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } };
                tn = m;
                tm = n;
                int[][] map = new int[tn][tm];
                // 1 : wall
                // 2 : gard
                // 3 : visited
                for (int[] wall : walls) {
                    map[wall[0]][wall[1]] = 1;
                }

                for (int[] guard : guards) {
                    int i = guard[0];
                    int j = guard[1];
                    map[i][j] = 2;
                    for (int d = 0; d < 4; d++) {
                        sight(map, D[d], i + D[d][0], j + D[d][1]);
                    }
                }
                int output = 0;

                for (int i = 0; i < tn; i++) {
                    for (int j = 0; j < tm; j++) {
                        if (map[i][j] == 0)
                            output += 1;
                    }
                }
                return output;
            }

            private boolean isAble(int i, int j) {
                return 0 <= i && i < tn && 0 <= j && j < tm;
            }

            private void sight(int[][] map, int[] direction, int i, int j) {
                while (isAble(i, j) && (map[i][j] == 0 || map[i][j] == 3)) {
                        map[i][j] = 3;
                        i += direction[0];
                        j += direction[1];
                }
            }
        }

        Solution s = new Solution();
        s.countUnguarded(4, 6, new int[][] { { 0, 0 }, { 1, 1 }, { 2, 3 } },
                new int[][] { { 0, 1 }, { 2, 2 }, { 1, 4 } });
    }
    

}
