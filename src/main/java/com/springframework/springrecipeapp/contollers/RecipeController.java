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


import java.security.Principal;

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
    public String getRecipeById(@PathVariable String id, Model model, @AuthenticationPrincipal UserDetails user){
        Recipe recipe = recipeService.findById(parseLong(id));
        if(recipe.isPublished() || recipe.getContributor().getEmail().equals(user.getUsername())){
            model.addAttribute("loggedInUser", recipe.getContributor());
            model.addAttribute("recipe",recipe);
            return "recipe/show";
        }
        throw new NotFoundException("Recipe Not Found");
    }

    @GetMapping("/recipe/new")
    public String newRecipe(Model model,@AuthenticationPrincipal UserDetails userDetails){
        User user = userService.findUserByEmail(userDetails.getUsername());
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setContributorId(user.getId());
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

    @GetMapping("/userRecipes")
    public String getRecipesByAuthor(Model model,@AuthenticationPrincipal UserDetails userDetails){
        User user = userService.findUserByEmail(userDetails.getUsername());

        model.addAttribute("loggedInUser",userService.findUserById(user.getId()));
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

//    @PostMapping("user/{userId}/recipe/{recipeId}/publish")
//    public String publishRecipe(@PathVariable String userId, @PathVariable String recipeId,
//                                @AuthenticationPrincipal Principal principal){
//
//    }

    @GetMapping("/recipe/{recipeId}/publish")
    public String publish(@PathVariable String recipeId,Model model,@AuthenticationPrincipal UserDetails userDetails){
        Recipe recipe = recipeService.findById(parseLong(recipeId));
        User user = recipe.getContributor();
        if(!userDetails.getUsername().equals(user.getEmail())){
            log.error("You do not have this permission");
            return "redirect:/";
        }

        Recipe published = user.publishRecipe(recipe);
        recipeService.save(published);
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
