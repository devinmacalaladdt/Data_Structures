package trie;

import java.util.ArrayList;

/**
 * This class implements a Trie. 
 * 
 * @author Sesh Venugopal
 *
 */
public class Trie {
	
	// prevent instantiation
	private Trie() { }
	
	/**
	 * Builds a trie by inserting all words in the input array, one at a time,
	 * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
	 * The words in the input array are all lower case.
	 * 
	 * @param allWords Input array of words (lowercase) to be inserted.
	 * @return Root of trie with all words inserted from the input array
	 */
	public static TrieNode buildTrie(String[] allWords) {
		
		TrieNode root = new TrieNode(null,null,null);
		
		if(allWords.length!=0){
			
			root.firstChild = new TrieNode(new Indexes(0,(short)0,(short)(allWords[0].length()-1)),null,null);
			
		}else{
			
			return root;
			
		}
		
	
		
		for(int i = 1; i<allWords.length;i++){
		
			TrieNode prev = root;
			TrieNode curr = root.firstChild;
			int stringCount = 0;
			
			while(true){
				
				String commonLets = commonLetters(allWords[curr.substr.wordIndex].substring(curr.substr.startIndex, curr.substr.endIndex+1), allWords[i].substring(stringCount, allWords[i].length()));
				
				
				if(commonLets.length()==0){
					
					if(curr.sibling!=null){
						
						prev = curr;
						curr = curr.sibling;
						continue;
						
					}else{
						
						curr.sibling = new TrieNode(new Indexes(i, (short)(stringCount), (short)(allWords[i].length()-1)), null, null);
						break;
						
					}
					
				}else if(commonLets.length()<allWords[curr.substr.wordIndex].substring(curr.substr.startIndex, curr.substr.endIndex+1).length()){
					
					stringCount+=commonLets.length();
					curr.firstChild = new TrieNode(new Indexes(curr.substr.wordIndex, (short)(curr.substr.startIndex+commonLets.length()), (short)(curr.substr.endIndex)), curr.firstChild, null);
					curr.firstChild.sibling = new TrieNode(new Indexes(i, (short)(stringCount), (short)(allWords[i].length()-1)),null,null);
					curr.substr.endIndex = (short)(curr.substr.startIndex+commonLets.length()-1);
					break;
					
				}else{
					
					stringCount+=commonLets.length();
					prev = curr;
					curr = curr.firstChild;
					if(curr==null){
						
						prev.firstChild = new TrieNode(new Indexes(prev.substr.wordIndex, (short)(prev.substr.startIndex+commonLets.length()), (short)(prev.substr.endIndex)), null, null);
						prev.firstChild.sibling = new TrieNode(new Indexes(i, (short)(stringCount), (short)(allWords[i].length()-1)), null, null);
						prev.substr.endIndex = (short)(prev.firstChild.substr.startIndex-1);
						break;
						
					}

					
				}
				
			}
			
			
		}
		
		return root;
	}
	
	private static String commonLetters(String s1, String s2){
		
		String common = "";
		
		int i = 0;
		
		while(i<s1.length() && i<s2.length()){
			
			if(s1.charAt(i)==s2.charAt(i)){
				
				common+=s1.charAt(i);
				i++;
				
			}else{
				
				break;
				
			}
			
		}
		
		return common;
		
	}
	
	/**
	 * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
	 * trie whose words start with this prefix. 
	 * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
	 * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
	 * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
	 * and for prefix "bell", completion would be the leaf node that holds "bell". 
	 * (The last example shows that an input prefix can be an entire word.) 
	 * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
	 * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
	 *
	 * @param root Root of Trie that stores all words to search on for completion lists
	 * @param allWords Array of words that have been inserted into the trie
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all leaf nodes in trie that hold words that start with the prefix, 
	 * 			order of leaf nodes does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */
	public static ArrayList<TrieNode> completionList(TrieNode root,
										String[] allWords, String prefix) {
		
		ArrayList<TrieNode> l = new ArrayList<TrieNode>();
		TrieNode curr = root.firstChild;
		String currentString = "";
		
		while(curr!=null){
			
			String temp = allWords[curr.substr.wordIndex].substring(curr.substr.startIndex, curr.substr.endIndex+1);
			
			String prevString = currentString;
			currentString = currentString+temp;
			
			if(currentString.length()>=prefix.length() && currentString.startsWith(prefix)){
				
				break;
				
			}
			
			if(prefix.startsWith(currentString)){
				
				curr = curr.firstChild;
				
			}else{
				
				curr = curr.sibling;
				currentString = prevString;
				
			}
			
			
		}
		
		if(curr==null){
			
			return null;
			
		}
		
		addWords(curr, l, prefix, allWords);
		
		
		return l;
	}
	
	private static void addWords(TrieNode root, ArrayList<TrieNode> l, String prefix, String[] allWords){
		
		if(root.firstChild == null && root.sibling==null){
			
			if(allWords[root.substr.wordIndex].startsWith(prefix)){
				
				l.add(root);
				
			}
			return;
			
		}
		
		if(root.firstChild != null){
			
			addWords(root.firstChild, l,prefix, allWords);
			
		}else{
			
			if(allWords[root.substr.wordIndex].startsWith(prefix)){
				
				l.add(root);
				
			}
			
		}
		if(root.sibling != null){
			
			addWords(root.sibling,l,prefix, allWords);
			
		}
		
		
		
		
	}
	
	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
							.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
 }
