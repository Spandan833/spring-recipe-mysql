package com.springframework.springrecipeapp.services;

import com.springframework.springrecipeapp.domain.Recipe;
import com.springframework.springrecipeapp.repsositories.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ImageServiceImplTest {

    ImageService imageService;

    @Mock
    RecipeRepository recipeRepository;

    private final long ID = 300L;

    @BeforeEach
    void setUp() {
        openMocks(this);
        imageService = new ImageServiceImpl(recipeRepository);
    }

    @Test
    public void Recipe_SaveImage_ImageSavedSuccessfully() throws Exception{
        Recipe recipe = Recipe.builder().id(ID).build();
        Optional<Recipe> recipeOptional = Optional.of(recipe);
        MultipartFile multipartFile = new MockMultipartFile("image.jpg","testing.txt","text/plain","Spandan Recipe Application".getBytes());
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        imageService.saveImageFile(ID,multipartFile);

        verify(recipeRepository, times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        Assertions.assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);

    }

}