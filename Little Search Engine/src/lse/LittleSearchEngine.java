package lse;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/**
 * This class builds an index of keywords. Each keyword maps to a set of pages in
 * which it occurs, with frequency of occurrence in each page.
 *
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in 
	 * DESCENDING order of frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashSet<String>(100,2.0f);
	}
	
	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile) 
	throws FileNotFoundException {
		
		HashMap<String,Occurrence> h = new HashMap<String,Occurrence>();
		Scanner sc = new Scanner(new File(docFile));
		
		Scanner s1 = new Scanner(new File("noisewords.txt"));
		while (s1.hasNext()) {
			String word = s1.next();
			noiseWords.add(word);
		}
		
		while(sc.hasNext()){
			
			String s = sc.next();
			s=getKeyword(s);
			if(s!=null && !noiseWords.contains(s)){
				
				if(h.containsKey(s)){
					
					h.put(s, new Occurrence(docFile,h.get(s).frequency+1));
					
				}else{
					
					h.put(s, new Occurrence(docFile,1));
					
				}
				
			}
			
		}
		
		return h;
			
	}

	
	
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeywords(HashMap<String,Occurrence> kws) {
		
		for(String s: kws.keySet()){
			
			if(s.length()>0){
				
				if(keywordsIndex.containsKey(s)){
					
					keywordsIndex.get(s).add(kws.get(s));
					insertLastOccurrence(keywordsIndex.get(s));
					
				}else{
					
					ArrayList<Occurrence> l = new ArrayList<Occurrence>();
					l.add(kws.get(s));
					keywordsIndex.put(s, l);
					
				}
				
			}
			
		}
		
		
	}
	
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * trailing punctuation, consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyword(String word) {
		
		int last = word.length()-1;
		String temp = word;
		String punct = ".,?:;!";
		
		for(int i = last; i>=0; i--){
			
			if(punct.contains(word.substring(i, i+1))){
				
				last--;
				
			}else{
				
				break;
				
			}
			
		}
		
		temp = word.substring(0,last+1).toLowerCase();
		if(temp.length()==0){return null;}
		for(int i = 0; i<temp.length(); i++){
			
			if(temp.charAt(i)<97 || temp.charAt(i)>122){
				
				return null;
				
			}
			
		}
		
		if(noiseWords.contains(temp)){
			
			return null;
			
		}
		
		
		return temp;
	}
	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion is done by
	 * first finding the correct spot using binary search, then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		
		
		Occurrence a[] = new Occurrence[occs.size()];
		ArrayList<Integer> l = new ArrayList<Integer>();
		
		if(occs.size()==1){
			
			l.add(0);
			return l;
			
		}
		
		for(int i = 0; i<occs.size()-1; i++){
			
			a[i] = occs.get(i);
			
		}
		
		Occurrence t = occs.get(occs.size()-1);
		int lo = 0;
		int high = a.length-2;
		int mid = (lo+high)/2;
		
		while(lo<=high){
			
			l.add(mid);
			
			if(a[mid].frequency==t.frequency){
				
				break;
				
			}else if(a[mid].frequency<t.frequency){
				
				high = mid-1;
				
			}else{
				
				lo = mid+1;
				
			}
			
			if(lo<=high){
				
				mid = (lo+high)/2;
				
			}
			
		}
		
		occs.remove(occs.size()-1);
		
		if(a[mid].frequency>=t.frequency){
			
			occs.add(mid+1, t);
			
		}else{
			
			occs.add(mid,t);
			
		}
		
		
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return l;
	}
	
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeywordsFromDocument(docFile);
			mergeKeywords(kws);
		}
		sc.close();
	}
	
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of document frequencies. (Note that a
	 * matching document will only appear once in the result.) Ties in frequency values are broken
	 * in favor of the first keyword. (That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2
	 * also with the same frequency f1, then doc1 will take precedence over doc2 in the result. 
	 * The result set is limited to 5 entries. If there are no matches at all, result is null.
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matches, returns null.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		
		ArrayList<String>l = new ArrayList<String>();
		
		if(!keywordsIndex.containsKey(kw1)&&!keywordsIndex.containsKey(kw2)){
			
			return null;
			
		}
		
		ArrayList<Occurrence>k1= new ArrayList<Occurrence>();
		ArrayList<Occurrence>k2= new ArrayList<Occurrence>();
		
		if(keywordsIndex.containsKey(kw1)){
			
			for(Occurrence o: keywordsIndex.get(kw1)){
				
				k1.add(o);
				insertLastOccurrence(k1);
				
			}
			
		}
		
		if(keywordsIndex.containsKey(kw2)){
			
			for(Occurrence o: keywordsIndex.get(kw2)){
				
				k2.add(o);
				insertLastOccurrence(k2);
				
			}
			
		}
		
		int curr1 = 0;
		int curr2 = 0;
		
		while(l.size()!=5 && (curr1<k1.size() || curr2<k2.size())){
			
			if(curr1<k1.size() && curr2<k2.size()){
				
				if(k1.get(curr1).frequency>=k2.get(curr2).frequency){
					
					if(!l.contains(k1.get(curr1).document)){
						
						l.add(k1.get(curr1).document);
						
					}
					
					curr1++;
					
				}else{
					
					if(!l.contains(k2.get(curr2).document)){
						
						l.add(k2.get(curr2).document);
						
					}
					
					curr2++;
					
				}
				
			}else if(curr1<k1.size()){
				
				if(!l.contains(k1.get(curr1).document)){
					
					l.add(k1.get(curr1).document);
					
				}
				
				curr1++;
				
			}else{
				
				if(!l.contains(k2.get(curr2).document)){
					
					l.add(k2.get(curr2).document);
					
				}
				
				curr2++;
				
			}
			
		}
		
		return l;
	
	}
	
	//hellofriend
}
