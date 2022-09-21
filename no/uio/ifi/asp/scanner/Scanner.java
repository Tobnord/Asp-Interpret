// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
	private LineNumberReader sourceFile = null;
	private String curFileName;
	private ArrayList<Token> curLineTokens = new ArrayList<>();
	private Stack<Integer> indents = new Stack<>();
	private final int TABDIST = 4;

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
		boolean eof = false;
		try {
			line = sourceFile.readLine();
			if (line == null) {
				sourceFile.close();
				sourceFile = null;
				eof = true;
			} else {
				Main.log.noteSourceLine(curLineNum(), line);
			}
		} catch (IOException e) {
			sourceFile = null;
			scannerError("Unspecified I/O error!");
		}

		// -- Must be changed in part 1:

		if (line == null) {
			curLineTokens.add(new Token(eofToken, curLineNum()));
		} else {
			String expandedLeadingTabsString = expandLeadingTabs(line);
			indentHandling(expandedLeadingTabsString);
			String stringWithoutWhitespace = expandedLeadingTabsString.replaceAll("\\s+", "");
			createTokens(stringWithoutWhitespace);

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

	private void createTokens(String s) {

		// Stops createTokens from reading comments
		if (s.contains("#")) {
			s = s.substring(0, s.indexOf("#"));
		}
		if (s.isEmpty()) {
			return;
		}

		char[] chars = s.toCharArray();

		// Loops through the current line, and creates tokens of the appropriate kind
		for (int i = 0; i < chars.length; i++) {

			// Initiates creation of digit tokens if the char is a digit
			if (isDigit(chars[i])) {
				//System.out.println("\nDIGIT found:" + chars[i]);
				i = createDigitTokens(chars, i);
			}

			// Initiates creation of operator tokens if the char is an operator
			else if (isOperator(chars[i])) {
				// System.out.println("\nOPERATOR found:" + chars[i]);
				i = createOperatorToken(chars, i);
			}

			// Initiates creation of string literal tokens if the char is a quotation
			else if (isQuotation(chars[i])) {
				//System.out.println("\nQUOTATION found:" + chars[i]);
				i = createStringLiteral(chars, i);
			}

			// Prays to Allah the allmighty that it creates nameTokens and keywordTokens
			// correctly
			else if (isLetterAZ(chars[i]) || chars[i] == '_') {
				//System.out.println("\nNAME OR KEYWORD found:" + chars[i]);
				i = createNameAndKeywordTokens(chars, i);
			}

			else {
				// Something is critically wrong here!
				System.out.println("Say what now? " + chars[i]);
			}
		}
	}

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

	private int createStringLiteral(char[] chars, int startIndex) {
		int stopIndex = startIndex;
		String currentWord = "";

		for (int i = startIndex + 1; i < chars.length; i++) {
			if (isQuotation(chars[i])) {
				Token token = new Token(stringToken, curLineNum());
				token.stringLit = currentWord;
				curLineTokens.add(token);

				stopIndex = i;
				return stopIndex;
			} else {
				currentWord += (chars[i]);
			}
			stopIndex = i;
		}

		// This is not supposed to happen! Syntax Error!
		// No end-quote found
		Main.error("No endqoute for string");

		return stopIndex;
	}

	private int createOperatorToken(char[] chars, int startIndex) {
		String operator = "" + chars[startIndex];
		int stopIndex = startIndex;

		// System.out.println("Operator: " + operator);
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
			// System.out.println("checking for Operator: "+operator);
			if (operator.equals(tk.image)) {
				// System.out.println("Operator: "+operator+" found in tk.image!");
				//System.out.println("final Operator kind: " + tk + "\nwith operator: " + operator);
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

	private int createDigitTokens(char[] chars, int startIndex) {
		String digitString = "" + chars[startIndex];
		int stopIndex = startIndex;

		//Stops generation of digitTokens if the digit contains a "-"
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

	private boolean isOperator(char c) {
		return c == '*'
				|| c == '=' || c == '/' || c == '>' || c == '<'
				|| c == '-' || c == '!' || c == '%' || c == '+'
				|| c == ':' || c == ',' || c == '[' || c == ']'
				|| c == '{' || c == '}' || c == '(' || c == ')'
				|| c == ';';
	}

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
