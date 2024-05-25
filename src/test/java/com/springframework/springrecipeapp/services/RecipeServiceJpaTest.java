package com.springframework.springrecipeapp.services;

import com.springframework.springrecipeapp.commands.RecipeCommand;
import com.springframework.springrecipeapp.coverters.RecipeCommandToRecipe;
import com.springframework.springrecipeapp.coverters.RecipeToRecipeCommand;
import com.springframework.springrecipeapp.domain.Recipe;
import com.springframework.springrecipeapp.exceptions.NotFoundException;
import com.springframework.springrecipeapp.repsositories.RecipeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceJpaTest {

    RecipeServiceJpa recipeService;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        recipeService = new RecipeServiceJpa(recipeRepository,recipeCommandToRecipe,recipeToRecipeCommand);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        Recipe recipe = new Recipe();
        Set<Recipe> recipeData = new HashSet<>();
        recipeData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipeData);

        Set<Recipe> recipes = recipeService.findAll();

        assertEquals(recipes.size(), recipeData.size());
        verify(recipeRepository,times(1)).findAll();
    }
    @Test
    void findById() {
        Recipe recipe = Recipe.builder().id(1L).description("Kadhai Chicken").build();
        Optional<Recipe> optionalRecipe = Optional.of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        Recipe returnedRecipe = recipeService.findById(1L);

        Assertions.assertNotNull(returnedRecipe);
        Assertions.assertEquals(recipe.getId(),returnedRecipe.getId());
        Mockito.verify(recipeRepository,times(1)).findById(eq(1L));
        Mockito.verify(recipeRepository, never()).findAll();
    }

    @Test
    void getRecipeByIdNotFoundException() throws Exception{
        Optional<Recipe> recipeOptional = Optional.empty();

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        //should go boom
        NotFoundException notFoundException = assertThrows(
                NotFoundException.class, () -> recipeService.findById(1L),
                "Expected exception to throw an error. But it didn't"
        );

        // then
        assertTrue(notFoundException.getMessage().contains("Recipe not found"));
    }

    @Test
    void delete() {
    }

    @Test
    void deleteById() {
        recipeService.deleteById(2L);

        Mockito.verify(recipeRepository, times(1)).deleteById(eq(2L));
    }

    @Test
    void dummy(){

    }
}