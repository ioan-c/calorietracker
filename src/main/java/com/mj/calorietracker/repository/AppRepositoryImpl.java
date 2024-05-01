package com.mj.calorietracker.repository;

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

public class AppRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements AppRepository<T, ID> {
    private final EntityManager entityManager;
    public AppRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }
    @Override
    @Transactional
    public void refresh(T t) {
        this.entityManager.refresh(this.entityManager.merge(t));
    }
    @Override
    @Transactional
    public void clear() {
        this.entityManager.clear();
    }
}
