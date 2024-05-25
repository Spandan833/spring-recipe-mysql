package com.springframework.springrecipeapp.services;

import com.springframework.springrecipeapp.commands.RecipeCommand;
import com.springframework.springrecipeapp.coverters.RecipeCommandToRecipe;
import com.springframework.springrecipeapp.coverters.RecipeToRecipeCommand;
import com.springframework.springrecipeapp.domain.Recipe;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class RecipeServiceIT {
    final String NEW_DESCIPTION = "NEW DESCRIPTION";

    @Autowired
    RecipeService recipeService;

    @Autowired
    com.springframework.springrecipeapp.repsositories.RecipeRepository recipeRepository;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @BeforeEach
    void setUp(){
    }

    @Test
    @Transactional
    void saveRecipeCommand() {
        Iterable<Recipe> recipes = recipeService.findAll();
        Recipe recipe = recipes.iterator().next();
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);

        recipeCommand.setDescription(NEW_DESCIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        Assertions.assertNotNull(savedRecipeCommand);
        Assertions.assertNotNull(recipeService.findById(savedRecipeCommand.getId()));
        Assertions.assertEquals(recipe.getId(), savedRecipeCommand.getId());
        Assertions.assertEquals(NEW_DESCIPTION, savedRecipeCommand.getDescription());
    }
}