package com.springframework.springrecipeapp.contollers;

import com.springframework.springrecipeapp.commands.IngredientCommand;
import com.springframework.springrecipeapp.commands.UnitOfMeasureCommand;
import com.springframework.springrecipeapp.coverters.IngredientCommandToIngredient;
import com.springframework.springrecipeapp.coverters.UnitOfMeasureCommandToUnitOfMeasure;
import com.springframework.springrecipeapp.domain.Ingredient;
import com.springframework.springrecipeapp.domain.Recipe;
import com.springframework.springrecipeapp.repsositories.UnitOfMeasureRepository;
import com.springframework.springrecipeapp.services.IngredientService;
import com.springframework.springrecipeapp.services.RecipeService;
import com.springframework.springrecipeapp.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientControllerTest {
    @Mock
    IngredientService ingredientService;
    @Mock
    RecipeService recipeService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Mock
    IngredientCommandToIngredient converter;

    IngredientController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        controller = new IngredientController(recipeService,ingredientService,unitOfMeasureService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void IngredientController_GetIngredientList_Success() throws Exception {
        Recipe recipe = Recipe.builder().id(1L).description("").build();
        when(recipeService.findById(anyLong())).thenReturn(recipe);
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"));
    }

    @Test
    void IngredientController_GetSingleIngredient_Success() throws Exception{
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(3L);
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(),anyLong())).thenReturn(ingredientCommand);
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"));
    }

    @Test
    void IngredientController_Update_Successful() throws Exception{
        IngredientCommand command = new IngredientCommand();
        command.setId(1L);

        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.addIngredient(new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()).convert(command));

        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<UnitOfMeasureCommand>());
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(),anyLong())).thenReturn(command);
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/2/ingredient/1/update"))
                .andExpect(model().attributeExists("uomList"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"));
    }
    @Test
    void IngredientController_GetNewIngredient_Successful()  throws Exception{
        Recipe recipe = Recipe.builder().id(1L).build();
        when(recipeService.findById(anyLong())).thenReturn(recipe);
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/"+recipe.getId()+"/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
    }
    @Test
    void IngredientController_UpdateIngredient_Successful() throws Exception{
        Recipe recipe = new Recipe();
        Long recipeId = 1L;
        recipe.setId(recipeId);

        IngredientCommand command = new IngredientCommand();
        Long ingredientId = 2L;
        command.setId(ingredientId);
        command.setRecipeId(recipeId);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        when(converter.convert(any())).thenReturn(ingredient);

        recipe.addIngredient(ingredient);

        when(ingredientService.saveIngredientCommand(any())).thenReturn(command);


        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/recipe/1/ingredient")
                                                        .param("id","")
                                                        .param("description","SomeIngredient")
                                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/"+command.getRecipeId()+"/ingredients/"+command.getId()+"/show"));

    }
}