/*
    Time Complexity = O(N*(W^2))
    Space Complexity = O(N*W)

    Where N is the number of elements in the given array/list and W is the maximum length of a String.
*/

import java.util.ArrayList;

class TrieNode 
{
    //    Each Trie Node contains a static array 'children' where each alphabet points to a Trie Node.
    public TrieNode children[];

    //    'isEnd' is true if the node represents end of a contact.
    public boolean isEnd;

    //    Constructor
    TrieNode() 
    {
        children = new TrieNode[26];

        //    Initialize all the Trie nodes with null
        for (char i = 'a'; i <= 'z'; i++) 
        {
            children[i - 'a'] = null;
        }

        isEnd = false;
    }
}

public class Solution 
{
    private static void insertContact(String str, TrieNode root) 
    {
        int n = str.length();

        //    'itr' is used to iterate the Trie Nodes
        TrieNode itr = root;

        for (int i = 0; i < n; i++) 
        {
        	
            //    Check if the str[i] is already present in our Trie.
			TrieNode next = itr.children[str.charAt(i) - 'a'];

            if (next == null) 
            {
                //   If not found then create a new TrieNode
                next = new TrieNode();

                //    Insert
                itr.children[str.charAt(i) - 'a'] = next;
            }

            //    Move the iterator('itr') to point to next Trie Node.
            itr = next;

            //    If its the last character of the String 'str' then mark 'isEnd' as true
            if (i == n - 1) 
            {
                itr.isEnd = true;
            }
        }
    }

    private static void viewSuggestionsHelper(TrieNode curr, String prefix, ArrayList<String> temp) 
    {
    	
        //    Check if the String 'prefix' ends at this Node If yes then display the String found so far
        if (curr.isEnd) 
        {
            temp.add(prefix);
        }

        //    Find all the adjacent Nodes to the current Node and then call the function recursively.
        for (char c = 'a'; c <= 'z'; c++) 
        {
            TrieNode next = curr.children[c - 'a'];

            if (next != null) 
            {
                viewSuggestionsHelper(next, prefix + c, temp);
            }
        }
    }

    private static ArrayList<ArrayList<String>> viewSuggestions(String str, TrieNode root) 
    {
        TrieNode prev = root;

        StringBuilder prefix = new StringBuilder();

        int n = str.length();

        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();

        for (int i = 0; i < n; i++) 
        {
        	
            //    'prefix' stores the String formed so far.
            prefix.append(str.charAt(i));

            //    Get the last character entered.
            char lastCharacter = prefix.charAt(i);

            //    Find the Node corresponding to the last character of 'prefix' which is pointed by prev of the Trie.
            TrieNode curr = prev.children[lastCharacter - 'a'];

            //    If nothing found, then break the loop as no more prefixes are going to be present.
            if (curr == null) 
            {
                i++;
                break;
            }

            //    If present in trie then insert all the contacts with given prefix in the result.
            ArrayList<String> temp = new ArrayList<String>();

            viewSuggestionsHelper(curr, prefix.toString(), temp);

            result.add(temp);

            //    Change prev for next prefix
            prev = curr;
        }

        return result;
    }

    private static void insertContactList(ArrayList<String> contactList, TrieNode root) 
    {

        int n = contactList.size();

        //    Insert each contact into the trie.
        for (int i = 0; i < n; i++) 
        {
            insertContact(contactList.get(i), root);
        }
    }

    public static ArrayList<ArrayList<String>> phoneDirectory(ArrayList<String> contactList, String queryStr) 
    {
        TrieNode root = new TrieNode();

        //    Insert all the Contacts into Trie.
        insertContactList(contactList, root);

        //    Return the corresponding suggestions.
        return viewSuggestions(queryStr, root);
    }
}
