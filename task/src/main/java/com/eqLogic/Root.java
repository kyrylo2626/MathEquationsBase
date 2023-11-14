package com.eqLogic;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Root {
	
	String sentenceEq, resL, resR;
	
	public boolean checkRoot(String sentenceEq, String root) {
		sentenceEq = sentenceEq.replace(" ", "");
		sentenceEq = sentenceEq.replace("x", root);
		String[] parts = sentenceEq.split("=");
		
		this.sentenceEq = parts[0];
		while (resL == null) replaceRoot("left");
		this.sentenceEq = parts[1];
		while (resR == null) replaceRoot("right");
		
		double result = Math.abs(Double.parseDouble(resL) - Double.parseDouble(resR));
		return result < 1e-9;
	}

	public void replaceRoot(String side) {
		String[] actions;
		String[] numbers;
		
		actions = sentenceEq.split("[\\(\\)]"); // Розділення рівняння по діям
		numbers = sentenceEq.split("[\\+\\-\\*\\/\\(\\)]"); // Розділення рівняння по операндам
		
		if (numbers.length != 1) solveEq(numbers, actions);
		else {
			if (side == "left") this.resL = numbers[0];
			else if (side == "right") this.resR = numbers[0];
		}
	}
	
	public void solveEq(String[] numbers, String[] actions) {
		boolean label = false;
		
		// Створення об'єкту даних математичних операцій за їх пріорітетом виконання
		String[] symbols;
		int index = 1;
		
		Map<Integer, String[]> symbols1 = new HashMap<Integer, String[]>();
		symbols1.put(1, (String[]) new String[]{"[*]", "[/]"});
		symbols1.put(2, (String[]) new String[]{"[+]", "[-]"});
		Map<Integer, String[]> symbols2 = new HashMap<Integer, String[]>();
		symbols2.put(1, (String[]) new String[]{"[*]-", "[/]-"});
		symbols2.put(2, (String[]) new String[]{"[+]-", "[-]-"});
		
		Map<Integer, Map<Integer, String[]>> symbolsList = new HashMap<Integer, Map<Integer, String[]>>();
		symbolsList.put(1, symbols1);
		symbolsList.put(2, symbols2);
		
		symbols = symbolsList.get(index).get(1);
		
		// Перебирання всіх операндів рівняння
		for (int i=0; i<numbers.length; i++) {
			// Якщо між операндами одна математична дія
			if (i+index < numbers.length && !numbers[i].isEmpty() && !numbers[i+index].isEmpty()) {
				// Перебирання всіх дій рівняння
				for (int k=0; k<actions.length; k++) {
					// Перебирання математичних операцій
					for (String symb: symbols) {
						/*  Перевірка наявності математичної операції symb
							між двома поряд розташованими операндами */
						if (Pattern.compile(numbers[i]+symb+numbers[i+index]).matcher(actions[k]).find()) {
							eqProcces(actions[k], numbers[i], numbers[i+index], symb);
							label = true;
							break;
						}
						if (label) break;
					}
					if (label) break;
				}
				if (label) break;
			}
			
			if (i == numbers.length-1) {
				if (symbols == symbolsList.get(index).get(1)) {
					symbols = symbolsList.get(index).get(2);
					i = -1;
				} else if (index == 1) {
					index = 2;
					i = -1;
					symbols = symbolsList.get(index).get(1);
				} else break;
			}
		}
	}

	// Обробка математичної дії
	public void eqProcces(String actions, String num1, String num2, String symbol) {
		String result;
		String symbolStr1, symbolStr2;
		double number1, number2;
		
		number1 = Double.parseDouble(num1);
		number2 = Double.parseDouble(num2);
		
		symbol = symbol.replace("[", "").replace("]", "");
		
		// Перевірка першого операнда на від'ємність
		if (actions.indexOf(num1+symbol+num2) != 0) {
			if (actions.charAt(actions.indexOf(num1+symbol+num2)-1) == '-')	number1 = -number1;
		}
		
		// Перевірка другого операнда на від'ємність
		if (symbol.length() != 1) number2 = -number2;
		
		// Виконання математичної операції
		result = Double.toString(mathOperation(number1, number2, symbol.charAt(0)));
		
		// Оновлення вигладу рівняння
		if (number1<0) symbolStr1 = sentenceEq.substring(0, sentenceEq.indexOf(num1+symbol+num2)-1);
		else symbolStr1 = sentenceEq.substring(0, sentenceEq.indexOf(num1+symbol+num2));
		
		symbolStr2 = sentenceEq.substring(sentenceEq.indexOf(num1+symbol+num2)+((num1+symbol+num2).length()));
		sentenceEq = symbolStr1 + "result" + symbolStr2;
		sentenceEq = sentenceEq.replace("(result)", "result");
		sentenceEq = sentenceEq.replace("result", result);
	}
	
	// Математична операція між двома операндами
	public double mathOperation(double number1, double number2, char symbol) {	
		
		switch (symbol) {
			case '+': return number1+number2;
			case '-': return number1-number2;
			case '*': return number1*number2;
			case '/': return number1/number2;
			default: return 0.0;
		}
	}
	
}


