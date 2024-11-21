package com.techelevator.dao;

import com.techelevator.model.Ingredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcIngredientDao implements IngredientDao{

    // Initialize the jdbcTemplate

    private final JdbcTemplate jdbcTemplate;

    public JdbcIngredientDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // Methods go here

    @Override
    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredientList = new ArrayList<>();

        String sql = "SELECT ingredient_id, ingredient_name\n" +
                "FROM ingredients;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while(rowSet.next()) {
            ingredientList.add(mapIngredientFromRowSet(rowSet));
        }
        return ingredientList;
    }


    @Override
    public Ingredient createIngredient(Ingredient ingredient) {

        String sql = "INSERT INTO ingredients(ingredient_name)\n" +
                "VALUES(?)\n" +
                "RETURNING ingredient_id;";

        try {
            int ingredientId = jdbcTemplate.queryForObject(sql, int.class, ingredient.getIngredientName());
            ingredient.setIngredientId(ingredientId);
        } catch (Exception ex) {
            System.out.println("Something went wrong!" + ex.getMessage());
        }

        return ingredient;
    }

    private Ingredient mapIngredientFromRowSet(SqlRowSet rowSet) {
        Ingredient ingredient = new Ingredient();

        int ingredientId = rowSet.getInt("ingredient_id");
        String name = rowSet.getString("ingredient_name");

        ingredient.setIngredientId(ingredientId);
        ingredient.setIngredientName(name);

        return ingredient;
    }
}
