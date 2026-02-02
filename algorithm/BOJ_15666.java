import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

public class BOJ_15666 {
    private static Integer[] arr;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // arr = new int[n];
        TreeSet<Integer> set = new TreeSet<>();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            set.add(Integer.parseInt(st.nextToken()));
        }
        // Arrays.sort(arr);
        arr = set.toArray(new Integer[0]);
        for (int i = 0; i < arr.length; i++) {
            List<Integer> temp = new ArrayList<>();
            temp.add(arr[i]);
            dfs(temp, i, m - 1);
            temp.remove(temp.size() - 1);
        }
    }
    
    private static void dfs(List<Integer> list, int index, int count) {
        if (count == 0) {
            System.out.println(list.stream().map(String::valueOf).collect(Collectors.joining(" ")));
            return;
        }
        for (int i = index; i < arr.length; i++) {
            list.add(arr[i]);
            dfs(list, i, count - 1);
            list.remove(list.size() - 1);
        }

    }
}
