package com.techelevator;

import java.util.HashMap;
import java.util.Map;

public class VendingItem {
   // Instance Variables
    private String itemName;
    private String itemType;
    private String itemSound;
    private double itemPrice;
    private int itemAmount = 5;


    // Getters
    public String getItemName() {
        return itemName;
    }
    public double getItemPrice() {
        return itemPrice;
    }
    public String getItemType() {
        return itemType;
    }

    public String getItemSound(){
        return itemSound;
    }
    public int getItemAmount() {
        return itemAmount;
    }


    // Setters
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public void setItemSound(String itemType) {
        Map<String, String> itemSounds = new HashMap<>();
        itemType = itemType.toLowerCase();
        itemSounds.put("chip", "CRUNCH CRUNCH, YUM!");
        itemSounds.put("candy", "MUNCH MUNCH YUM!");
        itemSounds.put("drink", "GLUG GLUG YUM!");
        itemSounds.put("gum", "CHEW CHEW YUM!");

        itemSound = itemSounds.get(itemType);
    }
    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }


    // Use Case Methods
    @Override
    public String toString(){
        if(itemAmount == 0) {
            return "Item: " + itemName + " | Price: $" +  itemPrice + " | Type: " + itemType + " | Quantity: SOLD OUT";
        }
        return "Item: " + itemName + " | Price: $" +  itemPrice + " | Type: " + itemType + " | Quantity: " + itemAmount;
    }
}
