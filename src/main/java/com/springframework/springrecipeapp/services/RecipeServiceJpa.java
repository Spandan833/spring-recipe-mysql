package com.springframework.springrecipeapp.services;

import com.springframework.springrecipeapp.commands.RecipeCommand;
import com.springframework.springrecipeapp.coverters.RecipeCommandToRecipe;
import com.springframework.springrecipeapp.coverters.RecipeToRecipeCommand;
import com.springframework.springrecipeapp.domain.Recipe;
import com.springframework.springrecipeapp.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceJpa implements RecipeService {

    private final com.springframework.springrecipeapp.repsositories.RecipeRepository recipeRespository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceJpa(com.springframework.springrecipeapp.repsositories.RecipeRepository recipeRespository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRespository = recipeRespository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> findAll() {
        log.debug("I m in the service");
        Set<Recipe> recipes = new HashSet<>();
        recipeRespository.findAll().forEach(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(Long id) {
       Optional<Recipe> recipeOptional = recipeRespository.findById(id);
       if(!recipeOptional.isPresent()){
           throw new NotFoundException("Recipe not found for id value: " + id);
       }
       return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(recipeCommand);

        Recipe savedRecipe = recipeRespository.save(detachedRecipe);
        log.debug("Saved Recipe Id: "+ savedRecipe.getId());
        
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public void delete(Recipe recipe) {
        recipeRespository.delete(recipe);
    }

    @Override
    public void deleteById(Long id) {
        recipeRespository.deleteById(id);
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(long id) {
        Recipe recipe = this.findById(id);
        RecipeCommand command = this.recipeToRecipeCommand.convert(recipe);
        return command;
    }
}
