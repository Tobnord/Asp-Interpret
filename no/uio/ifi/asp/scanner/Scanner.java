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
		String expandedLeadingTabsString = expandLeadingTabs(line);
		indentHandling(expandedLeadingTabsString);
		createTokens(expandedLeadingTabsString);

		// Terminate line:
		curLineTokens.add(new Token(newLineToken, curLineNum()));

		for (Token t : curLineTokens)
			Main.log.noteToken(t);
	}

	private void createTokens(String s) {

		if(s.isEmpty()) {
			return;
		}

		// List words delimited by whitespace
		String[] strArr = s.split("\\s+");

		// Loop through words in array
		for (String word : strArr) {
			// List chars in word
			char[] chars = word.toCharArray();

			List<Character> charList = new ArrayList<>();
			for (char ch: chars) {
				charList.add(ch);
			}
				

			boolean containsDot = false;
			boolean justNumbers = true;
			String stringLit = "";
			boolean stringLitUnderConstruction = false;

			if (TokenKind.contains(word)) {
				createKeywordTokens(word);
				continue;
			}

			if (word.contains(".")) {
				containsDot = true;
			}

			// Loop through chars in word
			for (int i = 0; i < chars.length; i++) {

				if (!isDigit(chars[i]) && chars[i] != '.') {
					justNumbers = false;
				}

				if (stringLitUnderConstruction && chars[i] != '\"' && chars[i] != '\'') {
					stringLit += chars[i];
				}

				if (!stringLitUnderConstruction) {
					int increment = createOperatorTokens(chars, i);
					i += increment;
				}

				if (chars[i] == '\"' && !stringLitUnderConstruction 
					|| chars[i] == '\'' && !stringLitUnderConstruction) {
					stringLitUnderConstruction = true;
				}
				else if ((chars[i] == '\"' && stringLitUnderConstruction)
					|| (chars[i] == '\'' && stringLitUnderConstruction)) {

					stringLitUnderConstruction = false;
					Token token = new Token(TokenKind.stringToken);
					token.stringLit = stringLit;
					curLineTokens.add(token);
				}
			}
			
			if (justNumbers) {
				if(containsDot) {
					Token token = new Token(TokenKind.floatToken);
					token.floatLit = Double.valueOf(word);
					curLineTokens.add(token);
				}
				else {
					Token token = new Token(TokenKind.integerToken);
					token.integerLit = Integer.valueOf(word);
					curLineTokens.add(token);
				}
			}

		}

		// Create name tokens
		createNameTokens(s);
	}

	private int createOperatorTokens(char[] chars, int i) {
		char c = chars[i];
		int increment = 0;

		switch (c) {
			case '*':
				curLineTokens.add(new Token(TokenKind.astToken));
				break;

			case '>':
				if (chars[i+1] == '=') {
					increment++;
					curLineTokens.add(new Token(TokenKind.greaterEqualToken));
				}
				else {
					curLineTokens.add(new Token(TokenKind.greaterToken));
				}
				break;

			case '<':
				if (chars[i+1] == '=') {
					increment++;
					curLineTokens.add(new Token(TokenKind.lessEqualToken));
				}
				else {
					curLineTokens.add(new Token(TokenKind.lessToken));
				}
				break;

			case '-':
				curLineTokens.add(new Token(TokenKind.minusToken));
				break;

			case '+':
				curLineTokens.add(new Token(TokenKind.plusToken));
				break;

			case '!':
				if (chars[i+1] == '=') {
					increment++;
					curLineTokens.add(new Token(TokenKind.notEqualToken));
				}
				break;

			case '/':
				if (chars[i+1] == '/') {
					curLineTokens.add(new Token(TokenKind.doubleSlashToken));
				}
				else {
					curLineTokens.add(new Token(TokenKind.slashToken));
				}
				break;

			case ':':
				curLineTokens.add(new Token(TokenKind.colonToken));
				break;

			case ',':
				curLineTokens.add(new Token(TokenKind.commaToken));
				break;

			case '=':
				if (chars[i+1] == '=') {
					curLineTokens.add(new Token(TokenKind.doubleEqualToken));
					increment++;
				}
				if (chars[i-1] == '!') {
					curLineTokens.add(new Token(TokenKind.notEqualToken));
				}
				if (chars[i+1] == '>') {
					increment++;
					curLineTokens.add(new Token(TokenKind.greaterEqualToken));
				}
				if (chars[i+1] == '<') {
					increment++;
					curLineTokens.add(new Token(TokenKind.lessEqualToken));
				}
				else {
					curLineTokens.add(new Token(TokenKind.equalToken));
				}
				break;

			case '{':
				curLineTokens.add(new Token(TokenKind.leftBraceToken));
				break;

			case '[':
				curLineTokens.add(new Token(TokenKind.leftBracketToken));
				break;

			case '(':
				curLineTokens.add(new Token(TokenKind.leftParToken));
				break;

			case '}':
				curLineTokens.add(new Token(TokenKind.rightBraceToken));
				break;

			case ']':
				curLineTokens.add(new Token(TokenKind.rightBracketToken));
				break;

			case ')':
				curLineTokens.add(new Token(TokenKind.rightParToken));
				break;

			case ';':
				curLineTokens.add(new Token(TokenKind.semicolonToken));
				break;
			default:
				break;
		}
		return increment;
	}

	private void createKeywordTokens(String s) {

		switch (s) {
			case "and":
				curLineTokens.add(new Token(TokenKind.andToken));
				break;

			case "as":
				curLineTokens.add(new Token(TokenKind.asToken));
				break;

			case "assert":
				curLineTokens.add(new Token(TokenKind.assertToken));
				break;

			case "break":
				curLineTokens.add(new Token(TokenKind.breakToken));
				break;

			case "class":
				curLineTokens.add(new Token(TokenKind.classToken));
				break;

			case "continue":
				curLineTokens.add(new Token(TokenKind.continueToken));
				break;

			case "def":
				curLineTokens.add(new Token(TokenKind.defToken));
				break;

			case "del":
				curLineTokens.add(new Token(TokenKind.delToken));
				break;

			case "elif":
				curLineTokens.add(new Token(TokenKind.elifToken));
				break;
				
			case "else":
				curLineTokens.add(new Token(TokenKind.elseToken));
				break;

			case "except":
				curLineTokens.add(new Token(TokenKind.exceptToken));
				break;

			case "False":
				curLineTokens.add(new Token(TokenKind.falseToken));
				break;

			case "finally":
				curLineTokens.add(new Token(TokenKind.finallyToken));
				break;

			case "for":
				curLineTokens.add(new Token(TokenKind.forToken));
				break;

			case "from":
				curLineTokens.add(new Token(TokenKind.fromToken));
				break;

			case "global":
				curLineTokens.add(new Token(TokenKind.globalToken));
				break;

			case "if":
				curLineTokens.add(new Token(TokenKind.ifToken));
				break;

			case "import":
				curLineTokens.add(new Token(TokenKind.importToken));
				break;

			case "in":
				curLineTokens.add(new Token(TokenKind.inToken));
				break;

			case "is":
				curLineTokens.add(new Token(TokenKind.isToken));
				break;

			case "lambda":
				curLineTokens.add(new Token(TokenKind.lambdaToken));
				break;

			case "None":
				curLineTokens.add(new Token(TokenKind.noneToken));
				break;

			case "nonlocal":
				curLineTokens.add(new Token(TokenKind.nonlocalToken));
				break;

			case "not":
				curLineTokens.add(new Token(TokenKind.notToken));
				break;

			case "or":
				curLineTokens.add(new Token(TokenKind.orToken));
				break;

			case "pass":
				curLineTokens.add(new Token(TokenKind.passToken));
				break;

			case "raise":
				curLineTokens.add(new Token(TokenKind.raiseToken));
				break;

			case "return":
				curLineTokens.add(new Token(TokenKind.returnToken));
				break;

			case "true":
				curLineTokens.add(new Token(TokenKind.trueToken));
				break;

			case "try":
				curLineTokens.add(new Token(TokenKind.tryToken));
				break;
				
			case "while":
				curLineTokens.add(new Token(TokenKind.whileToken));
				break;

			case "with":
				curLineTokens.add(new Token(TokenKind.withToken));
				break;
				
			case "yield":
				curLineTokens.add(new Token(TokenKind.yieldToken));
				break;

			default:
				break;
		}
	}

	private void createNameTokens(String s) {


	}

	private boolean isStringInt(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private boolean isStringFloat(String s) {
		try {
			Float.parseFloat(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
				

	private void indentHandling(String s) {
		String currentString = s;
		System.out.println("|" + s + "|");
		if (s == null) {
			while (indents.peek() > 0) {
				indents.pop();
				curLineTokens.add(new Token(dedentToken, curLineNum()));
			}
			curLineTokens.add(new Token(eofToken, curLineNum()));
			return;
		}

		if (s.isBlank()) {
			System.out.println("-- blank");
			return;
		}
		else if (s.contains("#")) {
			String s2 = s.substring(0, s.indexOf("#"));

			if(s2.isBlank()) {
				System.out.println("-- comment");
				return;
			}
			else {
				currentString = s2;
				System.out.println("-- code before comment");
			}
		}

		int n = findIndent(currentString);

		if (n > indents.peek()) {
			indents.push(n);
			curLineTokens.add(new Token(indentToken, curLineNum()));
		}
		else if (n < indents.peek()) {
			indents.pop();
			curLineTokens.add(new Token(dedentToken, curLineNum()));
		}

		if (n != indents.peek()) {
			scannerError("Indent failiure");
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
		// -- Must be changed in part 1:
		int n = 0;
		int m = 0;
		String newString = "";

		for (int i = 0; i < s.length(); i++) {
			newString = "";
			m = 0;
			if (s.charAt(i) == ' ') {
				n++;
			}
			else if (s.charAt(i) == '\t') {
				m = 4 - (n % 4);
				n += m;

				String spaces = "";
				for (int j = 0; j < m; j++) {
					spaces += " ";
				}

				char[] chars = s.toCharArray();

				for (int j = 0; j < chars.length; j++) {
					if (j != chars[i]) {
						newString = newString + chars[j];
					}
				}

				newString = spaces + newString;
			}
			else {
				newString = s;
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
