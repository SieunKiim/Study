// import java.io.BufferedReader;
// import java.io.IOException;
import java.io.*;
import java.util.*;

public class BOJ_11723 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        Set<Integer> set = new HashSet<>();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            String[] raw = br.readLine().split(" ");
            String output = cal(raw, set);
            if (output != null) {
                sb.append(output);
                sb.append("\n");
            }
        }
            System.out.println(sb.toString());
    }
    
    private static String cal(String[] raw, Set<Integer> set) throws Exception {
        String modi = raw[0];
        switch(modi){
            case "add":
                set.add(Integer.parseInt(raw[1]));
                return null;
            case "remove":
                set.remove(Integer.parseInt(raw[1]));
                return null;
            case "check":
                return set.contains(Integer.parseInt(raw[1])) ? "1" : "0";
            case "toggle":
                if (set.contains(Integer.parseInt(raw[1]))) {
                    set.remove(Integer.parseInt(raw[1]));
                } else {
                    set.add(Integer.parseInt(raw[1]));};
                return null;
            case "all":
                for (int i = 1; i <= 20; i++) {
                    set.add(i);
                }
                return null;
            case "empty":
                set.clear();
                return null;
            default:
                throw new RuntimeException("not found");
        }
    }
}
