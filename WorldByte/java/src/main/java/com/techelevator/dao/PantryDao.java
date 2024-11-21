package com.techelevator.dao;

import com.techelevator.model.Pantry;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;


@Component
public interface PantryDao {

    List<Pantry> getAllMyIngredients(Principal principal);
}
