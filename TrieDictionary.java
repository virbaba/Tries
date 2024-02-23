import java.util.ArrayList;

class Node {
    char ch;
    Node[] children;
    boolean isEnd;

    public Node(char ch) {
        this.ch = ch;
        children = new Node[26];
    }
}

public class Solution {
    public static void insert(Node root, ArrayList<String> contactList) {
        for (String str : contactList) {
            Node curr = root;
            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                int index = ch - 'a';
                if (curr.children[index] == null) {
                    curr.children[index] = new Node(ch);
                }
                curr = curr.children[index];
            }
            curr.isEnd = true;
        }
    }

    public static void search(Node root, String prefix, ArrayList<String> ans) {
        for (int i = 0; i < prefix.length(); i++) {
            char ch = prefix.charAt(i);
            int index = ch - 'a';
            if (root.children[index] != null) {
                root = root.children[index];
            }else {
                // Prefix not found, terminate the search
                return;
            }
        }
        // If we reach here, the prefix is found, perform a DFS to gather all words
        dfs(root, prefix, ans);
    }

    private static void dfs(Node root, String current, ArrayList<String> ans) {
        if (root.isEnd) {
            ans.add(current);
        }
        for (int i = 0; i < 26; i++) {
            if (root.children[i] != null) {
                char ch = (char) (i + 'a');
                dfs(root.children[i], current + ch, ans);
            }
        }
    }

    public static ArrayList<ArrayList<String>> phoneDirectory(ArrayList<String> contactList, String queryStr) {
        Node root = new Node('\u0000');
        ArrayList<ArrayList<String>> ans = new ArrayList<>();
        insert(root, contactList);

        String prefix = "";
        for (int i = 0; i < queryStr.length(); i++) {
            ArrayList<String> list = new ArrayList<>();
            prefix += queryStr.charAt(i);
            search(root, prefix, list);
            if(list.size() > 0){
                ans.add(list);
            }
        }

        return ans;
    }
}
