import java.util.*;
import java.io.*;

public class BOJ_1976 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());

        int[][] map = new int[n][n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        Set<Integer> finalGroup = new HashSet<>();
        StringTokenizer st2 = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            finalGroup.add(Integer.parseInt(st2.nextToken()) - 1);
        }

        boolean[] visited = new boolean[n];
        // System.out.println("finalGroupt: " + finalGroup);

        for (int i = 0; i < n; i++) {
            if (visited[i])
                continue;
            Set<Integer> group = new HashSet<>();
            ArrayDeque<Integer> que = new ArrayDeque<>();
            que.add(i);

            while (!que.isEmpty()) {
                int nowCity = que.poll();
                group.add(nowCity);
                visited[nowCity] = true;

                int[] nextCities = map[nowCity];
                for (int j = 0; j < n; j++) {
                    if (nextCities[j] == 1 && !visited[j]) {
                        que.add(j);
                    }
                }
            }
            // System.out.println("tempGroup: " + group);
            if (group.containsAll(finalGroup)) {
                System.out.println("YES");
                return;
            }
        }
        System.out.println("NO");
    }
}