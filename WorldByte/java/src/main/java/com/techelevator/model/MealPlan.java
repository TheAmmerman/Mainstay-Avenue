package com.techelevator.model;

import java.util.List;

public class MealPlan {

    //Instance Variables
    private int mealPlanId;
    private String title;
    private List<Integer> recipeList;
    private String description;
    private int duration;
    private  List<String> dietCategories;
    private String dietaryRestrictions;



    //Getters and Setters
    public int getMealPlanId() {
        return mealPlanId;
    }

    public void setMealPlanId(int mealPlanId) {
        this.mealPlanId = mealPlanId;
    }

    public List<Integer> getRecipeId() {
        return recipeList;
    }

    public List<Integer> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(List<Integer> recipeList) {
        this.recipeList = recipeList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<String> getDietCategories() {
        return dietCategories;
    }

    public void setDietCategories(List<String> dietCategories) {
        this.dietCategories = dietCategories;
    }

    public String getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setDietaryRestrictions(String dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }
}
