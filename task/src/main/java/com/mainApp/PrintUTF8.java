package com.mainApp;

import java.io.PrintStream;

// Вивід кириллиці в консоль
public class PrintUTF8 {
	protected PrintStream printStr;

	public PrintUTF8() throws java.io.UnsupportedEncodingException {
		this.printStr = new PrintStream(System.out, true, "UTF-8");
	}

}
