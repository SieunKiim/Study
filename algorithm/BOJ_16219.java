import java.util.*;
import java.io.*;

public class BOJ_16219 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        String[] temp = br.readLine().split(" ");
        int[] arr = new int[temp.length];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(temp[i]);
        }
        int k = Integer.parseInt(br.readLine());
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < n - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                set.add(i + 1);
            }
        }

        int[] output = new int[k];
        for (int i = 0; i < k; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());
            int tempHolder = arr[left];
            arr[left] = arr[right];
            arr[right] = tempHolder;
            
        }
        for(int i =0;i <k;i++){
            System.out.print(output[i] + " ");
        }
    }
    
    public static int checkCount(int[] arr) {
        int n = arr.length;
        int count = 0;
        
        return count;
    }
}
