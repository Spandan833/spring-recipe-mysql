package com.springframework.springrecipeapp.services;

import com.springframework.springrecipeapp.commands.UnitOfMeasureCommand;
import com.springframework.springrecipeapp.coverters.UnitOfMeasureToUnitOfMeasureCommand;
import com.springframework.springrecipeapp.domain.UnitOfMeasure;
import com.springframework.springrecipeapp.repsositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureServiceImplTest {

    @Mock
    UnitOfMeasureRepository uomRepository;

    UnitOfMeasureToUnitOfMeasureCommand converter;

    UnitOfMeasureService uomSerive;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        converter = new UnitOfMeasureToUnitOfMeasureCommand();
        uomSerive = new UnitOfMeasureServiceImpl(uomRepository,converter);
    }

    @Test
    void listAllUoms() {
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();

        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);
        unitOfMeasures.add(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);
        unitOfMeasures.add(uom2);

        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setId(3L);
        unitOfMeasures.add(uom3);

        UnitOfMeasure uom4 = new UnitOfMeasure();
        uom4.setId(4L);
        unitOfMeasures.add(uom4);

        UnitOfMeasure uom5 = new UnitOfMeasure();
        uom5.setId(5L);
        unitOfMeasures.add(uom5);

        UnitOfMeasure uom6 = new UnitOfMeasure();
        uom6.setId(6L);
        unitOfMeasures.add(uom6);

        Mockito.when(uomRepository.findAll()).thenReturn(unitOfMeasures);


        Assertions.assertEquals(uomSerive.listAllUoms().size(),6);

        Mockito.verify(uomRepository,Mockito.times(1)).findAll();
    }
}