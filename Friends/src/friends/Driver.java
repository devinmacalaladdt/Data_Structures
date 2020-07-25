package friends;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {

	public static void main(String args[]){
		
		Scanner sc;
		try {
			sc = new Scanner(new File("yee.txt"));
			Graph g = new Graph(sc);
			Friends f = new Friends();
			ArrayList<String>a = new ArrayList<String>();
			a = f.connectors(g);
			
			for(String s: a){
				
				System.out.print(s+", ");
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
