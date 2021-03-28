/*
	All code written around the time of 2018-05-02
	unless explicitly stated otherwise in comments
	or in JavaDoc @since tag
*/
//imports
import java.util.Scanner;

/**
* This class implements the postfix expression to calculate a "hash" of a passed in string.
*
* @author Josh Colvin
* @since 2018-05-02
*/
public class ColvinKHash {
	//Holds the information for the hash key
	private static int[] stack; 
	private static int maxKeyCapacity;
	private static int top;
	//Holds the information for the string
	private static int[] strCodes; 
	private static int maxCapacity;
	
	/**
	* Repeats passed in symbol a fixed number of times. Used as a visual divider on the command line/terminal.
	* 
	* @param symbol The symbol(s) to be displayed as a divider
	* @param num Number of times symbol is drawn in a line to the screen
	*/
	private static void printBuffer(String symbol,int num){
		
		for(int r = 1; r <= num; r++){
			System.out.print(symbol);
		}
		
	}
	
	/**
	* A method that takes a string and formats it according 
	* to the column length passed as a second argument. 
	* Whitespaces are added based on that second argument. 
	* 
	* @param str String to be formatted 
	* @param colLength Length of formatted string 
	* @return Formatted string 
	*/
	//The following is previous code I had written to practice calling a function for formating a title
	//Takes in a string and the desired column length
	//Depending on whether the string is odd or even, the function will adapt accordingly
	public static String formatString(String str,int colLength){
		int whiteSpace,whiteSpaceLeft,whiteSpaceRight;
		String updatedStr = "";
		
		//if the string is even in length, the space on either side will be equal
		//unless the string is even and the collumn length is odd, then there will
		//be an equal number of spaces to that of the length of the string on the
		//right of the string, while the left side will contain one less space than
		//the right
		if((str.length()%2) == 0){
			whiteSpace = colLength - (str.length() + 1);
			whiteSpaceLeft = (whiteSpace + 1) / 2;
			whiteSpaceRight = whiteSpace - whiteSpaceLeft + 1;
			for(int r = 0;r < whiteSpaceLeft; r++){
				updatedStr += " "; 
			}
			updatedStr += str;
			for(int j = 0;j < whiteSpaceRight; j++){
				updatedStr += " ";
			}
		}
		//else if the string is odd in length, the right side will have one space more than the left
		//unless both the string and column length are odd, then there will be an even number of spaces
		//on either side of the odd lengthed string
		else if((str.length() % 2) != 0) {
			whiteSpace = colLength - (str.length() + 1);
			whiteSpaceLeft = (whiteSpace + 1) / 2;
			whiteSpaceRight = whiteSpace - whiteSpaceLeft + 1;
			for(int r = 0;r < whiteSpaceLeft; r++){
				updatedStr += " ";
			}
			updatedStr += str;
			for(int j = 0; j < whiteSpaceRight; j++){
				updatedStr += " ";
			}
		}
		return updatedStr;
	}
	
	/**
	* This method is for handling the visual formatting 
	* of a title and a symbol divider placed above and 
	* below to create a distinguished.
	* 
	* @param phrase String to be displayed as title 
	* @param symbol Symbol to act as divider for title 
	* @param num Length or number of symbols to display above and below title
	*/
	public static void printTitle(String phrase,String symbol,int num){
		
		printBuffer(symbol,num);
		System.out.printf("\n");
		System.out.print(formatString(phrase,num));
		System.out.printf("\n");
		printBuffer(symbol,num);
		System.out.printf("\n");
		
	}
	
	/**
	* Allows for viewing the topmost integer value 
	* from the postfix expression stack without 
	* removing it.
	* 
	* @return Integer value from the top of the postfix expression stack 
	*/
	public static int peekCodes(){
		return stack[top];
	}
	
	/**
	* Resets the postfix expressoin stack pointer
	* so that any new values added to stack will
	* overwrite old ones.
	*/
	public static void resetCodes(){
		top = -1;
	}
	
	/**
	* Takes the passed in Integer value and adds it 
	* to the top of the postfix expression stack.
	* 
	* @param val Integer value to add to the postfix expression stack
	*/
	public static void pushCode(int val){
		top++;//Initially at position -1;
		stack[top] = val;
	}
	
	/**
	* Removes the topmost integer from the postfix 
	* expression stack and returns it's value.
	* 
	* @return Integer value from the top of the postfix expression stack
	*/
	public static int pullCode(){
		int currentTop = peekCodes();
		top--;
		return currentTop;
	}
	
	/**
	* A method for instantiating the array for holding 
	* the codes, the stack for maintaining the postfix 
	* calculations, and the starting position of the 
	* stack: -1.
	*/
	//This function initializes the parameters for
	// the "to be hashed" string
	public static void initialize(){
		strCodes = new int[maxCapacity];
		stack = new int[maxKeyCapacity];
		top = -1;
	}
	
	/**
	* This method takes a string and returns if the passed 
	* in argument is a valid, basic operation.
	* 
	* @param part A string to be tested 
	* @return Whether part is one of the basic operations
	*/
	public static Boolean isOperator(String part){
		if((part.equals("+")) || (part.equals("-")) || (part.equals("/")) || (part.equals("*")) || (part.equals("%"))){
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	* Method that takes two numbers and operates on them
	* specified by the operator parameter.
	* 
	* @param num1 First integer numbers to be operated on 
	* @param num2 Second integer numbers to be operated on 
	* @param operator Operation to execute on the two passed in ASCII numbers 
	* @return Value resulting from num1 and num2 being operated on 
	*/
	public static int evaluate(int num1, int num2, String operator){
		int total = 0;
		if(operator.equals("+")){
			total = num1 + num2;
		}
		else if (operator.equals("-")){
			total = num1 - num2;
		}
		else if (operator.equals("/")){
			total = num1 / num2;
		}
		else if (operator.equals("*")){
			total = num1 * num2;
		}
		else if (operator.equals("%")){
			total = num1 % num2;
		}
		return total;
	}
	
	/**
	* Finds and returns the largest integer value of 
	* the string array of ASCII codes passed in as 
	* an argument.
	* 
	* @param parts String array of ASCII codes to be searched through 
	* @return Maximum integer value found within string array after parsing 
	* @since 2021-03-28
	*/
	public static int maxValue(String[] parts){
		int currentChar;
		int max = 0;
		
		for(String part:parts){
			if(!isOperator(part)){
				/* 
					Previously incorrectly stored currentChar and max within same block 
					after checking initial 0 >= 0. Resulted in currentChar and max 
					always being equal. Max became equal to last indice in postfix 
					expression which is not always true 
				*/
				currentChar = Integer.parseInt(part);
				if (currentChar >= max) {
					max = currentChar;	
				}
			}
		}
		
		return max;
	}
	
	/**
	* Stores ASCII code values of inputted string into a 
	* new array of length specified by integer parameter.
	* 
	* @param arrayLength The length of the new array containing the inputted string codes 
	*/
	public static void resize(int arrayLength){
		int[] newCodeArray = new int[arrayLength];
		for(int i = 0;i < arrayLength; i++){
			newCodeArray[i] = strCodes[i];
		}
		strCodes = newCodeArray;
		maxCapacity = arrayLength;
	}
	
	/**
	* This method divides each ASCII code of inputted string 
	* by predetermined number. This method is called when 
	* the number of characters in the inputted string is 
	* greater than the max indice of the inputted postfix
	* expression.
	* 
	* @param avgDivisor Number to divide each inputted string codes by
	*/
	public static void average(int avgDivisor){
		for(int i = 0;i < maxCapacity; i++){
			strCodes[i] = strCodes[i] / avgDivisor;
		}
	}
	
	/**
	* A method that turns the characters, of the passed in 
	* string, into ASCII codes. The codes are then replaced 
	* by the respective indice values contained in the postfix 
	* expression parameter "key". The calulations are 
	* then executed based on the expression, and the final 
	* result is returned.
	* 
	* @param s Plain text string to be hashed 
	* @param key String containing the postfix expression
	* @return Integer value calculated by running parameter "s" through postfix parameter "key" 
	*/
	public static int KHash(String s,String key){
		char[] chrs = s.toCharArray(); 
		String[] keyParts = key.split("");
		int num1, num2, maxKey;
		int result = 0;
		
		maxKey = maxValue(keyParts) + 1;//indicates the number to be modded with the average the ASCII codes
		
		//loop through characters of string, and turns them into
		// ASCII codes that can be hashed
		for(int i = 0;i < maxCapacity; i++){
			strCodes[i] = (int)chrs[i]%128;
		}
		
		/*
			If the length of the characters of the string is greater than
			the max key, minimize the array to only include significant
			indices then average
			the greatest index key + 1
		*/
		if(maxCapacity > maxKey) {
			for (int r = maxKey; r < maxCapacity; r++){
				strCodes[r % maxKey] += strCodes[r];
			}
			//average the codes using max key
			int divisor = maxCapacity / maxKey;
			average(divisor);
			resize(maxKey);
		}
		
		/* 
			If the length of the max indice in the postfix expression is
			greater than the length of the input string, replace each indice 
			in expression by modding itself with length of input string
			
			ex.
				string length = 3;
				max key = 6
				indices before modding
				{0,1,2,3,4,5}
				indice % max key 
				indices after modding 
				{0,1,2,0,1,2}
		*/
		//Will replace the indices in the key with the respective ASCII codes
		for(int r = 0; r < maxKeyCapacity; r++){
			if(!isOperator(keyParts[r])){
				// Updated on 28 March 2021 to include "if" statement 
				if (maxKey > maxCapacity) {
					keyParts[r] = strCodes[Integer.parseInt(keyParts[r]) % maxCapacity] + "";	
				}
				else {
					keyParts[r] = strCodes[Integer.parseInt(keyParts[r])] + "";
				}
			}
		}
		
		//Uses the array of codes to substitue into the key to be
		// calculated and then returned to the user
		for(String part:keyParts){
			if(isOperator(part)){
				num2 = pullCode();
				num1 = pullCode();
				result = evaluate(num1, num2, part);
				pushCode(result);
			}
			else{
				pushCode(Integer.parseInt(part));
			}
		}
		
		return pullCode();
	}
	public static void main(String[] args){
		//Variables
			//title
			String title = "KHASH - Like Johnny Cash except encrypted";
			String endTitle = "KHASH - Limitless code in a codeless world";//The Office reference
			String symbol = "*";
			int symbolRepeat = 60;
			
			Scanner in = new Scanner(System.in);
			String phrase, key, response;
			int keyCode;
			
		printTitle(title,symbol,symbolRepeat);	
		
		//Ask user once for 
		System.out.print("Enter the phrase: ");
		phrase = in.nextLine();
		maxCapacity = phrase.length();
		System.out.print("Enter the key: ");
		//in.next();
		key = in.nextLine();
		maxKeyCapacity = key.length();
		
		initialize();
		keyCode = KHash(phrase,key);
		
		System.out.println("The khash of " + phrase + " with the key " + key + " is " + keyCode + ".");
		
		do {
			System.out.print("Try again? ");
			response = in.next();
			
			if(response.equalsIgnoreCase("y")) {
				in.nextLine();
				System.out.print("Enter the phrase (<enter> to keep the current): ");
				String phraseResponse = in.nextLine();
				
				if(phraseResponse.equals("")){
					//Keep the phrase
				}
				else{
					phrase = phraseResponse;
					maxCapacity = phrase.length();
				}
				
				System.out.print("Enter the key (<enter> to keep the current): ");
				String keyResponse = in.nextLine();
				
				if(keyResponse.equals("")){
					//Keep the key
				}
				else {
					key = keyResponse;
					maxKeyCapacity = key.length();
					resetCodes();
				}
			
				initialize();
				keyCode = KHash(phrase,key);
		
				System.out.println("The khash of " + phrase + " with the key " + key + " is " + keyCode + ".");
			}
		}while(response.equalsIgnoreCase("y"));
		
		System.out.printf("\n");
		printTitle(endTitle, symbol, symbolRepeat);
	}
	
}