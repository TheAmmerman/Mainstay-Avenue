package com.techelevator.controller;

import com.techelevator.dao.RecipeDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Recipe;
import com.techelevator.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/recipes")
@CrossOrigin
public class RecipeController {

    @Autowired RecipeDao recipeDao;
    @Autowired UserDao userDao;
    @Autowired RecipeService recipeService;


    public RecipeController(RecipeDao recipeDao, UserDao userDao, RecipeService recipeService) {
        this.recipeDao = recipeDao;
        this.userDao = userDao;
        this.recipeService = recipeService;
    }

    //API calls go here
    @GetMapping(path = "{searchQuery}")
    public List<Recipe> searchRecipesByKeyword(@PathVariable String searchQuery) {
        return recipeService.getRecipesByKeyword(searchQuery);
    }

    @GetMapping(path = "/{recipeId}/information")
    public Recipe viewRecipeDetails(@PathVariable int recipeId) {
        return recipeService.getRecipeDetails(recipeId);
    }

    @GetMapping(path = "/library")
    public List<Recipe> getAllMyRecipes(Principal principal) {
        List<Recipe> recipeList = new ArrayList<>();
        int userId = userDao.findIdByUsername(principal.getName());
        List<Integer> recipeIdList = recipeDao.getAllMyRecipeIds(userId);
        for (Integer recipeId : recipeIdList) {
            recipeList.add(recipeDao.getRecipe(recipeId));
        }
        return recipeList;
    }


    // utilizing viewRecipeDetails to map the recipe model
    // then using recipeDao.addRecipe, post the info into our database as a new recipe
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/library")
    public void addRecipe(Principal principal, @RequestBody Recipe recipe) {
        Recipe recipeToAdd = recipeService.getRecipeDetails(recipe.getRecipeId());
        if (!recipeDao.checkDatabaseForRecipe(recipeToAdd.getRecipeId())) {
            recipeDao.addRecipe(recipeToAdd);
        }
        recipeDao.setRecipeToUser(userDao.findIdByUsername(principal.getName()), recipe.getRecipeId());
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/library/{recipeId}")
    public void updateRecipe(@RequestBody Recipe recipe, @PathVariable int recipeId) {
        recipe.setRecipeId(recipeId);
        recipeDao.updateRecipeInLibrary(recipe);
    }


    @DeleteMapping("/library/{recipeId}")
    public void deleteRecipeFromLibrary(@PathVariable int recipeId) {
        recipeDao.deleteRecipeFromLibrary(recipeId);
    }




    // Extra cases; not implemented

    //    @GetMapping(path = "/")
//    public List<Recipe> searchRecipesByIngredients(Principal principal, String[] ingredients) {
//        return recipeService.getRecipesByIngredients(ingredients);
//    }
}
