package linear;

import java.util.Scanner;
import java.util.NoSuchElementException;

public class ParenMatch {

	public static boolean parenMatch(String expr) {
		Stack<Character> stk = new Stack<>();
		
		for (int i=0; i < expr.length(); i++) {
			char ch = expr.charAt(i);
			if (ch == '[' || ch == '(') {
				stk.push(ch);   // auto boxing of primitive char into Character before sending in to push method
				continue;
			}
			if (ch == ']' || ch == ')') {
				try {
					char ch2 = stk.pop();   // auto unboxing of returned Character into char before assigning to LHS
					if ((ch2 == '[' && ch != ']') || (ch2 == '(' && ch != ')')) {
						return false;  // mismatched parens or square brackets
					}
				} catch (NoSuchElementException e) {
					return false; // too many ']' or ')'
				}
				
			}
		} // for
		
		return stk.isEmpty();  // is stack is not empty, false returned => too many open '(' or '['
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter expression, 'quit' to stop: ");
		String line = sc.nextLine();
		while (!"quit".equals(line)) {
			if (parenMatch(line)) {
				System.out.println("Matched!");
			} else {
				System.out.println("Did not match");
			}
			System.out.print("Enter expression, 'quit' to stop: ");
			line = sc.nextLine();
		}
		sc.close();
	}

}
