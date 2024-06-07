package com.springframework.springrecipeapp.contollers;

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
        User user = userService.findUserByEmail(userDetails.getUsername());
        return "redirect:/user/" + user.getId() + "/recipes";
    }
}
