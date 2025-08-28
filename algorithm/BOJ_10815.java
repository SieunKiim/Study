import java.io.*;
import java.util.*;

public class BOJ_10815 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int[] arr = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        int m = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        int[] target = new int[m];
        for (int i = 0; i < m; i++) {
            target[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            if (isExist(arr, 0, n - 1, target[i])) {
                sb.append(1);
            } else {
                sb.append(0);
            }
            sb.append(" ");

        }
        System.out.println(sb.toString());
    }

    private static boolean isExist(int[] arr,int left, int right, int target) {
        if (left == right) {
            return arr[left] == target;
        }

        int mid = (left + right) / 2 + 1;
        if (arr[mid] <= target) {
            return isExist(arr, mid, right, target);
        }
        return isExist(arr, left, mid - 1, target);
    }
}
