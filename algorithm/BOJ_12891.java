import java.io.*;
import java.util.*;

public class BOJ_12891 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int len = Integer.parseInt(st.nextToken());
        String raw = br.readLine();
        st = new StringTokenizer(br.readLine());

        int[] count = new int[26];
        HashMap<Character, Integer> map = new HashMap<>();
        map.put('A', Integer.parseInt(st.nextToken()));
        map.put('C', Integer.parseInt(st.nextToken()));
        map.put('G', Integer.parseInt(st.nextToken()));
        map.put('T', Integer.parseInt(st.nextToken()));
        // System.out.println(map);

        for (int i = 0; i < len; i++) {
            char now = raw.charAt(i);
            count[now - 'A'] += 1;
        }
        int output = 0;
        if (checkValid(map, count))
            output += 1;
        for (int i = len; i < n; i++) {
            char rightChar = raw.charAt(i);
            count[rightChar - 'A'] += 1;
            char leftChar = raw.charAt(i - len);
            count[leftChar - 'A'] -= 1;
            if (checkValid(map, count))
                output += 1;
        }
        System.out.println(output);
    }
    
    private static boolean checkValid(HashMap<Character, Integer> map, int[] count) {
        // System.out.println(Arrays.toString(count));
        return map.get('A') <= count['A' - 'A'] &&
        map.get('C') <= count['C' - 'A'] &&
         map.get('G') <= count['G' - 'A'] &&
                map.get('T') <= count['T' - 'A'];
    }
}
