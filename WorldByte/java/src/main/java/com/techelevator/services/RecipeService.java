package com.techelevator.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.dao.JdbcRecipeDao;
import com.techelevator.model.Ingredient;
import com.techelevator.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService { // Recipe Service; in progress switching to Spoonacular

    @Value("${spoonacular.api.url}")
    private String apiURL;
    @Value("${spoonacular.api.key}")
    private String key;
    @Value("${spoonacular.api.general-endpoint}")
    private String generalEndpoint;
    @Value("${spoonacular.api.recipe-info-endpoint}")
    private String recipeInfoEndpoint;
    @Value("${spoonacular.api.ingredient-endpoint}")
    private String ingredientEndpoint;
    @Value("${spoonacular.api.necessary-parameters}")
    private String necessaryParameters;
    @Value("${spoonacular.api.random-parameter}")
    private String randomParameter;
    @Value("${spoonacular.api.detailed-parameters}")
    private String detailedParameters;
    @Autowired private JdbcRecipeDao recipeDao;

    public RecipeService() {
    }

    public Recipe getRecipeDetails(int recipeId) {
        String url = this.apiURL + "/" + recipeId + this.recipeInfoEndpoint + this.key;

        // Setting up httpEntity and restTemplate
        HttpEntity<String> httpEntity = new HttpEntity<>("");
        RestTemplate restTemplate = new RestTemplate();

        // Setting up objectMapper and jsonNode
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;

        // Get request:
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                String.class
        );

        Recipe recipe = new Recipe();
        recipe.setRecipeId(recipeId);

        // Mapping the response data to the recipe model and catching potential exceptions
        try {
            // Pulling the data straight from the jsonNode due to how the retrieved data is formatted
            jsonNode = objectMapper.readTree(response.getBody());

            // ID was set above
            recipe.setTitle(jsonNode.path("title").asText());
            recipe.setSummary(jsonNode.path("summary").asText());
            recipe.setDuration(jsonNode.path("readyInMinutes").asInt());
            recipe.setServings(jsonNode.path("servings").asInt());
            recipe.setSource(jsonNode.path("sourceUrl").asText());
            recipe.setImage(jsonNode.path("image").asText());

            List<Ingredient> ingredientList = new ArrayList<>();
            for (int i = 0; i < jsonNode.path("extendedIngredients").size(); i++) {
                Ingredient ingredient = new Ingredient();
                ingredient.setIngredientId(jsonNode.path("extendedIngredients").path(i).path("id").asInt());
                ingredient.setIngredientName(jsonNode.path("extendedIngredients").path(i).path("nameClean").asText());
                ingredientList.add(ingredient);
            }
            recipe.setIngredientList(ingredientList);

            List<String> instructions = new ArrayList<>();
            for (int i = 0; i < jsonNode.path("analyzedInstructions").path(0).path("steps").size(); i++) {
                instructions.add(jsonNode.path("analyzedInstructions").path(0).path("steps").path(i).path("step").asText());
            }
            recipe.setInstructions(instructions);

            List<String> dietCategories = new ArrayList<>();
            for (int i = 0; i < jsonNode.path("diets").size(); i++) {
                dietCategories.add(jsonNode.path("diets").path(i).asText());
            }
            recipe.setDietCategories(dietCategories);

            List<String> occasions = new ArrayList<>();
            for (int i = 0; i < jsonNode.path("occasions").size(); i++) {
                occasions.add(jsonNode.path("occasions").path(i).asText());
            }
            for (int i = 0; i < jsonNode.path("dishTypes").size(); i++) {
                occasions.add(jsonNode.path("dishTypes").path(i).asText());
            }
            recipe.setOccasions(occasions);

        }
         catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return recipe;
    }

    /**
     * This method accepts a general keyword and returns a list of recipe objects that
     * are associated with that keyword. If there is no searchQuery, then it returns a
     * random list
     *
     * @param searchQuery String containing the keyword that the user would like to search for
     * @return List of Recipe objects that align with the provided keyword
     */
    public List<Recipe> getRecipesByKeyword(String searchQuery) {
        String url = this.apiURL + this.generalEndpoint + this.key + this.necessaryParameters + this.detailedParameters;

        // If there is no searchQuery, it will return a random assortment of Recipes
        if (searchQuery == null) {
            url += this.randomParameter;
        } else {
            url += "&query=" + searchQuery;
        }

        // Setting up httpEntity and restTemplate
        HttpEntity<String> httpEntity = new HttpEntity<>("");
        RestTemplate restTemplate = new RestTemplate();

        // Setting up objectMapper and jsonNode
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;

        // Get request:
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                String.class
        );

        // Setting up list that will be hold recipes
        List<Recipe> recipes = new ArrayList<>();

        // Mapping the response data to the recipe model and catching potential exceptions
        try {
            // Setting the jsonNode to the "results" attribute that holds recipes
            jsonNode = objectMapper.readTree(response.getBody());
            JsonNode root = jsonNode.path("results");

            // For loop to add recipes to the list; only accepting the id, title, and image currently
            for (int i=0; i < root.size(); i++) {
                Recipe recipe = new Recipe();
                recipe.setRecipeId(root.path(i).path("id").asInt());
                recipe.setTitle(root.path(i).path("title").asText());
                recipe.setSummary(root.path(i).path("summary").asText());
                recipe.setDuration(root.path(i).path("readyInMinutes").asInt());
                recipe.setServings(root.path(i).path("servings").asInt());
                recipe.setSource(root.path(i).path("sourceUrl").asText());
                recipe.setImage(root.path(i).path("image").asText());

                // need to do ingredientList, instructions, and dietCategories, and occasions

                List<Ingredient> ingredientList = new ArrayList<>();
                for (int j = 0; j < root.path(i).path("analyzedInstructions").path(0).path("steps").size(); j++) {
                    Ingredient ingredient = new Ingredient();
                    for (int k = 0; k < root.path(i).path("analyzedInstructions").path(0).path("steps").path("ingredients").size(); k++) {
                        int ingredientId = root.path(i).path("analyzedInstructions").path(0).path("steps").path("ingredients").path(k).path("id").asInt();
                        String ingredientName = root.path(i).path("analyzedInstructions").path(0).path("steps").path("ingredients").path(k).path("name").asText();

                        if (ingredientId <= 0) {
                            break;
                        }

                        ingredient.setIngredientId(ingredientId);
                        ingredient.setIngredientName(ingredientName);
                        ingredientList.add(ingredient);
                    }
                }
                recipe.setIngredientList(ingredientList);

                List<String> instructions = new ArrayList<>();
                for (int j = 0; j < root.path(i).path("analyzedInstructions").path(0).path("steps").size(); j++) {
                    instructions.add(root.path(i).path("analyzedInstructions").path(0).path("steps").path(j).path("step").asText());
                }
                recipe.setInstructions(instructions);

                List<String> dietCategories = new ArrayList<>();
                for (int j = 0; j < root.path(i).path("diets").size(); j++) {
                    dietCategories.add(root.path(i).path("diets").path(j).asText());
                }
                recipe.setDietCategories(dietCategories);

                List<String> occasions = new ArrayList<>();
                for (int j = 0; j < root.path(i).path("occasions").size(); j++) {
                    occasions.add(root.path(i).path("occasions").path(j).asText());
                }
                for (int j = 0; j < root.path(i).path("dishTypes").size(); j++) {
                    occasions.add(root.path(i).path("dishTypes").path(j).asText());
                }
                recipe.setOccasions(occasions);

                recipes.add(recipe);
            }

        } catch (JsonProcessingException e) {
            System.out.println("Something went wrong.");
        }

        return recipes;
    }

    // Extra cases; not implemented
//    /**
//     * This method accepts a String array of ingredients and applies those as the
//     * search parameter(s) to find recipes that include those ingredients.
//     *
//     * @param ingredients A String array containing food items
//     * @return List of recipe objects that include the ingredients provided
//     */
//    public List<Recipe> getRecipesByIngredients(String[] ingredients) {
//
//        return null;
//    }
}