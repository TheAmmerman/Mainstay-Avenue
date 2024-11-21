package com.techelevator;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class VendingMachine {

    // Instance Variables
    private double currentMoney = 0;
    private TreeMap<String, VendingItem> mapMenu;
    private final static int OPTION_ONE = 1;
    private final static int OPTION_TWO = 2;
    private final static int OPTION_THREE = 3;
    private final static Scanner SCANNER = new Scanner(System.in);


    // Constructors
    public VendingMachine(File menu){
        constructMenu(menu);

    }


    // Setters

    public void setCurrentMoney(double currentMoney){
        this.currentMoney += currentMoney;

    }

    // Getters
    public double getCurrentMoney(){
        return Math.round(currentMoney * 100.0) / 100.0;
    }


    // Use Case Methods

    public void displayStarterMenu(){


        System.out.println(" Please choose an option: ");
        System.out.println(" (1) Display Vending Machine Items \n (2) Purchase \n (3) Exit ");

        // get users input
        try {
            int userInput = SCANNER.nextInt();
            SCANNER.nextLine();

            if (userInput == OPTION_ONE) {
                displayInventory();
                displayStarterMenu();
            } else if (userInput == OPTION_TWO) {
                displayPurchaseMenu();
            } else if (userInput == OPTION_THREE) {
                System.out.println("<<<< Thanks, Goodbye! >>>>");
                System.exit(0);
            } else {
                System.out.println("Invalid Number...");
                displayStarterMenu();
            }
        } catch (Exception ex) {
            System.out.println(" Something went wrong");
            SCANNER.nextLine();
            displayStarterMenu();
        }
    }

    public void displayInventory(){
        for (Map.Entry<String, VendingItem> entry : mapMenu.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public void displayPurchaseMenu(){
        //Learned from ChatGPT - formats the double
        //adds ',' to separate values >= 1000
        //'0.00' formats to two decimal places as well as replacing the 1s place with a '0' if there is no integer
        //'#' indicates optional digits. '#' will not print anything if the value is !>= 10

        //Turned value into String to format
        //formatted the value, printed newly formatted value as a String
        DecimalFormat decimalFormat2Places = new DecimalFormat("#,##0.00");
        String formattedMoney = decimalFormat2Places.format(getCurrentMoney());

        System.out.println("Current Money: $" + formattedMoney);

        System.out.println(" Please choose an option: ");
        System.out.println(" (1) Feed Money \n (2) Select Product \n (3) Finish Transaction ");

        // get users input
        try {

            int userInput = SCANNER.nextInt();
            SCANNER.nextLine();

            if (userInput == OPTION_ONE) {
                System.out.println("Please enter a whole dollar amount $: ");
                int depositAmount = SCANNER.nextInt();
                SCANNER.nextLine();

                setCurrentMoney(depositAmount);
                //added new TransactionLogger object and called method to create a log entry for depositing money
                TransactionLogger logDeposit = new TransactionLogger("FEED MONEY", depositAmount, getCurrentMoney());
                logDeposit.createLog();

                displayPurchaseMenu();
            } else if (userInput == OPTION_TWO) {
                purchaseItems();
            } else if (userInput == OPTION_THREE) {
                finishTransaction();
            } else {
                System.out.println("Invalid Number...");
                displayPurchaseMenu();
            }
        } catch (Exception ex) {
            System.out.println(" Something went wrong");
            SCANNER.nextLine();
            displayPurchaseMenu();
        }

    }

    private void constructMenu(File menu){

        this.mapMenu = new TreeMap<>();
        try(Scanner fileReader = new Scanner(menu)) {
            while(fileReader.hasNextLine()){
                String line = fileReader.nextLine();
                String[] arrayLine = line.split("\\|");

                VendingItem itemDescription = new VendingItem();
                itemDescription.setItemName(arrayLine[1]);
                itemDescription.setItemPrice(Double.parseDouble(arrayLine[2]));
                itemDescription.setItemType(arrayLine[3]);
                itemDescription.setItemSound(itemDescription.getItemType());

                mapMenu.put(arrayLine[0], itemDescription);

            }
        } catch (Exception ex){
            System.out.println("Something went wrong: " + ex.getMessage());
        }

    }

    public void purchaseItems() throws InterruptedException {

        displayInventory();
        System.out.println("");
        System.out.println("Select item to purchase (Ex: A3, B4 etc): ");
        String userInput = SCANNER.nextLine();

        boolean itemFound = false;


        for(String key : mapMenu.keySet()){
            if(key.equals(userInput.toUpperCase())){
                itemFound = true;
                VendingItem item = mapMenu.get(key);
                System.out.println("item costs: $" + item.getItemPrice());
                if(item.getItemAmount() > 0) {
                    if(getCurrentMoney() >= item.getItemPrice()){
                        item.setItemAmount(item.getItemAmount()-1);
                        setCurrentMoney(Math.round(-item.getItemPrice() * 100.0) / 100.0);
                        System.out.println("Item purchased: -$" +item.getItemPrice());
                        System.out.println(item.getItemSound());

                        //added new TransactionLogger object and called method to create a log entry for purchasing
                        TransactionLogger logPurchase = new TransactionLogger(item.getItemName() + " " +
                                key, item.getItemPrice(), getCurrentMoney());
                        logPurchase.createLog();
                        displayPurchaseMenu();
                    } else {
                        System.out.println("You don't have enough money!");
                        displayPurchaseMenu();
                    }
                } else {
                    System.out.println("There is no item in that slot!");
                    displayPurchaseMenu();
                }
            }
        }
        if(!itemFound) {

            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ITEM NOT FOUND >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            Thread.sleep(2000);

            purchaseItems();
        }
    }

    public void finishTransaction(){
        final double QUARTER = 0.25;
        final double DIME = 0.10;
        final double NICKEL = 0.05;
        int quartersAmount = 0;
        int dimesAmount = 0;
        int nicklesAmount = 0;
        double totalMoneyDispensed = getCurrentMoney();

        //If current money is < 25 then check for dimesAmount and so on
        while(currentMoney >= QUARTER) {
            currentMoney -= QUARTER;
            quartersAmount++;
        }
        while(currentMoney >= DIME) {
            currentMoney -= DIME;
            dimesAmount++;
        }
        while(currentMoney >= NICKEL) {
            currentMoney -= NICKEL;
            nicklesAmount++;
        }
        int totalCoins = quartersAmount + dimesAmount + nicklesAmount;

        //print out number of each coin, total amount of coins, and their total dollar value
        System.out.println("The Machine Gave You...\n Quarters: " + quartersAmount + "\nDimes: " + dimesAmount + "\nNickels: " + nicklesAmount);
        System.out.println("Total coins: " + totalCoins + "\nTotal dollar value: $" + totalMoneyDispensed);

        //creates new TransactionLogger Object and calls createLog to make a new log entry for dispensing change
        TransactionLogger logDispenseChange = new TransactionLogger("GIVE CHANGE", totalMoneyDispensed, getCurrentMoney());
        logDispenseChange.createLog();

        displayStarterMenu();
    }

    /*
        To do
        Create log

        Optional
        create testing
        Sales report
    */
}
