package com.springframework.springrecipeapp.services;

import com.springframework.springrecipeapp.commands.IngredientCommand;
import com.springframework.springrecipeapp.coverters.IngredientCommandToIngredient;
import com.springframework.springrecipeapp.coverters.IngredientToIngredientCommand;
import com.springframework.springrecipeapp.coverters.UnitOfMeasureCommandToUnitOfMeasure;
import com.springframework.springrecipeapp.domain.Ingredient;
import com.springframework.springrecipeapp.domain.Recipe;
import com.springframework.springrecipeapp.repsositories.RecipeRepository;
import com.springframework.springrecipeapp.repsositories.UnitOfMeasureRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand converter;
    private final UnitOfMeasureRepository uomRepository;

    @PersistenceContext
    EntityManager em;
    @Autowired
    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand converter, UnitOfMeasureRepository uomRepository) {
        this.recipeRepository = recipeRepository;
        this.converter = converter;
        this.uomRepository = uomRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> ingredientRecipeOptional = recipeRepository.findById(recipeId);
        if(ingredientRecipeOptional.isEmpty()){
            log.error("Recipe not found");
        }
        Recipe ingredientRecipe = ingredientRecipeOptional.get();

        Optional<Ingredient> requiredIngredient = ingredientRecipe.getIngredients()
                .stream().filter(ingredient -> ingredient.getId().equals(ingredientId))
                .findAny();

        if(requiredIngredient.isEmpty()){
            log.error("Ingredient was not found");
        }
        return this.converter.convert(requiredIngredient.get());
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredeientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredeientCommand.getRecipeId());
        if(recipeOptional.isEmpty()){
            //log.error("Recipe not found for id "+command.getRecipeId());
            return new IngredientCommand();
        }

        Recipe recipe = recipeOptional.get();

        IngredientCommandToIngredient converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        Optional<Ingredient> existingIngredient = recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredeientCommand.getId()))
                .findAny();

        if(existingIngredient.isEmpty()){
            recipe.addIngredient(converter.convert(ingredeientCommand));
        }
        else{
            Ingredient foundIngredient = existingIngredient.get();
            foundIngredient.setAmount(ingredeientCommand.getAmount());
            foundIngredient.setDescription(ingredeientCommand.getDescription());
            foundIngredient.setUnitOfMeasure(uomRepository.findById(ingredeientCommand.getUnitOfMeasure().getId())
                    .orElseThrow(() -> new RuntimeException("UOM not found exception")));
        }
        Recipe savedRecipe = recipeRepository.save(recipe);
        for (Ingredient ingredient : savedRecipe.getIngredients()) {
            if (ingredient.getAmount().equals(ingredeientCommand.getAmount())) {
                if (ingredient.getDescription().equals(ingredeientCommand.getDescription())) {
                    if (ingredient.getUnitOfMeasure().getId().equals(ingredeientCommand.getUnitOfMeasure().getId())) {
                        return this.converter
                                .convert(Optional.of(ingredient).get());
                    }
                }
            }
        }
        return this.converter
                .convert(Optional.<Ingredient>empty().get());
    }

    @Override
    @Transactional
    public void deleteByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> ingredientRecipeOptional = recipeRepository.findById(recipeId);
        if(ingredientRecipeOptional.isEmpty()){
            log.error("Recipe not found with id "+recipeId);
            return;
        }

        Recipe ingredientRecipe = ingredientRecipeOptional.get();
        Optional<Ingredient> ingredientOptional = ingredientRecipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .findAny();

        if(ingredientOptional.isPresent()){
            Ingredient ingredientToBeRemoved = ingredientOptional.get();
            ingredientToBeRemoved.setRecipe(null);
            ingredientRecipe.getIngredients().removeIf(ingredient -> ingredient.getId().equals(ingredientId));
            recipeRepository.save(ingredientRecipe);
        }
        else{
            log.error("Ingredient with id "+ingredientId+" was not found");
        }


    }

}
