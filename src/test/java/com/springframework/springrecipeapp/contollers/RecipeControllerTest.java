package com.springframework.springrecipeapp.contollers;

import com.springframework.springrecipeapp.commands.RecipeCommand;
import com.springframework.springrecipeapp.domain.Recipe;
import com.springframework.springrecipeapp.exceptions.NotFoundException;
import com.springframework.springrecipeapp.services.RecipeService;
import com.springframework.springrecipeapp.services.UserService;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class RecipeControllerTest {

    private static final String DESCRIPTION = "Biryani";
    private static final Long RecipeId = 22L;
    private static final Long UserId = 10L;

    public static final String URL = "https://stackoverflow.com/questions/46644515/spring-boot-thymeleaf-unit-test-model-attribute-does-not-exist";
    RecipeController recipeController;

    @Mock
    UserService userService;
    @Mock
    RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeController = new RecipeController(recipeService,userService);
    }

    @Test
    void getRecipeById() throws Exception{
        Recipe recipe = new Recipe();
        recipe.setId(2L);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        when(recipeService.findById(anyLong())).thenReturn(recipe);
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void RecipeController_GetNewRecipeForm_Success() throws Exception{
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/user/1/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testPostNewRecipeValidationFaile() throws Exception{
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        mockMvc.perform(post("/recipe")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", ""))
                .andExpect(model().hasErrors()) // Expect validation errors
                .andExpect(model().attributeExists("recipe")) // Expect "recipe" attribute in the model
                .andExpect(view().name("recipe/recipeform")); // Expect "recipe/recipeform" view
    }
    @Test
    void RecipeNotFoundTest() throws Exception{
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }
    @Test
    void RecipeController_PostNewRecipeForm_Success() throws  Exception{
        RecipeCommand recipeCommand = new RecipeCommand();
                recipeCommand.setId(RecipeId);
                recipeCommand.setDescription(DESCRIPTION);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id","")
                        .param("description","Some string")
                        .param("directions", "Some string")
                        .param("cookTime","10")
                        .param("url", URL))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:recipe/"+ RecipeId +"/show"));
    }

    @Test
    void RecipeController_UpdateRecipe_Success() throws Exception{
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RecipeId);
        recipeCommand.setDescription(DESCRIPTION);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/"+ RecipeId +"/update")).andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }



    @Disabled
    @Test
    void RecipeController_DeleteRecipe_Success() throws Exception{
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/"+ RecipeId +"/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                        .andExpect(header().string("Location","/"));

        Mockito.verify(recipeService,Mockito.times(1)).deleteByUserIdAndRecipeId(eq(UserId),eq(RecipeId));
    }
}