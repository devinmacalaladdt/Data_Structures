package math;

/**
 * This class encapsulates a BigInteger, i.e. a positive or negative integer with 
 * any number of digits, which overcomes the computer storage length limitation of 
 * an integer.
 * 
 */
public class BigInteger {

	/**
	 * True if this is a negative integer
	 */
	 boolean negative;
	
	/**
	 * Number of digits in this integer
	 */
	 int numDigits;
	
	/**
	 * Reference to the first node of this integer's linked list representation
	 * NOTE: The linked list stores the Least Significant Digit in the FIRST node.
	 * For instance, the integer 235 would be stored as:
	 *    5 --> 3  --> 2
	 */
	 DigitNode front;
	
	/**
	 * Initializes this integer to a positive number with zero digits, in other
	 * words this is the 0 (zero) valued integer.
	 */
	public BigInteger() {
		negative = false;
		numDigits = 0;
		front = null;
	}
	
	/**
	 * Parses an input integer string into a corresponding BigInteger instance.
	 * A correctly formatted integer would have an optional sign as the first 
	 * character (no sign means positive), and at least one digit character
	 * (including zero). 
	 * Examples of correct format, with corresponding values
	 *      Format     Value
	 *       +0            0
	 *       -0            0
	 *       +123        123
	 *       1023       1023
	 *       0012         12  
	 *       0             0
	 *       -123       -123
	 *       -001         -1
	 *       +000          0
	 *       
	 * 
	 * @param integer Integer string that is to be parsed
	 * @return BigInteger instance that stores the input integer
	 * @throws IllegalArgumentException If input is incorrectly formatted
	 */
	public static BigInteger parse(String integer) 
	throws IllegalArgumentException {
		// THE FOLLOWING LINE IS A PLACEHOLDER SO THE PROGRAM COMPILES
		// YOU WILL NEED TO CHANGE IT TO RETURN THE APPROPRIATE BigInteger
		
		
		BigInteger b = new BigInteger();
		boolean addZero = false;
		boolean illegalSpace = false;
		
		if(integer.length()>0){
			
			for(int i = 0; i<integer.length(); i++){
				
				if(Character.isDigit(integer.charAt(i)) || integer.charAt(i)=='-' || integer.charAt(i)==' '){
					
					switch(integer.charAt(i)){
					
					case ' ': 	
								if(i==0 || i == integer.length()-1){
									
									continue;
									
								}	
								else if(i>0 && i<integer.length() && (Character.isDigit(integer.charAt(i-1)) && Character.isDigit(integer.charAt(i+1)))){
									
									throw new IllegalArgumentException();
									
								}else{
									
									continue;
									
								}
					case '-': b.negative = true; illegalSpace = true; continue;
					case '0': 
						if(!addZero){
							
							continue;
						
						}else{
							
							b.front = new DigitNode(Integer.parseInt(integer.substring(i, i+1)),b.front); b.numDigits++;
							
						}
						
						break;
						
					default: b.front = new DigitNode(Integer.parseInt(integer.substring(i, i+1)),b.front); illegalSpace = true;
					b.numDigits++;
					addZero = true;
					break;
					
					
					}
						
				}else{
					
					throw new IllegalArgumentException();
					
				}
				
			}
			
		}
		
		return b;
	}
	
	
	
	
	
	
	
	
	/**
	 * Adds an integer to this integer, and returns the result in a NEW BigInteger object. 
	 * DOES NOT MODIFY this integer.
	 * NOTE that either or both of the integers involved could be negative.
	 * (Which means this method can effectively subtract as well.)
	 * 
	 * @param other Other integer to be added to this integer
	 * @return Result integer
	 */
	public BigInteger add(BigInteger other) {
		// THE FOLLOWING LINE IS A PLACEHOLDER SO THE PROGRAM COMPILES
		// YOU WILL NEED TO CHANGE IT TO RETURN THE APPROPRIATE BigInteger
		boolean makeNeg = false;
		BigInteger b1 = new BigInteger();
		b1 = b1.parse(this.toString());
		BigInteger b2 = new BigInteger();
		b2 = b2.parse(other.toString());
		BigInteger b3 = new BigInteger();	

			if(other.numDigits>numDigits){
				
				b1 = b1.parse(other.toString());
				b2 = b2.parse(this.toString());
				
			}else{
				
				b1 = b1.parse(this.toString());
				b2 = b2.parse(other.toString());;
				
			}
			
		
		if(b1.negative && b2.negative){
			
			makeNeg = true;
			
		}else if(b1.negative){
			
			b1.makeNeg();
			return b1.subtract(b2);
			
		}else if(b2.negative){
			
			b2.makeNeg();
			return b2.subtract(b1);
			
		}
		
		
		String integer = "";
		DigitNode curr2 = b2.front;
		int carryOver = 0;
		for(DigitNode curr = b1.front; curr!=null; curr = curr.next){
			
			
			if(curr2==null){
				
				if(curr.next==null){
					
					integer = ((curr.digit)+carryOver)+integer;
					
				}else{
					
					integer = (((curr.digit)+carryOver)%10)+integer;
					carryOver = ((curr.digit)+carryOver)/10;
					
				}
				
				
			}else{
				
				if(curr.next==null){
					
					integer = ((curr.digit)+(curr2.digit)+carryOver)+integer;
					
				}else{
					
					integer = (((curr.digit)+(curr2.digit)+carryOver)%10)+integer;
					carryOver = ((curr.digit)+(curr2.digit)+carryOver)/10;
					
				}
				
				curr2 = curr2.next;
			}
			

			
		}
		
		if(makeNeg){
			
			integer = "-"+integer;
			
		}
		
		b3 = b3.parse(integer);
		
		
		return b3;
	}
	
	
		
	
	private void makeNeg(){
		
		for(DigitNode curr = this.front; curr!=null;curr = curr.next ){
			
			curr.digit *=-1;
			
		}
		
	}
	
	
	private BigInteger subtract(BigInteger other){
		
		BigInteger large = new BigInteger();
		large = large.parse(this.toString());
		large.negative = this.negative;
		BigInteger small = new BigInteger();
		small = small.parse(other.toString());
		small.negative = other.negative;
		BigInteger b = new BigInteger();
		String sum = "";
		boolean makeNeg = false;
		
		
		if(this.numDigits>other.numDigits){
			
			large = large.parse(this.toString());
			large.negative = this.negative;
			small = small.parse(other.toString());
			small.negative = other.negative;
			
		}else if(other.numDigits>this.numDigits){
			
			large = large.parse(other.toString());
			large.negative = other.negative;
			small = small.parse(this.toString());
			small.negative = this.negative;
			
		}else{
			
			
			DigitNode curr1 = large.front;
			DigitNode curr2 = small.front;
			
			for(;;curr1 = curr1.next){
				
				if(curr1==null){
					
					break;
					
				}
				
				if(curr1.next==null){
					
					if(Math.abs(curr1.digit)>Math.abs(curr2.digit)){
						
						large = large.parse(this.toString());
						large.negative = this.negative;
						small = small.parse(other.toString());
						small.negative = other.negative;
						break;
						
					}else if(Math.abs(curr2.digit)>Math.abs(curr1.digit)){
						
						large = large.parse(other.toString());
						large.negative = other.negative;
						small = small.parse(this.toString());
						small.negative = this.negative;
						break;
						
					}else{
						
						DigitNode temp1 = large.front;
						DigitNode temp2 = small.front;
						
						for(;temp1!=null;temp1 = temp1.next){
							
							
							if(Math.abs(temp1.digit)>Math.abs(temp2.digit)){
								
								large = large.parse(this.toString());
								large.negative = this.negative;
								small = small.parse(other.toString());
								small.negative = other.negative;
								break;
								
							}else if(Math.abs(temp2.digit)>Math.abs(temp1.digit)){
								
								large = large.parse(other.toString());
								large.negative = other.negative;
								small = small.parse(this.toString());
								small.negative = this.negative;
								break;
								
							}
							
							if(temp1.next==null){
								
								b = b.parse("0");
								return b;
								
							}
							
							temp2 = temp2.next;
							
						}
						
						
					}
					
				}
				
				curr2 = curr2.next;
				
				
			}
			
			
		}
		
		if(small.negative){
			
			small.makeNeg();
			
		}
		if(large.negative){
			
			large.makeNeg();
			
		}
		
		
		if(large.negative){
			
			large.makeNeg();
			large.negative = false;
			small.makeNeg();
			small.negative = true;
			makeNeg = true;
			
		}
		
		
		
		DigitNode curr2 = small.front;
		for(DigitNode curr1 = large.front; curr1!=null; curr1 = curr1.next){
			
			if(curr2!=null){
				
				if(Math.abs(curr1.digit)<Math.abs(curr2.digit)){
					
					DigitNode temp = curr1;
					
					do{
					
						if(temp==curr1){
							
							curr1.digit+=10;
							
						}else{
							
							temp.digit+=9;
							
						}
						
						
						temp = temp.next;
						
					
						
					}while(temp.digit==0);
					
					temp.digit--;

					
					
				}
				
				sum = (curr1.digit+curr2.digit)+sum;
				curr2 = curr2.next;
				
			}else{
				
				sum = curr1.digit+sum;
				
			}
			
			
		}
		
		
		
		b = b.parse(sum);
		
		if(makeNeg){
			
			b.negative = true;
			large.negative = true;
			small.negative = false;
			makeNeg = false;
			
		}
		
		return b;
		
	}
		
		
	
	/**
	 * Returns the BigInteger obtained by multiplying the given BigInteger
	 * with this BigInteger - DOES NOT MODIFY this BigInteger
	 * 
	 * @param other BigInteger to be multiplied
	 * @return A new BigInteger which is the product of this BigInteger and other.
	 */
	public BigInteger multiply(BigInteger other) {
		// THE FOLLOWING LINE IS A PLACEHOLDER SO THE PROGRAM COMPILES
		// YOU WILL NEED TO CHANGE IT TO RETURN THE APPROPRIATE BigInteger
		
		BigInteger large = new BigInteger();
		large = large.parse(this.toString());
		BigInteger small = new BigInteger();
		small = small.parse(other.toString());
		boolean makeNeg = false;
		String ans = "";
		
		if(large.numDigits>other.numDigits){
			
			large = large.parse(this.toString());;
			small = small.parse(other.toString());;
			
		}else{
			
			large = large.parse(other.toString());
			small = small.parse(this.toString());;
			
		}
		
	if(large.negative && small.negative){
		
		makeNeg = false;
		
	}else if(large.negative || small.negative){
			
		makeNeg = true;
			
	}
		
		
		BigInteger sum = new BigInteger();
		String smallZeros = "";
		String largeZeros = "";
		
		for(DigitNode curr1 = small.front; curr1!=null;curr1 = curr1.next){
			
			
			for(DigitNode curr2 = large.front; curr2!=null;curr2 = curr2.next){
				
				BigInteger num = new BigInteger();
				num = num.parse((curr2.digit*curr1.digit)+largeZeros+smallZeros);
				sum = sum.add(num);
				largeZeros+="0";
				
			}
			
			largeZeros = "";
			smallZeros +="0";
			
		}
		
		if(makeNeg){
			
			sum.negative = true;
			
		}
		
		return sum;
		
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (front == null) {
			return "0";
		}
		
		String retval = front.digit + "";
		for (DigitNode curr = front.next; curr != null; curr = curr.next) {
				retval = curr.digit + retval;
		}
		
		if (negative) {
			retval = "-" + retval;
		}
		
		return retval;
	}
	
}
