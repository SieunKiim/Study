import java.util.Arrays;

public class LT_2300 {
    public static void main(String[] args) {
        class Solution {
            public int[] successfulPairs(int[] spells, int[] potions, long success) {
                int n = potions.length;
                Arrays.sort(potions);
                int[] output = new int[spells.length];
                for (int i = 0; i < spells.length; i++) {
                    int index = getIndex(potions, (int) (success / spells[i]));
                    System.out.println("index : " + index);
                    if (success % spells[i] != 0) {
                        index += 1;
                    }
                    output[i] = Math.max(0, n - index);
                }
                return output;
            }

            public int getIndex(int[] arr, int target) {
                int left = 0;
                int right = arr.length - 1;
                while (left < right) {
                    int mid = (left + right) / 2;
                    int midVal = arr[mid];
                    if (midVal < target) {
                        left = mid + 1;
                    } else {
                        right = mid;
                    }
                }
                return left;
            }
        }
        
        Solution s = new Solution();
        s.successfulPairs(new int[]{5,1,3}, new int[]{1,2,3,4,5}, 7L);
    }
}
