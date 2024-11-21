package com.techelevator.dao;


import com.techelevator.exception.DaoException;
import com.techelevator.model.Ingredient;
import com.techelevator.model.Recipe;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class JdbcRecipeDao implements RecipeDao {


    // Initialize the jdbcTemplate
    private final JdbcTemplate jdbcTemplate;

    public JdbcRecipeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Methods go here

    // GET
    @Override
    public List<Integer> getAllMyRecipeIds(int userId) {
        List<Integer> recipeIdList = new ArrayList<>();

        String sql = "SELECT recipe_id\n" +
                "FROM recipes_users\n" +
                "WHERE user_id = ?;";

        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
            while (rowSet.next()) {
                recipeIdList.add(rowSet.getInt("recipe_id"));
            }
        } catch (DaoException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }

        return recipeIdList;
    }

    public Recipe getRecipe(int recipeId) {
        String sql = "SELECT * FROM recipes_library WHERE recipe_id = ?";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, recipeId);

        return mapRecipeFromRowSet(rowSet);
    }

    /* Handles adding recipe to database */

    // POST for creating new recipe
    @Override
    public Recipe addRecipe(Recipe recipe) {

        String sql = "INSERT INTO recipes_library(recipe_id, title, ingredient_list,\n" +
            "\t\t\t\t\t\t\tinstructions, summary, duration,\n" +
            "\t\t\t\t\t\t\tservings, diet_categories, occasions,\n" +
            "\t\t\t\t\t\t   recipe_source_url, image_path)\n" +
            "VALUES(?,?,?,?,?,?,?,?,?,?,?);";

        String ingredientListString = "";
        for (int i = 0; i < recipe.getIngredientList().size(); i++) {
            if (i == recipe.getIngredientList().size() - 1) { // check to see if it's the last entry
                ingredientListString += recipe.getIngredientList().get(i).getIngredientId() + ":" + recipe.getIngredientList().get(i).getIngredientName();
            } else {
                ingredientListString += recipe.getIngredientList().get(i).getIngredientId() + ":" + recipe.getIngredientList().get(i).getIngredientName() + "#";
            }
        }

        String instructionsString = "";
        for (int i = 0; i < recipe.getInstructions().size(); i++) {
            if (i == recipe.getInstructions().size() - 1) { // check to see if it's the last entry
                instructionsString += recipe.getInstructions().get(i);
            } else {
                instructionsString += recipe.getInstructions().get(i) + "#";
            }
        }

        String dietCategoriesString = "";
        for (int i = 0; i < recipe.getDietCategories().size(); i++) {
            if (i == recipe.getDietCategories().size() - 1) { // check to see if it's the last entry
                dietCategoriesString += recipe.getDietCategories().get(i);
            } else {
                dietCategoriesString += recipe.getDietCategories().get(i) + "#";
            }
        }

        String occasionsString = "";
        for (int i = 0; i < recipe.getOccasions().size(); i++) {
            if (i == recipe.getOccasions().size() - 1) { // check to see if it's the last entry
                occasionsString += recipe.getOccasions().get(i);
            } else {
                occasionsString += recipe.getOccasions().get(i) + "#";
            }
        }

        try {
            jdbcTemplate.update(sql, recipe.getRecipeId(), recipe.getTitle(),
                    ingredientListString, instructionsString, recipe.getSummary(), recipe.getDuration(), recipe.getServings(),
                    dietCategoriesString, occasionsString, recipe.getSource(), recipe.getImage());


        } catch(DaoException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        } catch(NullPointerException ex) {

        }

        return recipe;
    }

    public void setRecipeToUser(int userId, int recipeId) {
        String sql = "INSERT INTO recipes_users VALUES(?, ?);";

        try {
            jdbcTemplate.update(sql, userId, recipeId);
        } catch (DaoException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }
    }

    public boolean checkDatabaseForRecipe(int recipeId) {
        String sql = "SELECT recipe_id FROM recipes_library WHERE recipe_id = ?;";
        int databaseRecipeId = 0;

        try {
            jdbcTemplate.queryForObject(sql, int.class, recipeId);
        } catch (DaoException ex) {
            return false;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
        return true;
    }

    // PUT
    @Override
    public void updateRecipeInLibrary(Recipe recipe) {

        String sql = "UPDATE recipes_library\n" +
                "SET title = ?, ingredient_list = ?,\n" +
                "\tinstructions = ?, summary = ?, duration = ?, servings = ?,\n" +
                "\tdiet_category = ?, occasions = ?,\n" +
                "\trecipe_source_url = ?, image_path = ?\n" +
                "WHERE recipe_id = ?;";

        try {
            int rowsAffected = jdbcTemplate.update(sql, int.class, recipe.getTitle(), recipe.getIngredientList(),
                    recipe.getInstructions(), recipe.getSummary(), recipe.getDuration(), recipe.getServings(),
                    recipe.getDietCategories(), recipe.getOccasions(), recipe.getSource(),
                    recipe.getImage(), recipe.getRecipeId());

            if(rowsAffected <= 0) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Recipe not found");
            }

        } catch (DaoException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Recipe not found, error: " + ex.getMessage());
        }
    }

    @Override
    public void deleteRecipeFromLibrary(int recipeId) {
        String sql = "DELETE FROM recipes_users\n" +
                "WHERE recipe_id = ?;";

        jdbcTemplate.update(sql, recipeId);
    }


    // NOTE: Need to get the ingredientList as an array to make into a list, same with dietaryRestrictions
    private Recipe mapRecipeFromRowSet(SqlRowSet rowSet) {
        Recipe recipe = new Recipe();

        if (rowSet.next()) {
            int recipeId = rowSet.getInt("recipe_id");
            String title = rowSet.getString("title");
            String ingredientsString = rowSet.getString("ingredient_list");
            String instructionsString = rowSet.getString("instructions");
            String summary = rowSet.getString("summary");
            int duration = rowSet.getInt("duration");
            int servings = rowSet.getInt("servings");
            String dietCategoriesString = rowSet.getString("diet_categories");
            String occasionsString = rowSet.getString("occasions");
            String source = rowSet.getString("recipe_source_url");
            String image = rowSet.getString("image_path");

            recipe.setRecipeId(recipeId);
            recipe.setTitle(title);
            recipe.setSummary(summary);
            recipe.setDuration(duration);
            recipe.setServings(servings);
            recipe.setSource(source);
            recipe.setImage(image);

            if (!ingredientsString.isEmpty()) {
                String[] ingredientsArray = ingredientsString.split("#");
                List<Ingredient> ingredientsList = new ArrayList<>();
                for (String ingredient : ingredientsArray) {
                    String[] ingredientComponents = ingredient.split(":");
                    ingredientsList.add(new Ingredient(Integer.parseInt(ingredientComponents[0]), ingredientComponents[1]));
                }
                recipe.setIngredientList(ingredientsList);
            }

            if (!instructionsString.isEmpty()) {
                String[] instructionsArray = instructionsString.split("#");
                List<String> instructionsList = new ArrayList<>(Arrays.asList(instructionsArray));
                recipe.setInstructions(instructionsList);
            }

            if (!dietCategoriesString.isEmpty()) {
                String[] dietCategoriesArray = dietCategoriesString.split("#");
                List<String> dietCategoriesList = new ArrayList<>(Arrays.asList(dietCategoriesArray));
                recipe.setDietCategories(dietCategoriesList);
            }

            if (!occasionsString.isEmpty()) {
                String[] occasionsArray = occasionsString.split("#");
                List<String> occasionsList = new ArrayList<>(Arrays.asList(occasionsArray));
                recipe.setOccasions(occasionsList);
            }
        }

        return recipe;
    }
}
