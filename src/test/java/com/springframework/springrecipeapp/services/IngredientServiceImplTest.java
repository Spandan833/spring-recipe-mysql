package com.springframework.springrecipeapp.services;

import com.springframework.springrecipeapp.commands.IngredientCommand;
import com.springframework.springrecipeapp.commands.UnitOfMeasureCommand;
import com.springframework.springrecipeapp.coverters.IngredientToIngredientCommand;
import com.springframework.springrecipeapp.coverters.UnitOfMeasureToUnitOfMeasureCommand;
import com.springframework.springrecipeapp.domain.Ingredient;
import com.springframework.springrecipeapp.domain.Recipe;
import com.springframework.springrecipeapp.domain.UnitOfMeasure;
import com.springframework.springrecipeapp.repsositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

class IngredientServiceImplTest {

    IngredientService ingredientService;
    @Mock
    com.springframework.springrecipeapp.repsositories.RecipeRepository recipeRepository;
    @Mock
    RecipeService recipeService;
    @Mock
    UnitOfMeasureRepository uomRepository;

    IngredientToIngredientCommand converter;

    private final Long ID1 = 1L;
    private final Long ID2 = 2L;
    private final Long ID3 = 3L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientService = new IngredientServiceImpl(recipeRepository,converter, uomRepository);
    }

    @Test
    void findByRecipeIdAndIngredientId() {
        Recipe recipe = new Recipe();
        recipe.setId(ID1);
        recipe.addIngredient(Ingredient.builder().id(1L).build());
        recipe.addIngredient(Ingredient.builder().id(2L).build());
        recipe.addIngredient(Ingredient.builder().id(3L).build());

        Mockito.when(recipeRepository.findById(ID1)).thenReturn(Optional.of(recipe));

        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(ID1,2L);
        Assertions.assertEquals(ingredientCommand.getRecipeId(),ID1);
        Assertions.assertEquals(ingredientCommand.getId(),2L);
    }


    @Test
    void saveIngredientCommand() {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ID2);
        ingredientCommand.setAmount(new BigDecimal(1));
        UnitOfMeasureCommand uomCOmmand = new UnitOfMeasureCommand();
        uomCOmmand.setId(2L);
        ingredientCommand.setUnitOfMeasure(uomCOmmand);
        ingredientCommand.setDescription("Description");

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());
        Recipe savedRecipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setAmount(new BigDecimal(1));
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(2L);
        ingredient.setUnitOfMeasure(uom);
        ingredient.setDescription("Description");
        savedRecipe.addIngredient(ingredient);
        savedRecipe.getIngredients().iterator().next().setId(ID2);

        Mockito.when(recipeRepository.findById(any())).thenReturn(recipeOptional);
        Mockito.when(recipeRepository.save(any())).thenReturn(savedRecipe);

        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        Assertions.assertEquals(ID2,savedIngredientCommand.getId());
    }
}