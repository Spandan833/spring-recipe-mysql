package com.springframework.springrecipeapp.repsositories;

import com.springframework.springrecipeapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findByDescriptionTeaspoon() {
         Optional<UnitOfMeasure> optionalUnitOfMeasure  = unitOfMeasureRepository.findByDescription("Teaspoon");

        Assertions.assertEquals("Teaspoon", optionalUnitOfMeasure.get().getDescription());
    }
    @Test
    void findByDescriptionPinch() {
        Optional<UnitOfMeasure> optionalUnitOfMeasure  = unitOfMeasureRepository.findByDescription("Pinch");

        Assertions.assertEquals("Pinch", optionalUnitOfMeasure.get().getDescription());
    }

    @Test
    void dummy(){

    }

}