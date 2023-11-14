package com.eqLogic;

import java.io.UnsupportedEncodingException;

import com.mainApp.PrintUTF8;

public class Equation extends PrintUTF8 {
	
	public Equation() throws UnsupportedEncodingException {
		super();
	}

	public boolean setSentenceEq(String sentenceEq) {
		String errorStr = "Перевірте правильність вводу та повторіть спробу.\n"; 
		char[] charArray = sentenceEq.toCharArray();
		
		if (!this.symbolsControl(charArray)) {
			printStr.println("\nВведене рівняння містить некоректні символи.\n" + errorStr);
			return false;
		} else if (!this.parenthesisEqual(charArray)) {
			printStr.println("\nВведене рівняння містить помилку в розміщенні дужок.\n" + errorStr);
			return false;
		} else if (!this.operationSequence(charArray)) {
			printStr.println("\nВведене рівняння містить помилку введення двох знаків "
							+ "математичних операцій поспіль.\n" + errorStr);
			return false;
		} else if (sentenceEq.indexOf('=') == -1) {
			printStr.println("\nВведене рівняння не містить знака рівності.\n" + errorStr);
			return false;
		} else return true;
	}
	
	// Перевірка на коректність розміщення дужок
	public boolean parenthesisEqual(char[] charArray) {
		int leftParenthesis=0, rightParenthesis=0;
		
        for (char c : charArray) {
            if (c=='(') leftParenthesis++;
            else if (c==')') rightParenthesis++;
            
            if (leftParenthesis < rightParenthesis) return false;
        }
        
        if (leftParenthesis==rightParenthesis) return true;
        else return false;
	}
	
	// Перевірка на 2 знаки математичних операцій поспіль
	public boolean operationSequence(char[] charArray) {
		String strTarget = "+-*/";
		
		for (int i=0; i<charArray.length; i++) {
            if (strTarget.indexOf(charArray[i]) != -1) {
            	if (strTarget.indexOf(charArray[i + 1]) != -1) {
            		if (charArray[i + 1] != '-') {
                		return false;
                	} else i++;
            	}   
            }
		}
		
		return true;
	}
	
	// Перевірка на наявність сторонніх символів
	public boolean symbolsControl(char[] charArray) {
		String strTarget = "+-*/.()0123456789x= ";
		
        for (char c : charArray) {
        	if (strTarget.indexOf(c) == -1) return false;
        }
        
        return true;
	}
}
