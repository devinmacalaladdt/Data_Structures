package search;

import java.util.HashSet;

// Point class defines hashCode to return an appropriate int
// that will serve as the hash code used by hash table
class Point {
	int x,y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/*
	public boolean equals(Point p) {
		BAD BAD IDEA
	}
	*/
	
	// this is the way to write equals 
	public boolean equals(Object o) {
		
		if (o == null || !(o instanceof Point)) {
			return false;
		}
		Point other = (Point)o;
		return x == other.x && y == other.y;
	}
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
	// implement a hashCode method, that overrides the Object class'
	// hashCode method - the trick is to use the String class' hashCode
	// on a string representation of this object
	public int hashCode() {
		return (""+x+y).hashCode();
	}
}

public class HashCodeDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// We want to store Point objects in a hash table.
		// Since there is no separation of key and value,
		// we will use the HashSet class instead
		
		// use constructor, with default load factor threshold (0.75)
		HashSet<Point> points = new HashSet<Point>(100);
		Point[] pointset = {
				new Point(2,3), new Point(2,5), 
				new Point(4,5), new Point(1,6)
		};
		
		for (Point p: pointset) {
			// there is no put method in HashSet, only add
			// if this already is in the hash table, then the
			// method returns without doing anything, with false
			if (points.add(p)) {
				System.out.println(p + " added to hash set");
			}
			// 1. the add method will call the Point hashCode method on
			// the point instance to get its hash code, 
			// 2. map to the hash table using hashcode mod table size,
			// 3. search in the chain at that table index, and
			// 4. insert if the point is not already there
		}
		
		// there is no get method since there is no key->value
		// mapping, but you can use contains to check if an item
		// is in the table
		Point p = new Point(2,5);
		if (points.contains(p)) {
			System.out.println(p + " is in the hash set");
		}
		// 1. the contains method will call the Point hashCode method on
		// the point instance to get its hash code, 
		// 2. map to the hash table using code mod table size,
		// 3. search in the chain at that table index
	}

}
