//Josh Colvin

//imports
import java.util.Scanner;

public class ColvinKHash {
	//Holds the information for the hash key
	private static int[] stack; 
	private static int maxKeyCapacity;
	private static int top;
	//Holds the information for the string
	private static int[] strCodes; 
	private static int maxCapacity;
	
	private static void printBuffer(String symbol,int num){
		
		for(int r = 1; r <= num; r++){
			System.out.print(symbol);
		}
		
	}
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
	public static void printTitle(String phrase,String symbol,int num){
		
		printBuffer(symbol,num);
		System.out.printf("\n");
		System.out.print(formatString(phrase,num));
		System.out.printf("\n");
		printBuffer(symbol,num);
		System.out.printf("\n");
		
	}
	public static int peekCodes(){
		return stack[top];
	}
	public static void resetCodes(){
		top = -1;
	}
	public static void pushCode(int val){
		top++;//Initially at position -1;
		stack[top] = val;
	}
	public static int pullCode(){
		int currentTop = peekCodes();
		top--;
		return currentTop;
	}
	//This function initializes the parameters for
	// the "to be hashed" string
	public static void initialize(){
		strCodes = new int[maxCapacity];
		stack = new int[maxKeyCapacity];
		top = -1;
	}
	public static Boolean isOperator(String part){
		if((part.equals("+")) || (part.equals("-")) || (part.equals("/")) || (part.equals("*")) || (part.equals("%"))){
			return true;
		}
		else {
			return false;
		}
	}
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
	public static int maxValue(String[] parts){
		int currentChar = 0;
		int max = 0;
		
		for(String part:parts){
			if((!isOperator(part)) && (currentChar >= max)){
				currentChar = Integer.parseInt(part);
				max = currentChar;
			}
		}
		
		return max;
	}
	public static void resize(int arrayLength){
		int[] newCodeArray = new int[arrayLength];
		for(int i = 0;i < arrayLength; i++){
			newCodeArray[i] = strCodes[i];
		}
		strCodes = newCodeArray;
		maxCapacity = arrayLength;
	}
	public static void average(int avgDivisor){
		for(int i = 0;i < maxCapacity; i++){
			strCodes[i] = strCodes[i] / avgDivisor;
		}
	}
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
		
		//If the length of the characters of the string is greater than
		//the max key, minimize the array to only include significant
		//indices then average
		//the greatest index key + 1
		if(maxCapacity > maxKey) {
			for (int r = maxKey; r < maxCapacity; r++){
				strCodes[r % maxKey] += strCodes[r];
			}
			//average the codes using max key
			int divisor = maxCapacity / maxKey;
			average(divisor);
			resize(maxKey);
		}
		
		//Will replace the indices in the key with the respective ASCII codes
		for(int r = 0; r < maxKeyCapacity; r++){
			if(!isOperator(keyParts[r])){
				keyParts[r] = strCodes[Integer.parseInt(keyParts[r])] + "";
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
		System.out.print(key);
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