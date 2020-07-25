package linear;

import java.util.NoSuchElementException;

class Node<T> {   // Node class has a generic type parameter T
	T data;
	Node<T> next;
	public Node(T data, Node<T> next) { // constructor name does NOT have a <T> next to it
		this.data = data;
		this.next = next;
	}

	public String toString() {
		return data.toString();
	}
}

public class LinkedList<T> {
	Node<T> front;
	int size;
	
	public LinkedList() {  // empty linked list to start with
		front = null;
		size = 0;
	}
	
	public void addFront(T item) {
		front = new Node<T>(item, front);
		size++;
	}

	public void deleteFront() 
	throws NoSuchElementException {
		if (front == null) {
			throw new NoSuchElementException();
		}
		front = front.next;
		size--;
	}

	public boolean search(T target) {
		for (Node<T> ptr=front; ptr != null; ptr=ptr.next) {
			if (target.equals(ptr.data)) {
				return true;
			}
		}
		return false;
	}
	
	// while loop version, stylized single-line output
	public void traverse() {
		if (front == null) {
			System.out.println("Empty list");
			return;
		}
		System.out.print(front.data);  // first item
		Node<T> ptr=front.next;    // prepare to loop starting with second item
		while (ptr != null) {
			System.out.print(" --> " + ptr.data);
			ptr = ptr.next;
		}
		System.out.println();
	}
	
	public int getSize() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public static void main(String[] args) {
		LinkedList<Integer> intll = new LinkedList<>();
		int x=5;
		intll.addFront(x);  // auto boxes value of x into an Integer object
		intll.addFront(new Integer(15));
		intll.addFront(-10);  // auto boxes -10 into an Integer object
		intll.traverse();
		intll.deleteFront();
		intll.traverse();
		intll.deleteFront();
		intll.traverse();
		intll.deleteFront();
		intll.traverse();
		intll.deleteFront(); // trying to delete from an empty list
	}
}
