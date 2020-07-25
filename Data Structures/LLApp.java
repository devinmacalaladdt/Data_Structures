package linear;

public class LLApp {


	public static IntNode addFront(int item, IntNode front) {
		return new IntNode(item, front);
	}

	public static IntNode deleteFront(IntNode front) {
		if (front == null) {
			System.out.println("Empty list, nothing to delete");
			return null;
		}
		return front.next;
	}

	public static boolean search(IntNode front, int target) {
		for (IntNode ptr=front; ptr != null; ptr=ptr.next) {
			if (target == ptr.data) {
				return true;
			}
		}
		return false;
	}

	/** FOR LOOP VERSION
	public static void traverse(IntNode front) {
		for (IntNode ptr=front; ptr != null; ptr=ptr.next) {
			System.out.println(ptr.data);
		}
	}
	**/
	
	// while loop version, stylized single-line output
	public static void traverse(IntNode front) {
		if (front == null) {
			System.out.println("Empty list");
			return;
		}
		System.out.print(front.data);  // first item
		IntNode ptr=front.next;    // prepare to loop starting with second item
		while (ptr != null) {
			System.out.print("->" + ptr.data);
			ptr = ptr.next;
		}
		System.out.println();
	}
	
	public static void addAfter(IntNode front, int newItem, int oldItem) {
		for (IntNode ptr=front; ptr != null; ptr=ptr.next) {
			if (oldItem == ptr.data) {
				IntNode tmp = new IntNode(newItem, null);
				tmp.next = ptr.next;
				ptr.next = tmp;
				return;
			}
		}
	}
	
	public static IntNode delete(IntNode front, int item) {
		if (front == null) {  // empty list
			System.out.println("Empty list, can't delete item");
			return front;
		}
		if (front.data == item) {
			return front.next;
			/* same as:
			 * front = front.next;
			 * return front;
			 */
		}
		IntNode ptr=front, prev=null;
		while (ptr != null && ptr.data != item) {
			prev = ptr;   // change prev first before stepping up ptr
			ptr = ptr.next;
		}
		// here because either ptr is null or we found the item
		if (ptr == null) {
			System.out.println("Item not found");
			return front;
		}
		prev.next = ptr.next;
		return front;
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IntNode front = null;
        traverse(front);  // test traverse on empty list
        front = addFront(4,front);
        traverse(front);
        front = addFront(6,front);
        traverse(front);
        front = addFront(8,front);
        traverse(front);
        
        front = deleteFront(front);
        traverse(front);
        front = deleteFront(front);
        traverse(front);
        front = deleteFront(front);
        traverse(front);
        front = deleteFront(front);
        
        // use StringLL
        StringLL strLL = new StringLL();
        strLL.addFront("cs112");
        strLL.addFront("cs111");
        strLL.deleteFront();
        boolean found = strLL.search("cs112");
	}

}
