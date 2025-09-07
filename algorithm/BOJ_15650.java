import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_15650 {
    private static int limit;
    private static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        limit = Integer.parseInt(st.nextToken());

        getString(1, limit, new StringBuilder());
    }
    
    private static void getString(int point, int left, StringBuilder s) {
        if (left == 0) {

            System.out.println(new StringBuilder(s.substring(1)));
        }
        for (int i = point; i <= N; i++) {
            if(i <=N){
                getString(i + 1, left - 1, s.append(" ").append(i));
                s.setLength(s.length() - 2);
            }
        }
    }
}
