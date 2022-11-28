package com.example.repository;


import com.example.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe,Integer>, JpaSpecificationExecutor<Recipe> {
    List<Recipe> findByName(@Param("name") String name);

    @Override List<Recipe> findAll();
}

