package com.springframework.springrecipeapp.coverters;

import com.springframework.springrecipeapp.commands.IngredientCommand;
import com.springframework.springrecipeapp.commands.RecipeCommand;
import com.springframework.springrecipeapp.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe,RecipeCommand> {
    private final CategoryToCategoryCommand categoryToCategoryCommand;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final NotesToNotesCommand notesToNotesCommand;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryToCategoryCommand, IngredientToIngredientCommand ingredientToIngredientCommand, NotesToNotesCommand notesToNotesCommand) {
        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.notesToNotesCommand = notesToNotesCommand;
    }


    @Nullable
    @Synchronized
    @Override
    public RecipeCommand convert(Recipe source) {
        if(source == null) return null;

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(source.getId());
        recipeCommand.setServings(source.getServings());
        recipeCommand.setDifficulty(source.getDifficulty());
        recipeCommand.setDirections(source.getDirections());
        HashSet<IngredientCommand> ingredientCommandHashSet = new HashSet<>();
        source.getIngredients().forEach(ingredient -> ingredientCommandHashSet.add(ingredientToIngredientCommand.convert(ingredient)));
        recipeCommand.setIngredients(ingredientCommandHashSet);
        recipeCommand.setNotes(notesToNotesCommand.convert(source.getNote()));
        recipeCommand.setPrepTime(source.getPrepTime());
        recipeCommand.setImage(source.getImage());
        if(source.getCategories() != null){
            source.getCategories().forEach(category -> recipeCommand.getCategories().add(categoryToCategoryCommand.convert(category)));
        }
        recipeCommand.setDescription(source.getDescription());
        return recipeCommand;
    }
}
