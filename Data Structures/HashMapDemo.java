package search;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

public class HashMapDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
			throws IOException {
		// TODO Auto-generated method stub
		// set up hash table using constructor with inital N=300, and 
		// load factor threshold=1.5
		HashMap<String,String> machines = new HashMap<String,String>(300,1.5f);

		System.out.print("Enter input file name: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String file = br.readLine().trim();

		BufferedReader filebr = new BufferedReader(new FileReader(file));
		// read lines
		String line=null;
		while ((line = filebr.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			
			String key = st.nextToken();
			String val = st.nextToken();
			
			// put is used for insert as well as update 
			// it will add if key does not exists, and
			// replace old value with new value if key exists
			String oldVal = machines.put(key, val);
			if (oldVal != null) {
				System.out.println("replaced value=" + oldVal + 
								   " with value=" + val + 
								   " for key=" + key);
			} else {
				System.out.println("added mapping " + key + " --> " + val);
			}
			
			// the put method will call the String class's hashCode
			// method (since the key type is String) to get the hash
			// code, then map using mod table size
			// it searches in the mapped location's chain to check
			// if key already exists (using equals). If there is
			// a match, it replaces old
			// value of this key with the value sent in, otherwise
			// adds the key with value
		}
		filebr.close();
		
		System.out.print("\nEnter machine you want to look up, or \"quit\": ");
		String machine = br.readLine().toLowerCase();
		while (!"quit".equals(machine)) {
			String room = machines.get(machine);
			
			// the get method will call the String class's hashCode
			// method to obtain the hashCode for the given key
			// (since the key is of type String), then map hash code
			// to table using mod, then search in that chain for
			// the key using equals
			
			if (room != null) {
				System.out.println(machine + " is in " + room);
			} else {
				System.out.println(machine + " is not in the hash table");
			}
			System.out.print("Enter machine you want to look up, or \"quit\": ");
			machine = br.readLine().toLowerCase();
		}

		System.out.println("\nHere's a list of all mappings:");
		Set<String> keys = machines.keySet();  // Set is of type String since keys are Strings
		Iterator<String> iterator = keys.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			System.out.println(key + " --> " + machines.get(key));
			
			
		}
	}
}
