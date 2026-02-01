import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

public class BOJ_15654 {

    private static List<String> tree;
    private static int[] arr;

    public static void main(String[] args) throws IOException {
        tree = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        arr = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(arr);
        for (int i = 0; i < n; i++) {
            List<Integer> temp = new ArrayList<>();
            Set<Integer> temp2 = new HashSet<>();
            temp2.add(i);
            temp.add(arr[i]);
            dfs(temp, temp2, m - 1);
            temp.remove(temp.size() - 1);
            temp2.remove(i);
        }
        for (String s : tree) {
            System.out.println(s);
        }
    }
    
    private static void dfs(List<Integer> list, Set<Integer> usedIndex, int count) {
        if (count == 0) {
            String s = list.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(" "));
            tree.add(s);
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            if (usedIndex.contains(i))
                continue;
            list.add(arr[i]);
            usedIndex.add(i);
            dfs(list, usedIndex, count - 1);
            list.remove(list.size() - 1);
            usedIndex.remove(i);
        }
    }
}
