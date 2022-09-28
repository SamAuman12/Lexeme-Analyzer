
/**
 * CSC 345-01 Assignment #1
 * 
 * On my honor, Samuel Auman, this assignment is my own work.  
 * I, Samuel Auman, will follow the instructor's rules and processes 
 * related to academic integrity as directed in the course syllabus.
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Students -- Add your import declarations here

import java.util.regex.*;

public class A1 {

	// Students -- Add your constants here
	public static int charClass;
	public static char[] lexeme;
	public static char nextChar;
	public static int lexLen;
	public static int token;
	public static int nextToken;
	public static int index;
	public static String text = "";
	public static String number = "";
	public static boolean broken = false;

	public static final int LETTER = 0;
	public static final int DIGIT = 1;
	public static final int UNKNOWN = 99;
	public static final int POINT = 30;

	public static final int FLOATDCL = 0;
	public static final int INTDEC = 1;
	public static final int PRINT = 2;
	public static final int ID = 3;
	public static final int ASSIGN = 4;
	public static final int PLUS = 5;
	public static final int MINUS = 6;
	public static final int INUM = 7;
	public static final int FNUM = 8;

	public static final char DECIMAL = '.';

	public static void main(String[] args) {
		try {
			// Do NOT make any changes to the following TWO lines
			File file = new File(args[0]);
			Scanner sc = new Scanner(file); // *** Do not make any other Scanners ***//

			// Students -- Your code and methods calls go here
			while (sc.hasNextLine()) {
				text += sc.nextLine();
			}
			
			text = text.replaceAll("\\s", "");
			for (int i = 0; i <= text.length() - 1; i++) {
				nextChar = text.charAt(i);
				if (Character.isDigit(nextChar) || nextChar == '.') {
					while (Character.isDigit(nextChar) || nextChar == '.') {
						number += nextChar;
						i++;
						nextChar = text.charAt(i);
						charClass = DIGIT;
						if (!Pattern.matches("\\d+\\.\\d*", number)) {
							if (!Pattern.matches("\\d+", number)) {
								String newNumber = "";
								for (int j = 0; j < number.length() - 1; j++) {
									newNumber += number.charAt(j);
								}
								number = newNumber;
								broken = true;
								lex();
								break;
							}
						}
					}
					if (broken == true) {
						break;
					}
					lex();
					char lastChar = text.charAt(i);
					getChar(lastChar);
					lex();
					number = "";
				} else {
					getChar(nextChar);
					lex();
				}
			}

			sc.close();
		} catch (

		FileNotFoundException e) {
			System.out.println("ERROR - cannot open front.in \n");
		}
	}

	// Students -- Add your method declarations here
	public static void getChar(char c) {
		if (Character.isLetter(c)) {
			charClass = LETTER;
		} else {
			charClass = UNKNOWN;
		}
	}

	public static int lex() {
		String s = "";
		switch (charClass) {
			case LETTER:
				s += nextChar;
				if (Pattern.matches("[^fip*]", s)) {
					nextToken = ID;
				} else if (Pattern.matches("f", s)) {
					nextToken = FLOATDCL;
				} else if (Pattern.matches("p", s)) {
					nextToken = PRINT;
				} else if (Pattern.matches("i", s)) {
					nextToken = INTDEC;
				}
				break;
			case DIGIT:
				s = number;
				if (Pattern.matches("\\d+", s)) {
					nextToken = INUM;
				} else if (s.contains(".")) {
					nextToken = FNUM;
				}
				break;
			case UNKNOWN:
				s = lookup(nextChar);
				break;
		}
		System.out.println("Next token is: " + nextToken + ", Next lexeme is " + s);
		return nextToken;
	}

	public static String lookup(char ch) {
		String unknown = "";
		switch (ch) {
			case '=':
				unknown += nextChar;
				nextToken = ASSIGN;
				break;
			case '+':
				unknown += nextChar;
				nextToken = PLUS;
				break;
			case '-':
				unknown += nextChar;
				nextToken = MINUS;
				break;
			default:
				broken = true;
				break;
		}
		return unknown;
	}

}