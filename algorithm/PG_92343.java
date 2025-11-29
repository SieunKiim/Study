import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PG_92343 {
    public static void main(String[] args) {

        class Solution {
            class Node {
                int val;
                List<Node> childs;

                public Node(int val) {
                    this.val = val;
                    this.childs = new ArrayList<>();
                }
            }

            public int solution(int[] info, int[][] edges) {
                HashMap<Integer, Node> nodeMap = new HashMap<>();

                for (int[] edge : edges) {
                    int parentIndex = edge[0];
                    int childIndex = edge[1];
                    Node childNode = new Node(info[childIndex]);

                    if (nodeMap.containsKey(parentIndex)) {
                        Node temp = nodeMap.get(parentIndex);
                        temp.childs.add(childNode);
                    } else {
                        Node parentNode = new Node(info[parentIndex]);
                        parentNode.childs.add(childNode);
                        nodeMap.put(parentIndex, parentNode);
                    }

                    nodeMap.put(childIndex, childNode);
                }
                int[] sw = new int[2];
                sw[nodeMap.get(0).val] += 1;
                dfs(nodeMap.get(0), sw);
                return output;
            }

            public int output = 0;

            public void dfs(Node node, int[] sw) { // 0 : 양, 1 : 늑대

                if (node.childs.size() == 0) {
                    if (sw[0] > sw[1])
                        output = Math.max(output, sw[0]);
                    return;
                }

                for (Node child : node.childs) {
                    sw[child.val] += 1;
                    dfs(child, sw);
                    sw[child.val] -= 1;
                }
            }
        }
        Solution s = new Solution();
        s.solution(new int[] { 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1 }, new int[][] { { 0, 1 }, { 1, 2 }, { 1, 4 },
                { 0, 8 }, { 8, 7 }, { 9, 10 }, { 9, 11 }, { 4, 3 }, { 6, 5 }, { 4, 6 }, { 8, 9 } });
    }
}
