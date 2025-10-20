import java.util.*;
import java.io.*;

public class BOJ_11725 {
    private class Node {
        int val;
        List<Node> subNode;
        Node parentNode;
        List<Integer> temp;

        public Node(int val, int t) {
            this.val = val;
            this.subNode = new ArrayList<>();
            this.parentNode = null;
            this.temp = new ArrayList<>();
            temp.add(t);
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        HashMap<Integer, List<Integer>> map = new HashMap<>();

        for (int i = 0; i < n-1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            List<Integer> A = map.getOrDefault(a, new ArrayList<>());
            List<Integer> B = map.getOrDefault(b, new ArrayList<>());
            A.add(b);
            B.add(a);
            map.put(a, A);
            map.put(b, B);
        }
        int[] output = new int[n + 1];
        output[1] = Integer.MAX_VALUE;
        Deque<Integer> que = new ArrayDeque<>();
        que.add(1);
        while (!que.isEmpty()) {
            int parent = que.poll();
            List<Integer> childs = map.get(parent);
            for (int c : childs) {
                
                if (output[c] == 0) {
                    output[c] = parent;
                    que.add(c);
                }

                
            }
        }
        for (int i = 2; i <= n; i++) {
            System.out.println(output[i]);
        }
    }
}
