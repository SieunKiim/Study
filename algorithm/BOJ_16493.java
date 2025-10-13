import java.util.*;
import java.io.*;

public class BOJ_16493 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] arr = new int[m][2];
        for (int i = 0; i < m; i++) {
            StringTokenizer st2 = new StringTokenizer(br.readLine());
            arr[i][0] = Integer.parseInt(st2.nextToken());
            arr[i][1] = Integer.parseInt(st2.nextToken());
        }

        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            int days = arr[i - 1][0];
            int pages = arr[i - 1][1];
            for (int j = 1; j <= n; j++) {
                if (j - days >= 0) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - days] + pages);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
                
            }
        }
        System.out.println(dp[m][n]);
    }
}
