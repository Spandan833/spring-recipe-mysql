package com.springframework.springrecipeapp.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UnitOfMeasure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
}
