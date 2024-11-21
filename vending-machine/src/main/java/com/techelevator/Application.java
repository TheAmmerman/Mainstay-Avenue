package com.techelevator;

import java.io.File;

public class Application {

	public static void main(String[] args) {

		String fileName = "vendingmachine.csv";
		File file = new File(fileName);
		VendingMachine vendingMachine1 = new VendingMachine(file);


		vendingMachine1.displayStarterMenu();




	}
}
