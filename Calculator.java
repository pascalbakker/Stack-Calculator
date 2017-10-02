import java.io.File;
import java.io.FileNotFoundException;
import java.util.EmptyStackException;
import java.util.Scanner;

/** Calculates the solution to math expressions 
 * 
 * Wentworth Institute of Technology
 * COMP 2000
 * Stack Project Calculator
 * 
 *  @author 	Pascal Bakker
 *  @author 	Ross Usinger
 *  @author 	Kevin Cotter
 * @version     %I%, %G%
 * @since       1.0
 */
public class Calculator{

	
    /** Calculates result of expression given. Returns an integer and cannot handle doubles.
     * 
     * @param input The expression to handle
     * @return The integer result of the expression
     */
    public static double compute(String input){
    	//String input = formatExpression(unformatedInput).trim(); //Adds white space before every operator.
    	
    	//Creates char array and converts input to array of chars.
    	char [] items = new char[input.length()];
    	for(int i=0;i<items.length;i++)
    		items[i]=input.charAt(i);

    	//Initializes both arrays.
    	VectorStack<Double> numberStack = new VectorStack<Double>(); 	//Holds numbers
    	VectorStack<Character> operationStack = new VectorStack<Character>(); //Holds operations
    	
    	try{	//Try to calculate solution to expression. Catch EmptyStackException
    		String fullNumber ="";
    		//Go through each char in items.
	    	for(int i=0;i<input.length();i++){
	    		
	    		//Do not add anything to the stack if it is white space
	    		if(!Character.isWhitespace(items[i])){
	    			//If a char is a number or a period(for decimal places)
	    			if((items[i]>='0'&&items[i]<='9')||items[i]=='.'){
	    				//If the next character is not a number between 1 and 9, add the current char the number string and push it.
	    				//Then reset the fullNumber string so it can be used again.
	    				//If the current character is a number and at the end of the array, do the same process
	    				if((i+1<input.length()&&
	    						!((items[i+1]>='0'&&items[i+1]<='9')||items[i+1]=='.'))||
	    						i+1==input.length()){
	    					fullNumber+=items[i];
	    					numberStack.push(Double.parseDouble(fullNumber));
	    					fullNumber="";
	    				} 
	    				//If the next character is also a number, then add it and continue through the loop.
	    				else{
	    					fullNumber+=items[i];
	    				}
	    				
	    			//If ( add it to the operator stack
	    			}else if(items[i]=='('){
	    				operationStack.push(items[i]);
	    				
	    			//If ) then calculate expression inside ()
	    			}else if(items[i]==')'){
	    				//Keep calculating the expression until ( is reached in operatorStack
	    				while(!numberStack.isEmpty()&&operationStack.peek()!='('){
	    					char operation = operationStack.pop();	//Gets operation to conduct
	    					double number1 = numberStack.pop();	//Gets first number
	    					double number2 = numberStack.pop();	//Gets second number
	    					double result = calculateResultOf2Numbers(number2 ,number1, operation);//Conducts operation
	    					numberStack.push(result); //Pushes number to numberStack. Then use number again next loop
	    				} //End of while loop
	    				
	    				operationStack.pop(); //Pop ( since it is no longer needed
	    			}
	    			
	    			//If number is a operator
	    			else if(items[i] == '+' || items[i] == '-' ||items[i] == '*' || items[i] == '/'){
	    				while(!operationStack.isEmpty()&&!numberStack.isEmpty()&&	//Avoids emptystack exception by checking both stacks.
	    						!((items[i]=='*'||items[i]=='/')&&(operationStack.peek()=='+'||operationStack.peek()=='-')||
	    			    		   (operationStack.peek()==')'||operationStack.peek()=='('))){ //Determines if the top of operation stack has a greater operation than the current item. If it does not have a greater importance, then it skips the loop
	    					char operation = operationStack.pop();	//Gets operation to conduct
	    					double number1 = numberStack.pop();	//Gets first number
	    					double number2 = numberStack.pop();	//Gets second number
	    					double result = calculateResultOf2Numbers(number2 ,number1, operation);//Conducts operation
	    					numberStack.push(result); //Pushes number to numberStack.
	    				}
	    				operationStack.push(items[i]); //Push operator to stack. Without this, the operator stack will be empty.
	    			} //else
	    		} //If items[i]!=' '
	    	} //for loop
	    	
	    	//Finish up calculations
	    	while(!operationStack.isEmpty()){ //While there are still operations
				char operation = operationStack.pop();	//Gets operation to conduct
				double number1 = numberStack.pop();	//Gets first number
				double number2 = numberStack.pop();	//Gets second number
				double result = calculateResultOf2Numbers(number2 ,number1, operation);//Conducts operation
				numberStack.push(result); //Pushes number to numberStack.
			}
	    	return numberStack.pop(); //Get last number in the stack. Should be only number left.
    	}catch(EmptyStackException ex){
    		System.out.println("Error: "+ex);
    	}
		return 0;
    	
    }//End of method
    

    /** Calculates the result of 2 numbers given an operator
     * 
     * @param number1 The first integer.
     * @param number2 The second integer.
     * @param operation The operation which is either addition,subtraction,multiplication, or division.
     * @return the result of the 2 numbers
     */
    public static double calculateResultOf2Numbers(double number1 ,double number2, char operation){
    	int result=0; //Result of 2 numbers
    	switch(operation){
    	case '+': return number1+number2; //If '+' add both numbers
    	case '-': return number1-number2; //If '-' subtract the numbers
    	case '*': return number1*number2; //If * multiply both numbers
    	case '/': return divide(number1,number2); //Divides both numbers(Checks for divide by zero case)
    	}
    	return result;
    }
    
    /** Finds the ration of 2 numbers. Throws exception if dividing by zero.
     * 
     * @param num1 the quotient
     * @param num2 the divisor
     * @return the result of num1/num2
     */
    public static double divide(double num1,double num2){
    	if (num2==0)
    		throw new IllegalArgumentException("Cant divide by 0."); 
    	else
    		return num1/num2;
    }
    
    /** Reads the 2 text files containing the expressions and prints the result of each line.
     * 
     * @param filename The location of the file
     * @throws FileNotFoundException
     */
    public static void readFromTextFile(String filename) throws FileNotFoundException{
    	File equations = new File(filename);
    	@SuppressWarnings("resource")
		Scanner in = new Scanner(equations);
    	while(in.hasNextLine()){
    		String equation = in.nextLine();
        	System.out.println("Expression: "+equation);
    		System.out.println("Result: "+compute(equation)+"\n");
    	}
    }
    
    
    /** Main method. Tests the files with the expressions.
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException
    {	

    	System.out.println("================================================================");
    	System.out.println("Infix Calculator Expressions - valid multi-digit -- 2016-10-04 01.txt");
    	System.out.println("================================================================");
    	readFromTextFile("Infix Calculator Expressions - valid multi-digit -- 2016-10-04 01.txt");
    	System.out.println("================================================================");
    	System.out.println("Infix Calculator Expressions - valid -- 2016-10-13 01.txt");
    	System.out.println("================================================================");
    	readFromTextFile("Infix Calculator Expressions - valid -- 2016-10-13 01.txt");

    }
}
