package com.springframework.springrecipeapp.contollers;

import com.springframework.springrecipeapp.commands.RecipeCommand;
import com.springframework.springrecipeapp.domain.Recipe;
import com.springframework.springrecipeapp.domain.User;
import com.springframework.springrecipeapp.exceptions.NotFoundException;
import com.springframework.springrecipeapp.services.RecipeService;
import com.springframework.springrecipeapp.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import static java.lang.Long.parseLong;

@Controller
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;

    private final UserService userService;


    public RecipeController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @GetMapping("recipe/{id}/show")
    public String getRecipeById(@PathVariable String id, Model model){
        model.addAttribute("recipe",recipeService.findById(parseLong(id)));
        return "recipe/show";
    }

    @GetMapping("user/{id}/recipe/new")
    public String newRecipe(@PathVariable String id, Model model){
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setContributorId(parseLong(id));
        model.addAttribute("recipe",recipeCommand);
        return "recipe/recipeform";
    }

    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(parseLong(id)));
        return "recipe/recipeform";
    }
    @PostMapping("/recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return "recipe/recipeform";
        }

        RecipeCommand saveCommand = recipeService.saveRecipeCommand(recipeCommand);
        return "redirect:recipe/"+saveCommand.getId()+"/show";
    }

    @GetMapping("user/{id}/recipes")
    public String getRecipesByAuthor(@PathVariable String id, Model model){
        model.addAttribute("user",userService.findUserById(parseLong(id)));
        return "recipe/userRecipes";
    }

    @PostMapping("user/{id}/recipe")
    public String addNewRecipeByAuthor(@PathVariable String id,
                                       @Valid @ModelAttribute("recipe") RecipeCommand recipeCommand,
                                       BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "recipe/recipeform";
        }

        recipeCommand.setContributorId(parseLong(id));
        RecipeCommand saveCommand = recipeService.saveRecipeCommand(recipeCommand);
        return "redirect:/recipe/"+saveCommand.getId()+"/show";
    }

    @GetMapping("recipe/{id}/delete")
    public String delete(@PathVariable String id,@AuthenticationPrincipal UserDetails userDetails){
        User user = userService.findUserByEmail(userDetails.getUsername());
        recipeService.deleteByUserIdAndRecipeId(user.getId(),parseLong(id));
        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception ex){
        log.error("Handling not found exception");
        log.error(ex.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.setViewName("404error");
        mav.addObject("exception", ex);
        return mav;
    }
}
