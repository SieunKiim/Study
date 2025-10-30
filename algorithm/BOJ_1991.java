import java.io.*;
import java.util.*;

 class Node {
    char value;
    Node left;
    Node right;

     Node(char root) {
        this.value = root;
    }
}

public class BOJ_1991 {
    
        
    static StringBuffer br, br_pre, br_in, br_post;

    public static void main(String args[]) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(in.readLine());
        Node[] arr = new Node[N];
        for (int i = 0; i < N; i++)
            arr[i] = new Node((char) ('A' + i));
        for (int i = 0; i < N; i++) {
            String str = in.readLine();
            arr[str.charAt(0) - 'A'].left = str.charAt(2) == '.' ? null : arr[str.charAt(2) - 'A'];
            arr[str.charAt(0) - 'A'].right = str.charAt(4) == '.' ? null : arr[str.charAt(4) - 'A'];
        }

        br = new StringBuffer();
        preOrder(arr[0]);
        br.append("\n");
        inOrder(arr[0]);
        br.append("\n");
        postOrder(arr[0]);
        br.append("\n");
        System.out.println(br);

        // br_pre = new StringBuffer();
        // br_in = new StringBuffer();
        // br_post = new StringBuffer();
        // order(arr[0]);
        // System.out.println(br_pre + "\n" + br_in + "\n" + br_post);
    }

    // static void order(Node node) {
    // br_pre.append(node.value);
    // if (node.left != null) order(node.left);
    // br_in.append(node.value);
    // if (node.right != null) order(node.right);
    // br_post.append(node.value);
    // }

    static void preOrder(Node node) {
        br.append(node.value);
        if (node.left != null)
            preOrder(node.left);
        if (node.right != null)
            preOrder(node.right);
    }

    static void inOrder(Node node) {
        if (node.left != null)
            inOrder(node.left);
        br.append(node.value);
        if (node.right != null)
            inOrder(node.right);
    }

    static void postOrder(Node node) {
        if (node.left != null)
            postOrder(node.left);
        if (node.right != null)
            postOrder(node.right);
        br.append(node.value);
    }

}
