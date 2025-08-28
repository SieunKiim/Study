import java.util.*;
import java.io.*;

public class BOJ_7568 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(bf.readLine());

        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            String[] a = bf.readLine().split(" ");
            arr[i][0] = Integer.parseInt(a[0]);
            arr[i][1] = Integer.parseInt(a[1]);
        }

        int[] result = new int[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j)
                    continue;
                if (!isBig(arr[i], arr[j])) {
                    result[i] += 1;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (i != 0)
                sb.append(" ");
            sb.append(n - result[i]);
        }
        System.out.println(sb.toString());
    }

    private static boolean isBig(int[] a, int[] b) {
        return b[0] > a[0] && b[1] > a[1];
    }
}
