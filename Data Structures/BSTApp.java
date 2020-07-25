package tree;

// User MUST implement the Comparable interface
class User implements Comparable<User> {
	String userId;
	String pwd;
	String name;
	
	public User(String userId, String pwd, String name) {
		this.userId = userId;
		this.pwd = pwd;
		this.name = name;
	}
	
	public int compareTo(User other) {
		return userId.compareTo(other.userId);
	}
}

public class BSTApp {

	public static void main(String[] args) {
		// BST with String objects
		BST<String> strBST = new BST<String>();
		strBST.insert("fences");
		strBST.insert("godfather");
		strBST.insert("bee movie");
		strBST.insert("last samurai");
		strBST.insert("the revenant");
		System.out.println(strBST.search("last samurai"));
		System.out.println(strBST.search("bourne supremacy"));
		
		// BST with User objects
		BST<User> userBST = new BST<User>();
		userBST.insert(new User("kwinslet","kw65!abc","Kate Winslet"));
		userBST.insert(new User("ielba","gg7%$7hh", "Idris Elba"));
		userBST.insert(new User("estone","ytq217gvc","Emma Stone"));
		// get password for a user, matching on id
		User user = userBST.search(new User("estone",null,null));
		System.out.println(user.pwd);
		
	}

}
