package apps;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {

	/**
	 * Expression to be evaluated
	 */
	String expr;                
    
	/**
	 * Scalar symbols in the expression 
	 */
	ArrayList<ScalarSymbol> scalars;   
	
	/**
	 * Array symbols in the expression
	 */
	ArrayList<ArraySymbol> arrays;
    
    /**
     * String containing all delimiters (characters other than variables and constants), 
     * to be used with StringTokenizer
     */
    public static final String delims = " \t*+-/()[]";
    
    /**
     * Initializes this Expression object with an input expression. Sets all other
     * fields to null.
     * 
     * @param expr Expression
     */
    public Expression(String expr) {
        this.expr = expr;
    }

    /**
     * Populates the scalars and arrays lists with symbols for scalar and array
     * variables in the expression. For every variable, a SINGLE symbol is created and stored,
     * even if it appears more than once in the expression.
     * At this time, values for all variables are set to
     * zero - they will be loaded from a file in the loadSymbolValues method.
     */
    public void buildSymbols() {
    		scalars = new ArrayList<ScalarSymbol>();
    		arrays = new ArrayList<ArraySymbol>();
    		String nums = "0123456789";
    		ArrayList<String> l = new ArrayList<String>();
    		String expr1 = expr;
    		String temp = "";
    		String contain = "";
    		for(int c = 0; c<expr1.length();c++){
    			
    			if(delims.contains(expr1.substring(c, c+1))){
    				
    				if(temp==""){
    					
    					continue;
    					
    				}
    				
    				if(contain.contains(" "+temp+" ")){
    					
    					temp = "";
    					continue;
    					
    				}else if(nums.contains(temp.substring(0,1))){
    					
    					temp = "";
    					continue;
    					
    				}else{
    					
    					if(expr1.substring(c,c+1).equals("[")){
        					
        					arrays.add(new ArraySymbol(temp));
        					contain+=" "+temp+" ";
        					temp = "";
        					
        				}else{
        					
        					scalars.add(new ScalarSymbol(temp));
        					contain+=" "+temp+" ";
        					temp = "";
        					
        				}
    					
    				}
    			
    				continue;
    			}
    			
    			temp += expr1.substring(c,c+1);
    			
    			if(c+1==expr1.length()){
    				
    				if(nums.contains(temp.substring(0,1))){
    					
    					temp = "";
    					continue;
    					
    				}
    				
    				scalars.add(new ScalarSymbol(temp));
    				
    			}
    			
    		}
    		
    		
    		printScalars();
    		printArrays();
    		
    		
    		
    }
    
    
    
    
    /**
     * Loads values for symbols in the expression
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     */
    public void loadSymbolValues(Scanner sc) 
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String sym = st.nextToken();
            ScalarSymbol ssymbol = new ScalarSymbol(sym);
            ArraySymbol asymbol = new ArraySymbol(sym);
            int ssi = scalars.indexOf(ssymbol);
            int asi = arrays.indexOf(asymbol);
            if (ssi == -1 && asi == -1) {
            	continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                scalars.get(ssi).value = num;
            } else { // array symbol
            	asymbol = arrays.get(asi);
            	asymbol.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    String tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    asymbol.values[index] = val;              
                }
            }
        }
        
        
    }
    
    
    /**
     * Evaluates the expression, using RECURSION to evaluate subexpressions and to evaluate array 
     * subscript expressions.
     * 
     * @return Result of evaluation
     */
    public float evaluate() {
    		
    	return evaluater(expr.replaceAll(" ", ""));
    		
    }
    
    private float evaluater(String temp){
    	
    	Stack varStack = new Stack();
    	Stack opStack = new Stack();
    	String nums = "0123456789";
    	
    	String ops = "+-/*()[]";
    	String operators = "";
    	
    	for(int i = 0; i<temp.length();i++){
    		
    		if(ops.contains(temp.substring(i,i+1))){
    			
    			operators+=temp.substring(i,i+1);
    			
    		}
    		
    	}
    	
    	StringTokenizer st = new StringTokenizer(temp.substring(0,temp.length()),delims);
    	
    	
    	int charCount = 0;
    	int currentIndex=0;
    	
    	while(st.hasMoreElements()){
    		
    		
    		if(charCount<operators.length() && operators.charAt(charCount)=='('){
    			
    			String subTemp = "";
    			int numOpen = 0;
    			int numClose = 0;
    			int index = currentIndex;
    			
    			do{
    				
    				subTemp += temp.charAt(index);
    				
    				if(temp.charAt(index)=='('){
    					
    					numOpen++;
    					
    				}else if(temp.charAt(index)==')'){
    					
    					numClose++;
    					
    				}
    				
    				index++;
    				
    			}while(numOpen!=numClose);
    			
    			subTemp = subTemp.substring(1,subTemp.length()-1);
    			StringTokenizer st1 = new StringTokenizer(subTemp,delims);
    			int i = 0;
    			int x = st1.countTokens();
    			
    			while(i<x){
    				
    				st.nextToken();
    				i++;
    				
    			}
    			
    			varStack.push(evaluater(subTemp));
    			currentIndex += subTemp.length()+2;
    			numOpen = 0;
    			numClose = 0;
    			charCount++;
    			while(numClose<=numOpen && charCount<operators.length()){
    				
    				if(operators.charAt(charCount)=='('){
    					
    					numOpen++;
    					
    				}else if(operators.charAt(charCount)==')'){
    					
    					numClose++;
    					
    				}
    				
    				charCount++;
    				
    			}
    			
    			
    			
    			
    			
    			
    		}else{
    			
    			String var = st.nextToken();
        		
        		float variable = 0;
        		if(nums.contains(var.substring(0, 1))){
        			
        			variable = Float.parseFloat(var);
        			
        		}else{
        			
        			for(ScalarSymbol s: scalars){
        				
        				if(s.name.equals(var)){
        					
        					variable = s.value;
        					break;
        					
        				}
        				
        			}
        			
        			for(ArraySymbol a: arrays){
        				
        				if(a.name.equals(var)){
        					
        					String subTemp = "";
        	    			int numOpen = 0;
        	    			int numClose = 0;
        	    			int index = currentIndex+var.length();
        	    			
        	    			do{
        	    				
        	    				subTemp += temp.charAt(index);
        	    				
        	    				if(temp.charAt(index)=='['){
        	    					
        	    					numOpen++;
        	    					
        	    				}else if(temp.charAt(index)==']'){
        	    					
        	    					numClose++;
        	    					
        	    				}
        	    				
        	    				index++;
        	    				
        	    			}while(numOpen!=numClose);
        	    			subTemp = subTemp.substring(1,subTemp.length()-1);
        	    			StringTokenizer st1 = new StringTokenizer(subTemp,delims);
        	    			int i = 0;
        	    			int x = st1.countTokens();
        	    			
        	    			while(i<x){
        	    				
        	    				st.nextToken();
        	    				i++;
        	    				
        	    			}
        	    			variable = (float)a.values[(int)evaluater(subTemp)];
        	    			currentIndex += subTemp.length()+2;
        	    			numOpen = 0;
        	    			numClose = 0;
        	    			charCount++;
        	    			while(numClose<=numOpen && charCount<operators.length()){
        	    				
        	    				if(operators.charAt(charCount)=='['){
        	    					
        	    					numOpen++;
        	    					
        	    				}else if(operators.charAt(charCount)==']'){
        	    					
        	    					numClose++;
        	    					
        	    				}
        	    				
        	    				charCount++;
        	    				
        	    			}

        	    			
        					
        				}
        				
        			}
        			
        		}
        		
        		varStack.push(variable);
        		currentIndex+=var.length();
    			
    		}
    		
    		
    		
    		if(!opStack.isEmpty()){
    			
    			if(opStack.peek().equals("*")){
    				
    				varStack.push((float)varStack.pop()*(float)varStack.pop());
    				opStack.pop();
    				
    			}else if(opStack.peek().equals("/")){
    				
    				Float f = (float)varStack.pop();
    				varStack.push((float)varStack.pop()/f);
    				opStack.pop();
    				
    			}
    			
    		}
    		
    		if(charCount<operators.length()){
    			
    			opStack.push(operators.substring(charCount, charCount+1));
    			charCount++;
    			currentIndex++;
    			
    		}
    		
    		
    		if(opStack.isEmpty()){
    			
    			break;
    			
    		}

    	}
    	
    	Stack opStack1 = new Stack();
    	Stack varStack1 = new Stack();
    	
    	while(opStack.size()!=0){
    		
    		opStack1.push(opStack.pop());
    		
    	}
    	while(varStack.size()!=0){
    		
    		varStack1.push(varStack.pop());
    		
    	}
    	
    	
    	
    	while(opStack1.size()!=0){
    	
    		if(opStack1.peek().equals("+")){
    			
    			varStack1.push((float)varStack1.pop()+(float)(varStack1.pop()));
    			opStack1.pop();
    			
    		}else if(opStack1.peek().equals("-")){
    			
    			varStack1.push((float)varStack1.pop()-(float)varStack1.pop());
    			opStack1.pop();
    			
    		}
    		
    		
    		
    	}
    	
    
    	return (float)varStack1.peek();
    	
    }

    /**
     * Utility method, prints the symbols in the scalars list
     */
    public void printScalars() {
        for (ScalarSymbol ss: scalars) {
            System.out.println(ss);
        }
    }
    
    /**
     * Utility method, prints the symbols in the arrays list
     */
    public void printArrays() {
    		for (ArraySymbol as: arrays) {
    			System.out.println(as);
    		}
    }

}
