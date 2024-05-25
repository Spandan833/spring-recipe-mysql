package com.springframework.springrecipeapp.coverters;

import com.springframework.springrecipeapp.commands.CategoryCommand;
import com.springframework.springrecipeapp.domain.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {
    CategoryToCategoryCommand converter;

    @BeforeEach
    public void setUp() {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    public void categoryNull_convert_returnNUll(){
        Category category = null;

        CategoryCommand categoryCommand = converter.convert(category);

        Assertions.assertNull(categoryCommand);
    }

    @Test
    public void emptyCategoryObject_convert_notNull(){
        Category category = new Category();

        CategoryCommand categoryCommand = converter.convert(category);

        Assertions.assertNotNull(categoryCommand);
    }

    @Test
    public void categoryObject_convert_propertiesMatch(){
        Category category = new Category();
        category.setDescription("Mexican");
        category.setId(2L);

        CategoryCommand categoryCommand = converter.convert(category);

        Assertions.assertNotNull(categoryCommand);
        Assertions.assertEquals(category.getDescription(),categoryCommand.getDescription());
        Assertions.assertEquals(category.getId(), categoryCommand.getId());
    }

}