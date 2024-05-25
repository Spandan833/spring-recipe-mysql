package com.springframework.springrecipeapp.contollers;

import com.springframework.springrecipeapp.commands.IngredientCommand;
import com.springframework.springrecipeapp.domain.Recipe;
import com.springframework.springrecipeapp.services.IngredientService;
import com.springframework.springrecipeapp.services.RecipeService;
import com.springframework.springrecipeapp.services.UnitOfMeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static java.lang.Long.parseLong;

@Controller
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    @Autowired
    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredients")
    public String getIngredients(@PathVariable String recipeId, Model model){
        model.addAttribute("recipe", recipeService.findById(parseLong(recipeId)));
        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredient(@PathVariable String recipeId,@PathVariable String ingredientId, Model model){
        model.addAttribute("ingredient",ingredientService.findByRecipeIdAndIngredientId(parseLong(recipeId),parseLong(ingredientId)));
        model.addAttribute("uomList",unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/new")
    public String addNewIngredient(@PathVariable String recipeId, Model model){
        Recipe recipe = recipeService.findById(parseLong(recipeId));
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(1000L);
        ingredientCommand.setRecipeId(recipe.getId());
        model.addAttribute("ingredient",ingredientCommand);
        model.addAttribute("uomList",unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }


    @RequestMapping("recipe/{recipeId}/ingredients/{ingredientId}/show")
    public String getIngredient(@PathVariable String recipeId,@PathVariable String ingredientId, Model model){
        Long recipeIdLong = parseLong(recipeId);
        Long ingredientIdLong = parseLong(ingredientId);
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeIdLong,ingredientIdLong));
        return "recipe/ingredient/show";
    }

    @PostMapping
    @RequestMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command){
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
        return "redirect:/recipe/"+savedCommand.getRecipeId()+"/ingredients/"+savedCommand.getId()+"/show";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String delete(@PathVariable String recipeId,@PathVariable String ingredientId){
        ingredientService.deleteByRecipeIdAndIngredientId(parseLong(recipeId),parseLong(ingredientId));
        return "redirect:/recipe/"+recipeId+"/ingredients";
    }

}
