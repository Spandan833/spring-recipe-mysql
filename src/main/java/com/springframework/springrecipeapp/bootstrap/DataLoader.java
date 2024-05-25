package com.springframework.springrecipeapp.bootstrap;

import com.springframework.springrecipeapp.domain.*;
import com.springframework.springrecipeapp.repsositories.CategoryRepository;
import com.springframework.springrecipeapp.repsositories.RecipeRepository;
import com.springframework.springrecipeapp.repsositories.UnitOfMeasureRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoader(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug("Loading Bootstrap data");
        recipeRepository.saveAll(this.getRecipes());
    }

    private List<Recipe> getRecipes() {

        List<Recipe> recipes = new ArrayList<>(2);

        //get UOMs
        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("Each");

        if(!eachUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByDescription("Tablespoon");

        if(!tableSpoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        if(!teaSpoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("Dash");

        if(!dashUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("Pint");

        if(!pintUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> cupsUomOptional = unitOfMeasureRepository.findByDescription("Cup");

        if(!cupsUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> poundUomOptional = unitOfMeasureRepository.findByDescription("Pound");

        if(!poundUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        //get optionals
        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure teaspoon = tableSpoonUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure pintUom = pintUomOptional.get();
        UnitOfMeasure cupsUom = cupsUomOptional.get();
        UnitOfMeasure poundUom = poundUomOptional.get();

        //get Categories
        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");

        if(!americanCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");

        if(!mexicanCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> indianCategoryOptional = categoryRepository.findByDescription("Indian");

        if(!indianCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();
        Category indianCategory = indianCategoryOptional.get();

        //Yummy Guac
        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setServings(2);
        guacRecipe.setUrl("http//:www.Cookme.com");
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd");

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws");
        guacNotes.setRecipe(guacRecipe);
        guacRecipe.setNotes(guacNotes);

        guacRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), eachUom));
        guacRecipe.addIngredient(new Ingredient("Kosher salt", new BigDecimal(".5"), teaspoon));
        guacRecipe.addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), tableSpoonUom));
        guacRecipe.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), tableSpoonUom));
        guacRecipe.addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), eachUom));
        guacRecipe.addIngredient(new Ingredient("Cilantro", new BigDecimal(2), tableSpoonUom));
        guacRecipe.addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(2), dashUom));
        guacRecipe.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), eachUom));

        guacRecipe.getCategories().add(americanCategory);
        guacRecipe.getCategories().add(mexicanCategory);
        System.out.println("============American Recipes=============");

        for(Recipe recipe : americanCategory.getRecipes()){
            System.out.println(recipe.getDescription());
        }
        //add to return list
        recipes.add(guacRecipe);

        //Yummy Tacos
        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Spicy Grilled Chicken Taco");
        tacosRecipe.setCookTime(9);
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setServings(2);
        tacosRecipe.setUrl("http//:www.Cookme.com");
        tacosRecipe.setDifficulty(Difficulty.MODERATE);

        tacosRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm");

        Notes tacoNotes = new Notes();
        tacoNotes.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ");
        tacosRecipe.setNotes(tacoNotes);
        recipeRepository.save(tacosRecipe);
        recipeRepository.save(guacRecipe);

        tacosRecipe.addIngredient(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("Dried Oregano", new BigDecimal(1), teaspoon));
        tacosRecipe.addIngredient(new Ingredient("Dried Cumin", new BigDecimal(1), teaspoon));
        tacosRecipe.addIngredient(new Ingredient("Sugar", new BigDecimal(1), teaspoon));
        tacosRecipe.addIngredient(new Ingredient("Salt", new BigDecimal(".5"), teaspoon));
        tacosRecipe.addIngredient(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), eachUom));
        tacosRecipe.addIngredient(new Ingredient("finely grated orange zestr", new BigDecimal(1), tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("Olive Oil", new BigDecimal(2), tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("boneless chicken thighs", new BigDecimal(4), tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("small corn tortillasr", new BigDecimal(8), eachUom));
        tacosRecipe.addIngredient(new Ingredient("packed baby arugula", new BigDecimal(3), cupsUom));
        tacosRecipe.addIngredient(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), eachUom));
        tacosRecipe.addIngredient(new Ingredient("radishes, thinly sliced", new BigDecimal(4), eachUom));
        tacosRecipe.addIngredient(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), pintUom));
        tacosRecipe.addIngredient(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), eachUom));
        tacosRecipe.addIngredient(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), eachUom));
        tacosRecipe.addIngredient(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cupsUom));
        tacosRecipe.addIngredient(new Ingredient("lime, cut into wedges", new BigDecimal(4), eachUom));

        tacosRecipe.getCategories().add(americanCategory);
        tacosRecipe.getCategories().add(mexicanCategory);

        Recipe chickenBiryani = new Recipe();
        chickenBiryani.setDescription("Indian Chicken Biryani");
        chickenBiryani.getCategories().add(indianCategory);
        chickenBiryani.setDifficulty(Difficulty.MODERATE);
        chickenBiryani.setServings(1);
        chickenBiryani.setCookTime(50);

        Notes biryaniNotes = new Notes();
        chickenBiryani.setNotes(biryaniNotes);
        chickenBiryani.setDirections("1 Marinate the chicken\n"+ "2 Prepare the rice \n"
                        + "3 Make the saffron milk\n"+ "4 Fry the onions\n" + "5 Cook the chicken\n" +
                        "6 Preaheat the chicken" + "\n" + "7 Assemble the biryani");

        biryaniNotes.setRecipeNotes("Biryani (pronounced bir-ja-ni), or biriyani, is a layered rice dish with its roots in the Mughal Dynasty in India. Mughals came to India in the 1500s and brought with them their own culture, language, and cuisine. When blended with those of India, this gave birth to many poetic things, biryani being one of them.\n" +
                "\n" +
                "Born in the royal kitchens of the Mughal India, biryani was developed as an effort to blend flavors of spicy Indian rice dishes to that of the Persian rice dish called pilaf.\n" +
                "\n" +
                "Just like the diversity in culture from one region of India to another, a biryani recipe changes from one region to another as well. Different biryani recipes were developed around the country in all mughal centers of India from Delhi to Lucknow, and all over the southern regions of India.");

        chickenBiryani.setNotes(biryaniNotes);

        chickenBiryani.addIngredient(new Ingredient("ginger garlic paste, divided", new BigDecimal(2),teaspoon));
        chickenBiryani.addIngredient(new Ingredient("Turmeric powder", new BigDecimal(1), teaspoon));
        chickenBiryani.addIngredient(new Ingredient("whole chilis, sliced (jalaepno or serrano)",new BigDecimal(3), eachUom));
        chickenBiryani.addIngredient(new Ingredient("plain whole milk",new BigDecimal(1/4), cupsUom));
        chickenBiryani.addIngredient(new Ingredient("Olive oil",new BigDecimal(2), teaspoon));
        chickenBiryani.addIngredient(new Ingredient("Bone in chicken",new BigDecimal(1.5), poundUom));
        chickenBiryani.addIngredient(new Ingredient("Ghee",new BigDecimal(3), teaspoon));
        chickenBiryani.addIngredient(new Ingredient("Long Grain Basmati Rice", new BigDecimal(2), cupsUom));
        chickenBiryani.addIngredient(new Ingredient("Whole black cardamom pods", new BigDecimal(3), eachUom));
        chickenBiryani.addIngredient(new Ingredient("Whole pepper corns", new BigDecimal(1), teaspoon));
        chickenBiryani.addIngredient(new Ingredient("Cooking oil", new BigDecimal(1), cupsUom));
        chickenBiryani.addIngredient(new Ingredient("Milk cold or temperature", new BigDecimal(1/3), cupsUom));
        chickenBiryani.addIngredient(new Ingredient("Whole black cardamom pods", new BigDecimal(3), eachUom));

        recipeRepository.save(chickenBiryani);
        recipes.add(chickenBiryani);

        Recipe palakPaneer = new Recipe();
        palakPaneer.setDescription("Palak Paneer");
        palakPaneer.getCategories().add(indianCategory);
        palakPaneer.setDifficulty(Difficulty.EASY);
        palakPaneer.setServings(4);
        palakPaneer.setCookTime(30);

        Notes palakPaneerNotes = new Notes();
        palakPaneerNotes.setRecipeNotes("Palak paneer is a popular Indian dish of creamy spinach gravy mixed with soft paneer cheese. It has a vibrant green color from the spinach.");
        palakPaneer.setNotes(palakPaneerNotes);

        palakPaneer.setDirections("1. Cook spinach and then blend into smooth paste \n" +
                "2. Brown paneer cubes \n" +
                "3. Prepare tomato onion base gravy \n" +
                "4. Add spinach puree, spices and paneer cubes. Simmer.");

        palakPaneer.addIngredient(new Ingredient("Spinach", new BigDecimal(2), poundUom));
        palakPaneer.addIngredient(new Ingredient("Paneer", new BigDecimal(1), poundUom));
        recipeRepository.save(palakPaneer);
        recipes.add(palakPaneer);
// other ingredients, save and add to list

        Recipe chanaMasala = new Recipe();
        chanaMasala.setDescription("Chana Masala");
        chanaMasala.getCategories().add(indianCategory);
        chanaMasala.setDifficulty(Difficulty.EASY);
        chanaMasala.setServings(6);
        chanaMasala.setCookTime(40);

        Notes chanaMasalaNotes = new Notes();
        chanaMasalaNotes.setRecipeNotes("Chana masala is a popular Indian dish made with chickpeas in a tomato based spicy gravy. It goes well with rice or roti.");
        chanaMasala.setNotes(chanaMasalaNotes);

        chanaMasala.setDirections("1. Soak chickpeas overnight and cook until soft\n" +
                "2. Prepare onion tomato masala paste\n" +
                "3. Add spices and cook well \n" +
                "4. Add cooked chickpeas and simmer for 10 minutes\n" +
                "5. Finish with cilantro");

        chanaMasala.addIngredient(new Ingredient("Chickpeas", new BigDecimal(2), cupsUom));
        chanaMasala.addIngredient(new Ingredient("Onion", new BigDecimal(1), eachUom));
        chanaMasala.addIngredient(new Ingredient("Tomatoes", new BigDecimal(3), eachUom));
// Other ingredients

        recipeRepository.save(chanaMasala);
        recipes.add(chanaMasala);
// Set all other properties like above

        Recipe dalMakhani = new Recipe();
        dalMakhani.setDescription("Dal Makhani");
// Set all properties

        Recipe tikkaMasala = new Recipe();
        tikkaMasala.setDescription("Chicken Tikka Masala");
// Set all properties

        Recipe naan = new Recipe();
        naan.setDescription("Naan");
// Set all properties

        return recipes;
    }
}