package task;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;
/*s
 * This class determines whether, 
 * by the given bracket sequence, it is correct
 */
public class BracketChecker{
	
	private Stack<Character> mainStack; // stack holding opening brackets
	private String sequence;
	
	public BracketChecker(){			// class constructor that initialize variables
		mainStack = new Stack<Character>(); 
		sequence = "";
	}
	public void readFile(String fileName) throws IOException{
		BufferedReader bufferedReader = null;               // trying to read sequence from file
   		bufferedReader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
           		sequence += line;	                              
       	}
        bufferedReader.close();
	}

  	public String bracketsCheck(){               	// method which checks correctness of the parentheses sequence
    	if(sequence.charAt(0) == ')' || sequence.charAt(0) == '}' || sequence.charAt(0) == ']') // if string begins with closing parenthesis, this string is incorrect 
      		return "incorrect";
   		boolean isCorrect = true;
   		Character bracket;
   		for(int i = 0; i < sequence.length(); i++){
       		bracket = sequence.charAt(i);
       		if(bracket == '(' || bracket == '{' || bracket == '[')  // if there is an opening parenthesis, we push it to the stack
       			mainStack.push(bracket);
       		else{															  // if there is a closing parenthesis,
       			isCorrect = isBracketPair(mainStack.firstElement(), bracket); // we check, is this parenthesis make pair with top of stack 
          			if(isCorrect)												// if parenthesis makes pair,
          				mainStack.pop(); 										// we remove this pair 
          			else
       		    		return "incorrect";											// else - sequence is incorrect
      		  	}    
      		}
   		String answer = ""; // variable for answer  
		answer = (isCorrect)?"correct":"incorrect";  //checks isCorrect and writes the result in "answer"

		return answer;
  	}
  	private boolean isBracketPair(Character bracket_1, Character bracket_2){ // method that checks whether the parentheses are paired
    		if(bracket_1 == '(' && bracket_2 == ')') return true;
    		if(bracket_1 == '[' && bracket_2 == ']') return true;
    		if(bracket_1 == '{' && bracket_2 == '}') return true;
    		return false;
	}  
}
