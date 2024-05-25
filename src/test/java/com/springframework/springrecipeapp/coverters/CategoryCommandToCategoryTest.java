package com.springframework.springrecipeapp.coverters;

import com.springframework.springrecipeapp.commands.CategoryCommand;
import com.springframework.springrecipeapp.domain.Category;
import com.springframework.springrecipeapp.domain.Recipe;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {

    CategoryCommandToCategory converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryCommandToCategory();
    }

    @Test
    void sourceNull_convert_returnNull() {
        CategoryCommand categoryCommandObject = null;

        Category category = converter.convert(categoryCommandObject);

        Assertions.assertNull(category);
    }

    @Test
    void emptyObject_convert_notNull(){
        CategoryCommand categoryCommand = new CategoryCommand();

        Category category = converter.convert(categoryCommand);

        Assertions.assertNotNull(category);
    }

    @Test
    void commandObject_convert_convertObjectPropertiesMatch(){
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setDescription("Chinese");
        categoryCommand.setId(3L);

        Category category = converter.convert(categoryCommand);

        Assertions.assertNotNull(category);
        Assertions.assertEquals(categoryCommand.getId(), category.getId());
        Assertions.assertEquals(categoryCommand.getDescription(), category.getDescription());

    }
}