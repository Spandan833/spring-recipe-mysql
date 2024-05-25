package com.springframework.springrecipeapp.coverters;

import jakarta.annotation.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import com.springframework.springrecipeapp.commands.CategoryCommand;
import com.springframework.springrecipeapp.domain.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    @Synchronized
    @Nullable
    @Override
    public Category convert(@Nullable CategoryCommand source) {
        if(source == null) return null;

        final Category category = new Category();
        category.setId(source.getId());
        category.setDescription(source.getDescription());

        return category;
    }
}
