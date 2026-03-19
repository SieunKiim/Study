import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class HR_SubarraysWithGivenSumAndBoundedMaximum {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int numsCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> nums = IntStream.range(0, numsCount).mapToObj(i -> {
            try {
                return bufferedReader.readLine().replaceAll("\\s+$", "");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(toList());

        long k = Long.parseLong(bufferedReader.readLine().trim());

        long M = Long.parseLong(bufferedReader.readLine().trim());

        long result = Result.countSubarraysWithSumAndMaxAtMost(nums, k, M);

        System.out.println(result);

        bufferedReader.close();
    }

    class Result {

        /*
         * Complete the 'countSubarraysWithSumAndMaxAtMost' function below.
         *
         * The function is expected to return a LONG_INTEGER.
         * The function accepts following parameters:
         * 1. INTEGER_ARRAY nums
         * 2. LONG_INTEGER k
         * 3. LONG_INTEGER M
         */

        public static long countSubarraysWithSumAndMaxAtMost(List<Integer> nums, long k, long M) {
            if (nums.isEmpty())
                return 0;
            int n = nums.size();
            int output = 0;

            List<List<Long>> preSumList = new ArrayList<>();
            List<Long> buffer = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (nums.get(i) > M) {
                    if (!buffer.isEmpty()) {
                        preSumList.add(new ArrayList<>(buffer));
                        buffer.clear();
                    }
                    continue;
                }

                if (buffer.isEmpty()) {
                    buffer.add((long) nums.get(i));
                } else {
                    long temp = buffer.get(buffer.size() - 1);
                    buffer.add(temp + nums.get(i));
                }
            }
            if (!buffer.isEmpty()) {
                preSumList.add(new ArrayList<>(buffer));
            }

            for (List<Long> list : preSumList) {
                output += counter(list, k);
            }
            return output;
        }

        private static long counter(List<Long> preSum, long target) {
            HashMap<Long, Long> map = new HashMap<>();
            map.put(0L, 1L);
            long output = 0;
            for (Long pre : preSum) {
                Long key = pre - target;
                // System.out.println(key);
                output += map.getOrDefault(key, 0L);
                map.put(pre, map.getOrDefault(pre, 0L) + 1L);
            }
            return output;
        }

    }
}
