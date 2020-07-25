package linear;

class StringNode {
	String data;
	StringNode next;
	public StringNode(String data, StringNode next) {
		this.data = data;
		this.next = next;
	}

	public String toString() {
		return data;
	}
}

public class StringLL {
	StringNode front;
	int size;
	
	public StringLL() {  // empty linked list to start with
		front = null;
		size = 0;
	}
	
	public void addFront(String item) {
		front = new StringNode(item, front);
		size++;
	}

	public void deleteFront() {
		if (front == null) {
			System.out.println("Empty list, nothing to delete");
			return;
		}
		front = front.next;
		size--;
	}

	public boolean search(String target) {
		for (StringNode ptr=front; ptr != null; ptr=ptr.next) {
			if (target == ptr.data) {
				return true;
			}
		}
		return false;
	}
}
