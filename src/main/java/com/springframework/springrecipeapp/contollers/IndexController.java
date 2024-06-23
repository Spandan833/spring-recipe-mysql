package com.springframework.springrecipeapp.contollers;

import com.springframework.springrecipeapp.domain.Recipe;
import com.springframework.springrecipeapp.domain.User;
import com.springframework.springrecipeapp.services.RecipeService;
import com.springframework.springrecipeapp.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.List;

@Slf4j
@Controller
public class IndexController {
    private final RecipeService recipeService;

    @Autowired
    private UserService userService;

    public IndexController(RecipeService recipeService){

        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","/index","/index.html"})
    public String getHome(Model model, @AuthenticationPrincipal UserDetails userDetails){
        List<Recipe> publishedRecipes = recipeService.findAll().stream()
                                                        .filter(recipe -> recipe.isPublished()).toList();
        User user = userService.findUserByEmail(userDetails.getUsername());
        model.addAttribute("recipes",publishedRecipes);
        model.addAttribute("loggedInUser",user);
        return "index";
    }
}
