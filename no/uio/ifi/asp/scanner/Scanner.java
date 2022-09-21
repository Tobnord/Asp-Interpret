// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.scanner;

import java.io.*;
import java.lang.Thread.State;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
	private LineNumberReader sourceFile = null;
	private String curFileName;
	private ArrayList<Token> curLineTokens = new ArrayList<>();
	private Stack<Integer> indents = new Stack<>();
	private final int TABDIST = 4;

	private String stringLiteral = "";
	private boolean stringUnderConstruction = false;

	public Scanner(String fileName) {
		curFileName = fileName;
		indents.push(0);

		try {
			sourceFile = new LineNumberReader(
					new InputStreamReader(
							new FileInputStream(fileName),
							"UTF-8"));
		} catch (IOException e) {
			scannerError("Cannot read " + fileName + "!");
		}
	}

	private void scannerError(String message) {
		String m = "Asp scanner error";
		if (curLineNum() > 0)
			m += " on line " + curLineNum();
		m += ": " + message;

		Main.error(m);
	}

	public Token curToken() {
		while (curLineTokens.isEmpty()) {
			readNextLine();
		}
		return curLineTokens.get(0);
	}

	public void readNextToken() {
		if (!curLineTokens.isEmpty())
			curLineTokens.remove(0);
	}

	private void readNextLine() {
		curLineTokens.clear();

		// Read the next line:
		String line = null;
		try {
			line = sourceFile.readLine();
			if (line == null) {
				sourceFile.close();
				sourceFile = null;
			} else {
				Main.log.noteSourceLine(curLineNum(), line);
			}
		} catch (IOException e) {
			sourceFile = null;
			scannerError("Unspecified I/O error!");
		}

		// -- Must be changed in part 1:
		String stringLiteral = "";
		if (line == null) {
			curLineTokens.add(new Token(eofToken, curLineNum()));
		} else {
			String s = expandLeadingTabs(line);
			indentHandling(s);

			// Stops createTokens from reading comments
			if (s.contains("#")) {
				s = s.substring(0, s.indexOf("#"));
			}

			raiseErrorIfNumberOfQuotesOdd(s);

			if (!s.isEmpty()) {

				String[] wordArray = s.split("\\s+");

				for (String word : wordArray) {
					createTokens(word);
				}
			}
			

			// Terminate line:
			// if line is not blank or every char before the # is not blank, then generate a
			// newline token
			if (!line.isBlank()) {
				if (!line.contains("#")) {
					curLineTokens.add(new Token(newLineToken, curLineNum()));
				} else if (!line.substring(0, line.indexOf('#')).isBlank()) {
					curLineTokens.add(new Token(newLineToken, curLineNum()));
				}
			}
		}

		for (Token t : curLineTokens)
			Main.log.noteToken(t);
	}

	private void raiseErrorIfNumberOfQuotesOdd(String s) {
		List<Integer> indexOfQuotesList = new ArrayList<Integer>();
				
		if (s.contains("\"") || s.contains("\'")) {
			char[] charArray = s.toCharArray();
			for (int i = 0; i < charArray.length; i++) {
				if (charArray[i] == '\"' || charArray[i] == '\'') {
					indexOfQuotesList.add(i);
				}
			}
		}

		if (indexOfQuotesList.size() % 2 != 0) {
			Main.error("Line " + curLineNum() + " has no en quote for the string literal");
		}
	}

	private void createTokens(String s) {

		char[] chars = s.toCharArray();
		

		// Loops through the current line, and creates tokens of the appropriate kind
		for (int i = 0; i < chars.length; i++) {

			// Initiates creation of string literal tokens if the char is a quotation
			if (stringUnderConstruction || isQuotation(chars[i])) {
				
				i = createStringLiteralToken(chars, i);
				
			}

			// Initiates creation of digit tokens if the char is a digit
			else if (isDigit(chars[i])) {
				i = createDigitTokens(chars, i);
			}

			// Initiates creation of operator tokens if the char is an operator
			else if (isOperator(chars[i])) {
				i = createOperatorToken(chars, i);
			}

			// Initiates createation of nameTokens and keywordTokens
			// correctly
			else if (isLetterAZ(chars[i]) || chars[i] == '_') {
				//System.out.println("\nNAME OR KEYWORD found:" + chars[i]);
				i = createNameAndKeywordTokens(chars, i);
			}

			else {
				// This is not supposed to happen!
				System.out.println("Unexpected case for createToken(): " + chars[i]);
			}
		}
	}

	//Creates a stringToken, and returns the index for the end of the string
	private int createStringLiteralToken(char[] chars, int startIndex) {
		int stopIndex = startIndex;

		if (stringUnderConstruction) {
			stringLiteral += chars[startIndex];
		}

		stringUnderConstruction = true;

		for (int i = startIndex + 1; i < chars.length; i++) {
			stopIndex = i;
			
			if (isQuotation(chars[i])) {
				
				Token token = new Token(stringToken, curLineNum());
				token.stringLit = stringLiteral;
				curLineTokens.add(token);
				
				stringUnderConstruction = false;
				stringLiteral = "";
				
				return stopIndex;
			}
			else {
				stringLiteral += chars[i];
			}
		}

		stringLiteral += " ";
		
		return stopIndex;
	}

	//Creates the appropriate name- or keywordToken, and returns the index for the end of the word
	private int createNameAndKeywordTokens(char[] chars, int startIndex) {
		int stopIndex = startIndex;
		String currentWord = "" + chars[startIndex];
		Token token = new Token(nameToken, curLineNum());

		for (int i = startIndex + 1; i < chars.length; i++) {
			
			token.name = currentWord;

			if (token.checkResWords()) {
				currentWord = "";
				curLineTokens.add(token);
				return stopIndex;
			}

			if (!isDigit(chars[i]) && !isLetterAZ(chars[i]) && chars[i] != '_') {
				curLineTokens.add(token);
				stopIndex = i - 1;
				return stopIndex;
			} else {
				currentWord += chars[i];
			}
			stopIndex = i;
		}
		//Handles name- or keywordTokens at the end of the line
		if(!currentWord.isBlank()){
			token.name = currentWord;
			if (token.checkResWords()) {
				currentWord = "";
				curLineTokens.add(token);
			}
			else{
				curLineTokens.add(token);
			}
		}
		return stopIndex;
	}

	//Creates the appropriate operatorToken, and returns the index for the end of the operator(s)
	private int createOperatorToken(char[] chars, int startIndex) {
		String operator = "" + chars[startIndex];
		int stopIndex = startIndex;

		if (chars.length > startIndex + 1) {
			if (isOperator(chars[startIndex + 1]) && isNextOperatorValid(chars[startIndex], chars[startIndex + 1])) {
				operator += chars[startIndex + 1];
				stopIndex++;
			}
			else if (chars[startIndex] == '-' && isDigit(chars[startIndex + 1])) {
				createDigitTokens(chars, startIndex);
			}
		}

		for (TokenKind tk : EnumSet.range(astToken, semicolonToken)) {
			if (operator.equals(tk.image)) {
				curLineTokens.add(new Token(tk, curLineNum()));
				return stopIndex;
			}
		}

		return stopIndex;
	}

	//Returns true if an operator is a double operator
	private boolean isNextOperatorValid(char first, char second) {
		String doubleOperator = "" + first + second;
		boolean result = false;

		for (TokenKind tk : EnumSet.range(astToken, semicolonToken)) {
			if (doubleOperator.equals(tk.image)) {
				result = true;
			}
		}
		
		return result;
	}

	//Creates the appropriate integer- or floatToken, and returns the index for the end of the number
	private int createDigitTokens(char[] chars, int startIndex) {
		String digitString = "" + chars[startIndex];
		int stopIndex = startIndex;

		if(digitString.contains("-")){
			return stopIndex+1;
		}

		for (int i = startIndex + 1; i < chars.length; i++) {
			if (!isDigit(chars[i]) && chars[i] != '.') {
				Token token = new Token(integerToken, curLineNum());
				if (digitString.contains(".")) {
					token.floatLit = Float.parseFloat(digitString);
				} else {
					token.integerLit = Integer.parseInt(digitString);
				}
				curLineTokens.add(token);

				stopIndex = i - 1;
				return stopIndex;
			} else {
				digitString += (chars[i]);
			}
			stopIndex = i;
		}

		//Handles digits at the end of the line
		if(!digitString.isBlank()){
            Token token = new Token(integerToken, curLineNum());
            if(digitString.contains(".")){
                token.floatLit = Float.parseFloat(digitString);
            }
            else{
                token.integerLit = Integer.parseInt(digitString);
            }
            curLineTokens.add(token);
        }
        return stopIndex;
	}

	private void indentHandling(String s) {

		String currentString = s;
		System.out.println("|" + s + "|");

		if (s.isBlank()) {
			System.out.println("-- blank");
			return;
		} else if (s.contains("#")) {
			String s2 = s.substring(0, s.indexOf("#"));

			if (s2.isBlank()) {
				System.out.println("-- comment");
				return;
			} else {
				currentString = s2;
				System.out.println("-- code before comment");
			}
		}

		int n = findIndent(currentString);

		while (n != indents.peek()) {
			if (n > indents.peek()) {
				indents.push(n);
				curLineTokens.add(new Token(indentToken, curLineNum()));
			} else if (n < indents.peek()) {
				indents.pop();
				curLineTokens.add(new Token(dedentToken, curLineNum()));
			}
		}
	}

	public int curLineNum() {
		return sourceFile != null ? sourceFile.getLineNumber() : 0;
	}

	private int findIndent(String s) {
		int indent = 0;

		while (indent < s.length() && s.charAt(indent) == ' ')
			indent++;
		return indent;
	}

	private String expandLeadingTabs(String s) {

		int n = 0;
		String newString = "";

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == ' ') {
				n++;
				newString += c;
			} else if (c == '\t') {
				int m = 4 - (n % 4);
				for (int j = 0; j < m; j++) {
					newString += ' ';
					n++;
				}
			} else {
				newString += c;
			}
		}
		return newString;
	}

	private boolean isLetterAZ(char c) {
		return ('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z') || (c == '_');
	}

	private boolean isDigit(char c) {
		return '0' <= c && c <= '9';
	}

	//Returns True if char c is an operator
	private boolean isOperator(char c) {
		return c == '*'
				|| c == '=' || c == '/' || c == '>' || c == '<'
				|| c == '-' || c == '!' || c == '%' || c == '+'
				|| c == ':' || c == ',' || c == '[' || c == ']'
				|| c == '{' || c == '}' || c == '(' || c == ')'
				|| c == ';';
	}

	//Returns True if char c is a quotation mark
	private boolean isQuotation(char c) {
		return c == '\'' || c == '\"';
	}

	public boolean isCompOpr() {
		TokenKind k = curToken().kind;
		// -- Must be changed in part 2:
		return false;
	}

	public boolean isFactorPrefix() {
		TokenKind k = curToken().kind;
		// -- Must be changed in part 2:
		return false;
	}

	public boolean isFactorOpr() {
		TokenKind k = curToken().kind;
		// -- Must be changed in part 2:
		return false;
	}

	public boolean isTermOpr() {
		TokenKind k = curToken().kind;
		// -- Must be changed in part 2:
		return false;
	}

	public boolean anyEqualToken() {
		for (Token t : curLineTokens) {
			if (t.kind == equalToken)
				return true;
			if (t.kind == semicolonToken)
				return false;
		}
		return false;
	}
}
