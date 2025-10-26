import java.util.*;
import java.io.*;

public class BOJ_11004 {
    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[] arr = new int[n];
        StringTokenizer raw = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(raw.nextToken());
        }
        int[] res = margeSort(arr, 0, n - 1);
        // System.out.println(Arrays.toString(res));
        System.out.println(res[k - 1]);
    }

    private static int[] margeSort(int[] arr, int left, int right) {

        int n = right - left + 1;
        int[] output = new int[n];
        if (right-left <= 1) {
            output[left-left] = Math.min(arr[left], arr[right]);
            output[right-left] = Math.max(arr[left], arr[right]);
            return output;
        }
        int mid = (left + right) /2;
        int[] leftArr = margeSort(arr, left, mid);
        int[] rightArr = margeSort(arr, mid + 1, right);
        int leftIndex = 0;
        int rightIndex = 0;
        for (int i = 0; i < n; i++) {
            if (rightIndex == rightArr.length) {
                output[i] = leftArr[leftIndex];
                leftIndex += 1;
                continue;
            }
            if (leftIndex == leftArr.length) {
                output[i] = rightArr[rightIndex];
                rightIndex += 1;
                continue;
            }
            // int tempRight = Math.min(rightIndex, rightArr.length - 1);
            // int tempLeft = Math.min(leftIndex, leftArr.length - 1);
            if (leftArr[leftIndex] < rightArr[rightIndex]) {
                output[i] = leftArr[leftIndex];
                leftIndex += 1;
            } else {
                output[i] = rightArr[rightIndex];
                rightIndex += 1;
            }
        }
        return output;
    }
}
