import java.util.*;
import java.io.*;

public class BOJ_1010 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        long[] output = new long[n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            output[i] = combination(a, b);
        }
        for (long o : output) {
            System.out.println(o);
        }
    }

    public static long combination(int r, int n) {
        if (r > n - r)
            r = n - r; // 대칭성 활용
        long result = 1;
        for (int i = 0; i < r; i++) {
            result *= (n - i);
            result /= (i + 1);
        }
        return result;
    }
    
    
}



