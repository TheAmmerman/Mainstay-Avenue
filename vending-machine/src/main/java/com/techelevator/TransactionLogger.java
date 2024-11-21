package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionLogger {


    private String action;

    private double transactionAmount;

    private double newBalance;

    public TransactionLogger(String action, double transactionAmount, double newBalance) {
        this.action = action;
        this.transactionAmount = transactionAmount;
        this.newBalance = newBalance;
    }

    public void createLog(){
        File file = new File("Log.txt");
        try(PrintWriter vendingLog = new PrintWriter(new FileOutputStream(file, true))) {

            //Learned from chatGPT
            //This is a class which can format the date/time. y=year, M=month, d=day
            //h=hour, m=minute, s=second, a=AM/PM
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
            String formatDateTime = LocalDateTime.now().format(formatter);

            //String.format learned from chatGPT and it allows you to format your string with
            //the % symbol is a placeholder to signify the start of the format
            //s=String so for example '%s' signifies a formatted string
            //f=float value and '.2' signifies the amount of decimal places
            //so for example '$%.2f' could be translated into '$5.00'
            vendingLog.println(String.format("%s | %s | $%.2f | $%.2f", formatDateTime, action, transactionAmount, newBalance));
            vendingLog.flush();
        } catch (FileNotFoundException ex) {
            System.out.println("The file could not be found: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }
    }
}
