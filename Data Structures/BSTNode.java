package tree;

// BST nodes hold objects that implement compareTo method
public class BSTNode<T extends Comparable<T>> {
	T data; BSTNode<T> left, right;
	public BSTNode(T data) {
		this.data = data;
		left = null;
		right = null;
	}

}
