import java.util.*;

public class LT_2872 {
    public static void main(String[] args) {
        class Solution {
            class Tree {
                int val;
                List<Tree> childs;

                public Tree(int val) {
                    this.val = val;
                    this.childs = new ArrayList<>();
                }
            }

            public int mod;
            public int output;
            public int[] mapping;

            public int maxKDivisibleComponents(int n, int[][] edges, int[] values, int k) {
                mod = k;
                mapping = values;
                HashMap<Integer, Tree> map = new HashMap<>();
                for (int[] edge : edges) {
                    int parentVal = edge[0];
                    int childVal = edge[1];
                    Tree child = new Tree(childVal);
                    if (map.containsKey(parentVal)) {
                        Tree temp = map.get(parentVal);
                        temp.childs.add(child);
                        map.put(parentVal, temp);
                    } else {
                        Tree temp = new Tree(parentVal);
                        temp.childs.add(child);
                        map.put(parentVal, temp);
                    }
                    map.put(childVal, child);
                }
                if (dfs(map.get(0)) % 3 == 0) {
                    output += 1;
                }
                return output;
            }

            public int dfs(Tree tree) {
                if (tree.childs == null || tree.childs.size() == 0) {
                    return mapping[tree.val] % mod;
                }
                int sum = mapping[tree.val];
                for (Tree child : tree.childs) {
                    int temp = dfs(child);
                    if (temp == 0)
                        output += 1;
                    sum += temp;
                }
                return sum % mod;
            }
        }

        Solution s = new Solution();
        s.maxKDivisibleComponents(5, new int[][] { { 0, 2 }, { 1, 2 }, { 1, 3 }, { 2, 4 }},
                new int[] { 1, 8, 1, 4, 4 }, 6);
    }
}
