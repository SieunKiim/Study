import java.util.*;
import java.io.*;

public class BOJ_17827 {
    
    public static void main(String[] args) throws IOException {
         class Node {
            int val;
            int nextIndex;

            Node(int a, int b) {
                this.val = a;
                this.nextIndex = b;
            }
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        String[] rawArr = br.readLine().split(" ");
        
        Node[] nodes = new Node[n];
        for (int i = 0; i < n - 1; i++) {
            int nodeValue = Integer.parseInt(rawArr[i]);
            nodes[i] = new Node(nodeValue, i + 1);
        }
        nodes[n - 1] = new Node(Integer.parseInt(rawArr[n - 1]), c);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < m; i++) {
            int count = Integer.parseInt(br.readLine());
            if (count <= c) {
                sb.append(nodes[count].val);
            } else {
                int cal = count - c+1;
                int tempIndex = cal % (n - c+1);
                sb.append(nodes[tempIndex + c-1].val);
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
}
