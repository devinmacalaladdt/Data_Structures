package lse;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Driver {

	public static void main(String args[]){
		
		
		LittleSearchEngine lse = new LittleSearchEngine();
		
		try {
			lse.makeIndex("WowCh1.txt", "noisewords.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(String s : lse.keywordsIndex.keySet()){
			
			System.out.println();
			System.out.print(s+"     ");
			
			for(Occurrence o: lse.keywordsIndex.get(s)){
				
				System.out.print("doc: "+o.document+"   "+o.frequency+"     ");
				
			}
			
		}
		
		
		System.out.println();
		System.out.println();
		
		ArrayList<String>l = lse.top5search("red", "car");
		for(String s: l){
			
			System.out.print(s+"\t");
			
		}
		
		
		
	}
	
}
