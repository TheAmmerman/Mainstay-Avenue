package com.techelevator.model;

import java.util.List;

public class Recipe {

    // Instance Variables - need to add data safeguard annotations
    private int recipeId;
    private String title;
    private List<Ingredient> ingredientList;
    private List<String> instructions;
    private String summary;
    private int duration;
    private int servings;
    private List<String> dietCategories;
    private List<String> occasions; // comprised of both "occasions" and "dishTypes" from the API
    private String source;
    private String image;

    // Constructor
    public Recipe() {
    }

    // Getters and Setters

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public List<String> getDietCategories() {
        return dietCategories;
    }

    public void setDietCategories(List<String> dietCategories) {
        this.dietCategories = dietCategories;
    }

    public List<String> getOccasions() {
        return occasions;
    }

    public void setOccasions(List<String> occasions) {
        this.occasions = occasions;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
