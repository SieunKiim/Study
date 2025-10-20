import java.util.*;
import java.io.*;

public class BOJ_14728 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int totalStudyTime = Integer.parseInt(st.nextToken());

        int[][] chapters = new int[n][2];
        for (int i = 0; i < n; i++) {
            StringTokenizer st2 = new StringTokenizer(br.readLine());
            chapters[i][0] = Integer.parseInt(st2.nextToken());
            chapters[i][1] = Integer.parseInt(st2.nextToken());
        }
        int[][] dp = new int[n + 1][totalStudyTime + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= totalStudyTime; j++) {
                int time = chapters[i-1][0];
                int score = chapters[i-1][1];
                if (time > j)
                    dp[i][j] = dp[i-1][j];
                else dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - time] + score);
            }
        }
        System.out.println(dp[n][totalStudyTime]);
    }
}
