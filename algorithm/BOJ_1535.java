import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class BOJ_1535 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        List<Integer> L = Arrays.stream(br.readLine().split(" ")).map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> J = Arrays.stream(br.readLine().split(" ")).map(Integer::parseInt).collect(Collectors.toList());

        int[][] dp = new int[n + 1][101];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= 100; j++) {
                dp[i][j] = dp[i-1][j];
                if (j >= L.get(i - 1)) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - L.get(i-1)] + J.get(i-1));
                }
                
            }
        }
        System.out.println(dp[n][99]);
    }
}
