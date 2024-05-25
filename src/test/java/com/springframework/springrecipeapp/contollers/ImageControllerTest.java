package com.springframework.springrecipeapp.contollers;

import com.springframework.springrecipeapp.commands.RecipeCommand;
import com.springframework.springrecipeapp.services.ImageService;
import com.springframework.springrecipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;

import static javax.management.Query.eq;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ImageControllerTest {

    ImageController imageController;
    MockMvc mockMvc;
    @Mock
    ImageService imageService;
    @Mock
    RecipeService recipeService;

    private final Long RECIPE_ID = 100L;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        imageController = new ImageController(imageService,recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    void showUploadForm() throws Exception{
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/"+RECIPE_ID+"/image"))
                .andExpect(view().name("recipe/imageform"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void handleImagePost() throws Exception{
        MockMultipartFile mpFile = new MockMultipartFile("imageFile","testing.txt",
                "text/plain","Spandan Recipe APp".getBytes());
        mockMvc.perform(multipart("/recipe/1/image").file(mpFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location","/recipe/1/show"));
    }

    @Test
    void recipeImageFromDB() throws Exception{
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        String data = "Spandan";
        byte[] byteArray = data.getBytes();
        Byte[] wrappedByte = new Byte[byteArray.length];
        int i = 0;
        for(Byte b : byteArray){
            wrappedByte[i++] = b;
        }

        recipeCommand.setImage(wrappedByte);

        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
                                                .andExpect(status().isOk())
                                                .andReturn()
                                                .getResponse();

        assertEquals(byteArray.length, response.getContentAsByteArray().length);

    }
}