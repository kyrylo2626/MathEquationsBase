package com.mainApp;

import java.util.List;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import com.eqLogic.Equation;
import com.eqLogic.Root;
import com.model.EqBase;
import com.model.Models;

public class AppMenu extends PrintUTF8 {
	Scanner in;
	Models data;

	public AppMenu() throws UnsupportedEncodingException {
		super();
		
		in = new Scanner(System.in);
		data = new Models();
		
		printStr.println("Вас вітає система обліку математичних рівнянь.");
		
		boolean exitLoop = false;
		
		while (!exitLoop) {
			
			printStr.println("\nОберіть дію:\n"
							+ "1. Ввести рівняння;\n"
							+ "2. Ввести корені рівняння;\n"
							+ "3. Пошук рівняння за коренями;\n"
							+ "4. Вихід.\n");
			
			int userInput = in.nextInt();

			switch (userInput) {
				case 1: setEquation(); break;
				case 2: setEqRoots(); break;
				case 3: searchEq(); break;
				case 4: exitLoop=true; data.close(); break;
			}
		}
		
		in.close();
	}
	
	// Додання рівняння в БД
	public void setEquation() throws UnsupportedEncodingException {
		
		printStr.println("\nДля введення математичних операцій використовуйте символи +, -, *, /.\n"
						+ "Для введення десяткових дробів використовуйте крапку.\n"
						+ "Для позначення рівня вкладеності виразів використовуйте круглі дужки ().\n"
						+ "Для позначення невідомої величини в рівнянні використовуйте латинську маленьку літеру \"x\".\n\n"
						+ "Введіть досліджуване рівняння:\n");
		
		in.nextLine();
		String userInput = in.nextLine();
		userInput = userInput.replace(" ", "");
		
		Equation equation = new Equation();
		// Перевірка на корректність вводу
		if (equation.setSentenceEq(userInput)) {
			// Спроба збереження в БД
			if (data.setEqSentence(userInput)) {
				printStr.println("\nЗбережено.");
			} else printStr.println("\nВиникла помилка. Спробуйте ще раз.");
		}
	}
	
	// Додання кореня до рівняння в БД
	public void setEqRoots() throws UnsupportedEncodingException {
		printStr.println("\nВведіть рівняння, якому буде збережено корінь:");
		in.nextLine();
		String userInput = in.nextLine();
		String equationSentence = userInput.replace(" ", "");
		
		// Перевірка наявності рівняння в БД
		String eqId = data.getEqId(userInput);
		if (eqId != "empty") {
			printStr.println("\nВведіть корінь рівняння:");
			String equationRoot = in.nextLine();
			
			// Перевірка чи є введене значення коренем рівняння
			Root roots = new Root();
			boolean check = roots.checkRoot(equationSentence, equationRoot);
			
			if (check) {
				// Спроба збереження кореня в БД
				if (data.setEqRoot(eqId, Double.parseDouble(equationRoot))) {
					printStr.println("\nЗбережено.");
				} else printStr.println("\nВиникла помилка. Спробуйте ще раз.");
				
			} else {
				printStr.println("Введений корінь не є корнем рівняння. Спробуйте ще раз.");
			}
			
		} else {
			printStr.println("Введене рівняння відсутнє в базі. Перевірте правильність вводу або додайте рівняння в базу з меню.");
		}
		
	}
	
	// Пошук рівнянь в БД за коренем
	public void searchEq() throws UnsupportedEncodingException {
		printStr.println("\nВведіть корінь шуканого рівняння:");
		
		in.nextLine();
		String userInput = in.nextLine();
		try {
			Double.parseDouble(userInput);
			// Перевірка на наявність відповідних рівняннь в БД
			List<EqBase> equationSentence = data.getEqSentence(Double.parseDouble(userInput));
			
			if (equationSentence.size() != 0) {
				printStr.println("\nЗнайдені рівняння за вашим запитом:");
				for (EqBase equation: equationSentence) {
		        	System.out.println("- " + equation.getEqSentence() + ";");
		        }
			} else printStr.println("\nЗа вашим запитом нічого не знайдено.");
			
		} catch (Exception e) {
			printStr.println("\nВведене значення не є коректним. Спробуйте ще раз.");
		}
	}
	
}
