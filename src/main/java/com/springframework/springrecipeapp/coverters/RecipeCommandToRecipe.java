package com.springframework.springrecipeapp.coverters;

import com.springframework.springrecipeapp.commands.RecipeCommand;
import com.springframework.springrecipeapp.domain.Recipe;
import com.springframework.springrecipeapp.domain.User;
import com.springframework.springrecipeapp.repsositories.UserRepository;
import com.springframework.springrecipeapp.services.UserService;
import com.springframework.springrecipeapp.services.UserServiceImpl;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {
    private final NotesCommandToNotes notesConverter;
    private final CategoryCommandToCategory categoryConverter;
    private final IngredientCommandToIngredient ingredientConverter;

    private final UserService userService;

    public RecipeCommandToRecipe(NotesCommandToNotes notesConverter, CategoryCommandToCategory categoryConverter,
                                 IngredientCommandToIngredient ingredientConverter, UserService userService) {
        this.notesConverter = notesConverter;
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.userService = userService;
    }

    @Nullable
    @Synchronized
    @Override
    public Recipe convert(RecipeCommand source) {
        ;
        if(source == null) return null;

        Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setCookTime(source.getCookTime());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setDirections(source.getDirections());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setNotes(notesConverter.convert(source.getNotes()));
        User contributor = userService.findUserById(source.getContributorId());
        recipe.setContributor(contributor);
        recipe.setImage(source.getImage());
        if(source.getCategories() != null){
            source.getCategories()
                    .forEach(categoryCommand -> recipe.getCategories().add(categoryConverter.convert(categoryCommand)));
        }

        if(source.getIngredients() != null){
            source.getIngredients()
                    .forEach(ingredientCommand -> recipe.addIngredient(ingredientConverter.convert(ingredientCommand)));
        }

        return recipe;
    }
}
