import java.util.*;
import java.io.*;

public class BOJ_25757 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        String game = st.nextToken();
        HashSet<String> set = new HashSet<>();

        for (int i = 0; i < n; i++) {
            set.add(br.readLine());
        }
        int output = 0;
        if (game.equals("Y")) {
            output = set.size();
        } else if (game.equals("F")) {
            output = set.size() / 2;
        } else {
            output = set.size() / 3;
        }
        System.out.println(output);
    }
}