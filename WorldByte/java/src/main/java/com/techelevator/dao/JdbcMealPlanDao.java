package com.techelevator.dao;


import com.techelevator.exception.DaoException;
import com.techelevator.model.MealPlan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcMealPlanDao implements MealPlanDao{


    // Initialize the jdbcTemplate
    private final JdbcTemplate jdbcTemplate;

    public JdbcMealPlanDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Methods go here

    @Override
    public List<MealPlan> getAllMyMealPlans(int userId) {
        List <MealPlan> mealPlanList = new ArrayList<>();

        String sql = "SELECT *\n" +
                "FROM meal_plan\n" +
                "WHERE user_id = ?;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);

        while (rowSet.next()) {
            mealPlanList.add(mapMealPlanFromRowSet(rowSet));
        }

        return mealPlanList;
    }

    // retrieves info for a selected meal plan on an account.
    // Will need refactoring once we determine the nature of the meal plan (for both front and back)
    @Override
    public MealPlan getMealPlanDetails(int mealPlanId) {

        MealPlan myMealPlan = new MealPlan();

        String sql = "SELECT *\n" +
                "FROM meal_plan\n" +
                "WHERE meal_plan_id = ?;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, mealPlanId);

        if(rowSet.next()) {
            myMealPlan = mapMealPlanFromRowSet(rowSet);
        }
        return myMealPlan;
    }

    // Need MealPlan tables active before this will function
    @Override
    public MealPlan createMealPlan(MealPlan mealPlan) {
        MealPlan newMealPlan = new MealPlan();

        String sql = "INSERT INTO meal_plans(title, recipe_list, description, duration, diet_categories, dietary_restrictions)\n" +
                "VALUES(?,?,?,?,?,?)\n" +
                "RETURNING meal_plan_id;";
        try{
            int mealPlanId = jdbcTemplate.queryForObject(sql, int.class, mealPlan.getTitle(),
                    mealPlan.getRecipeList(), mealPlan.getDescription(), mealPlan.getDuration(),
                    mealPlan.getDietCategories(), mealPlan.getDietaryRestrictions());

            mealPlan.setMealPlanId(mealPlanId);

        } catch (DaoException ex) {
            System.out.println("Something went wrong, error: " + ex.getMessage());
        }

        return mealPlan;
    }

    @Override
    public void updateMealPlan(MealPlan mealPlan) {

        String sql = "UPDATE meal_plan\n" +
                "SET title = ?, recipe_list = ?, description = ?,\n" +
                "\tduration = ?, diet_categories = ?, dietary_restrictions = ?\n" +
                "WHERE meal_plan_id = ?;";

        try {

            int rowsAffected = jdbcTemplate.update(sql, int.class, mealPlan.getTitle(),
                    mealPlan.getRecipeList(), mealPlan.getDescription(), mealPlan.getDuration(),
                    mealPlan.getDietCategories(), mealPlan.getDietaryRestrictions(),
                    mealPlan.getMealPlanId());

            if(rowsAffected <= 0) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Could not find the Meal Plan!");
            }

        } catch(DaoException ex) {
            throw new DaoException("Something went wrong! error: " + ex.getMessage());
        }
    }

    @Override
    public void deleteMealPlan(int mealPlanId) {
        String sql = "DELETE FROM meal_plan\n" +
                "WHERE meal_plan_id = ?;";

        jdbcTemplate.update(sql, mealPlanId);
    }


    private MealPlan mapMealPlanFromRowSet(SqlRowSet rowSet) {

        List<Integer> recipeList = new ArrayList<>();
        List<String> dietCategories = new ArrayList<>();
        MealPlan mealPlan = new MealPlan();

        int mealPlanId = rowSet.getInt("meal_plan_id");
        String title = rowSet.getString("title");
        String recipeListString = rowSet.getString("recipe_list");

        if(recipeListString != null && !recipeListString.isEmpty()) {
            String[] recipeStringArray = recipeListString.split(",");
            for(String id : recipeStringArray) {
                try{
                    recipeList.add(Integer.parseInt(id.trim()));
                } catch(NumberFormatException ex) {
                    System.out.println("Invalid number format" + ex.getMessage());
                }
            }
        }

        String description = rowSet.getString("description");
        int duration = rowSet.getInt("duration");
        String dietCategoriesString = rowSet.getString("diet_categories");

        if(dietCategoriesString != null && !dietCategoriesString.isEmpty()) {
            String[] dietCategoriesStringArray = dietCategoriesString.split(",");
            for (String category : dietCategoriesStringArray) {
                dietCategories.add(category.trim());
            }
        }
        String dietaryRestrictions = rowSet.getString("dietary_restrictions");


        mealPlan.setMealPlanId(mealPlanId);
        mealPlan.setTitle(title);
        mealPlan.setRecipeList(recipeList);
        mealPlan.setDescription(description);
        mealPlan.setDuration(duration);
        mealPlan.setDietCategories(dietCategories);
        mealPlan.setDietaryRestrictions(dietaryRestrictions);

        return mealPlan;
    }
}
