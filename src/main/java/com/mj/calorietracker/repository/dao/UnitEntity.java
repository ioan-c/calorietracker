package com.mj.calorietracker.repository.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "units")
public class UnitEntity {
    @Id
    private String name;
    private String abbrev;
}
