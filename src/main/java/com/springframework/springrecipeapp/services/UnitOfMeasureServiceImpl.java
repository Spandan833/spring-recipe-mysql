package com.springframework.springrecipeapp.services;

import com.springframework.springrecipeapp.commands.UnitOfMeasureCommand;
import com.springframework.springrecipeapp.coverters.UnitOfMeasureToUnitOfMeasureCommand;
import com.springframework.springrecipeapp.repsositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService{

    private final UnitOfMeasureRepository uomRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand converter;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository uomRepository, UnitOfMeasureToUnitOfMeasureCommand converter) {
        this.uomRepository = uomRepository;
        this.converter = converter;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        HashSet<UnitOfMeasureCommand> uomSet = new HashSet<>();
        uomRepository.findAll().forEach(uom -> uomSet.add(this.converter.convert(uom)));
        return uomSet;
    }

}
