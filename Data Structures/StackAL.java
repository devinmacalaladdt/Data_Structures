package linear;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class StackAL<T> {
	
	private ArrayList<T> items;
	
	public StackAL() {
		items = new ArrayList<>();
	}
	
	public StackAL(int cap) {
		items = new ArrayList<>(cap);  // initial capacity = cap
	}
	
	public void push(T item) {
		items.add(item);   // adds to end, end = top
	}

	public T pop() 
	throws NoSuchElementException {
		try {
			return items.remove(items.size()-1); // removes and returns last item
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new NoSuchElementException();
		}
	}
	
	public int size() {
		return items.size();
	}
	
	public boolean isEmpty() {
		return items.isEmpty();
	}
	
	public void clear() {
		items.clear();
	}
}
