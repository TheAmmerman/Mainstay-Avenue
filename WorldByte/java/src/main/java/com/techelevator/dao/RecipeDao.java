package com.techelevator.dao;

import com.techelevator.model.Recipe;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;


@Component
public interface RecipeDao {

    List<Integer> getAllMyRecipeIds(int userId);
    Recipe getRecipe(int recipeId);

    Recipe addRecipe(Recipe recipe);

    void setRecipeToUser(int userId, int recipeId);
    boolean checkDatabaseForRecipe(int recipeId);

    void updateRecipeInLibrary(Recipe recipe);

    void deleteRecipeFromLibrary(int recipeId);
}
