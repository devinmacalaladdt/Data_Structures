package tree;

import java.util.ArrayList;
import java.util.NoSuchElementException;

// BST does not allow duplicates
// only allows objects that implement compareTo
public class BST<T extends Comparable<T>> {

	BSTNode<T> root;
	int size;
	
	public BST() {   // constructor initializes tree to empty
		root = null;
		size = 0;
	}
	
	public T search(T key) {
		BSTNode<T> ptr = root;
		while (ptr != null) {
			int c = key.compareTo(ptr.data);
			if (c == 0) {
				return ptr.data;
			}
			/*
			if (c < 0) {
				ptr = ptr.left;
			} else {
				ptr = ptr.right;
			}
			*/
			// this is the same thing as the if-else block, using the TERNARY operator
			ptr = c < 0 ? ptr.left : ptr.right;
		}
		return null;
	}
	
	public void insert(T item) 
	throws IllegalArgumentException {
		BSTNode<T> prev=null, ptr=root;
		int c=0;
		while (ptr != null) {   // search until fail
			c = item.compareTo(ptr.data);
			if (c == 0) { // duplicate, not allowed
				throw new IllegalArgumentException("Duplicates not allowed");
			}
			prev=ptr;
			ptr = c < 0 ? ptr.left : ptr.right;
		}
		// create new node for item
		BSTNode<T> temp = new BSTNode<T>(item);
		if (prev == null) { // tree was empty
			root = temp;
			size = 1;
			return;
		}
		if (c < 0) {
			prev.left = temp;
		} else {
			prev.right = temp;
		}
		size++;
	}
	
	public T delete(T key) 
	throws NoSuchElementException {
		// search and locate key
		BSTNode<T> x=root, p=null;
		T hold=null;
		while (x != null) {
			int c = key.compareTo(x.data);
			if (c == 0) {
				hold = x.data;   // hold on for eventual return
				break;
			}
			// step up p first
			p = x;
			// this is the same thing as the if-else block, using the TERNARY operator
			x = c < 0 ? x.left : x.right;
		}
		
		// key is not in tree
		if (x == null) {
			throw new NoSuchElementException();
		}
		
		// check case 3 first, since it falls through to case 1 or case 2
		if (x.left != null && x.right != null) {
			// find the predecessor of x
			p = x;
			BSTNode<T> y = x.left; // left turn
			while (y.right != null) {
				p = y;
				y = y.right;
			}
			// copy y's data into x
			x.data = y.data;
			// set x to y, ready to fall through to case 1 or case 2
			x = y;
		}
	
		// check case 1
		/*  SEPARATE CODE BLOCK FOR CASE 1 NOT NEEDED
		 * CASE 2 BLOCK THAT FOLLOWS ALSO WORKS FOR CASE 1
		if (x.left == null && x.right == null) {
			if (p == null) {
				root = null;
				size = 0;
				return hold;
			}
		
			if (x == p.left) {
				p.left = null;
			} else {
				p.right = null;
			}
			size--;
			return hold;
		}
		*/
		
		// only other scenario is case 2, no need for a check
		if (p == null) {
			root = x.left != null ? x.left : x.right;
			size--;
			return hold;
		}
		if (x == p.right) {
			p.right = x.left != null ? x.left : x.right;
		} else {
			p.left = x.left != null ? x.left : x.right;
		}
		size--;
		return hold;
	}
	
	public ArrayList<T> sort() {
		ArrayList<T> list = new ArrayList<T>(size);
		inorder(root, list);
		return list;
	}
	
	private static <T extends Comparable<T>>
	void inorder(BSTNode<T> root, ArrayList<T> list) {
		if (root == null) {
			return;
		}
		inorder(root.left, list);  // L
		list.add(root.data);  // V
		inorder(root.right, list);  // R
	}
	
	
	
}
