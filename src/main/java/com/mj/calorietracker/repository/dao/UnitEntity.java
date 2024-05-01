package com.mj.calorietracker.repository.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "units")
public class UnitEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String name;
    private String abbrev;
}
