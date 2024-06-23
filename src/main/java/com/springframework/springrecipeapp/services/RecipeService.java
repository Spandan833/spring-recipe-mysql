package com.springframework.springrecipeapp.services;

import com.springframework.springrecipeapp.commands.RecipeCommand;
import com.springframework.springrecipeapp.domain.Recipe;

import java.util.Set;
public interface RecipeService {
    Set<Recipe> findAll();

    Recipe findById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    Recipe save(Recipe recipe);

    void delete(Recipe recipe);

    void deleteByUserIdAndRecipeId(Long userId, Long recipeId); //(Long Id);

    RecipeCommand findCommandById(long id);

}
