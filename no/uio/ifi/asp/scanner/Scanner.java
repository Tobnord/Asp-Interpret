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

		// Terminate line:
		curLineTokens.add(new Token(newLineToken, curLineNum()));

		for (Token t : curLineTokens)
			Main.log.noteToken(t);
	}

	private void indentHandling(String s) {
		String currentString = s;
		if (s == null) {
			while (indents.peek() > 0) {
				indents.pop();
				curLineTokens.add(new Token(dedentToken, curLineNum()));
				System.out.println("EOF madafakka");
			}
			return;
		}

		if (s.isBlank()) {
			System.out.println("-- blank");
			return;
		}
		else if (s.contains("#")) {
			String s2 = s.substring(0, s.indexOf("#"));
			// System.out.println(s2); // CLEAN

			if(s2.isBlank()) {
				return;
			}
			else {
				currentString = s2;
				System.out.println("-- code before comment");
			}
		}

		int n = findIndent(currentString);
		System.out.println("-- find indent:" + n); // CLEAN

		if (n > indents.peek()) {
			indents.push(n);
			curLineTokens.add(new Token(indentToken, curLineNum()));
		}
		else if (n < indents.peek()) {
			indents.pop();
			curLineTokens.add(new Token(dedentToken, curLineNum()));
		}

		if (n != indents.peek()) {
			// System.out.println("Indent failiure");
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
		// System.out.println("ExpandLeadingTabs current input = " + s); // CLEAN

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

				// System.out.println(newString); CLEAN
				newString = spaces + newString;
			}
			else {
				newString = s;
			}
		}
		// System.out.println("ExpandLeadingTabs n-value: " + n); CLEAN
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
