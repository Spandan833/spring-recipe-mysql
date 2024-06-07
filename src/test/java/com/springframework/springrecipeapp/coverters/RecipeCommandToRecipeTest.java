package com.springframework.springrecipeapp.coverters;

import com.springframework.springrecipeapp.commands.RecipeCommand;
import com.springframework.springrecipeapp.domain.Recipe;
import com.springframework.springrecipeapp.repsositories.UserRepository;
import com.springframework.springrecipeapp.services.UserService;
import com.springframework.springrecipeapp.services.UserServiceImpl;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class RecipeCommandToRecipeTest {
    RecipeCommandToRecipe converter;

    @Autowired
    UserService userService;
    final Long ID = 3L;
    final String DESCRIPTION = "Paneer Tikka";
    final Integer COOK_TIME = 30;
    final Integer SERVINGS = 2;
    final String URL = "www.cookme.com";
    @BeforeEach
    void setUp() {
        converter = new RecipeCommandToRecipe(new NotesCommandToNotes(),
                                    new CategoryCommandToCategory(),
                                    new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()), userService);
    }

    @Test
    void RecipeCommandNull_Convert_RecipeIsNull(){
        RecipeCommand recipeCommand = null;

        Recipe recipe = converter.convert(recipeCommand);

        Assertions.assertNull(recipe);
    }


    @Disabled
    @Test
    void RecipeCommand_Convert_RecipeObjectPropertiesMatch() {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(ID);
        recipeCommand.setDescription(DESCRIPTION);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setUrl(URL);


        Recipe recipe = converter.convert(recipeCommand);

        Assertions.assertNotNull(recipe);
        Assertions.assertEquals(ID, recipe.getId());
        Assertions.assertEquals(DESCRIPTION, recipe.getDescription());
        Assertions.assertEquals(COOK_TIME,recipe.getCookTime());
        Assertions.assertEquals(SERVINGS,recipe.getServings());
        Assertions.assertEquals(URL,recipe.getUrl());
    }
}