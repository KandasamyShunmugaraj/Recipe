package com.example.service;

import com.example.entity.Recipe;
import com.example.model.RecipeRequest;
import com.example.repository.RecipeRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class RecipeService {

    private final RecipeRepository repository;

    public List<Recipe> getRecipeByName(String name) {
        return repository.findByName(name);
    }


    public List<Recipe> getRecipeBasedOnSearchCriteria(RecipeRequest request) {
        return repository.findAll((Specification<Recipe>) (root, query, criteriaBuilder) -> {
            Predicate p = criteriaBuilder.conjunction();
            if (Objects.nonNull(request.getName()) && StringUtils.isNotEmpty(request.getName())) {
                p = criteriaBuilder.and(p, criteriaBuilder.equal(root.get("name"), request.getName()));
            }
            if (Objects.nonNull(request.getIsVegetarian()) ) {
                p = criteriaBuilder.and(p, criteriaBuilder.equal(root.get("isVegetarian"), request.getIsVegetarian()));
            }
            if (Objects.nonNull(request.getServings()) ) {
                p = criteriaBuilder.and(p, criteriaBuilder.equal(root.get("servings"), request.getServings()));
            }
            if (Objects.nonNull(request.getIngredients()) && StringUtils.isNotEmpty(request.getName())) {
                p = criteriaBuilder.and(p, criteriaBuilder.equal(root.get("ingredients"), request.getIngredients()));
            }
            if (Objects.nonNull(request.getInstructions()) && StringUtils.isNotEmpty(request.getName())) {
                p = criteriaBuilder.and(p, criteriaBuilder.equal(root.get("instructions"), request.getInstructions()));
            }
            return p;
        });
    }


    public Recipe saveRecipe(Recipe recipe) {
        return repository.save(recipe);
    }

    public void deleteRecipe(Recipe recipe) {
        repository.delete(recipe);
    }



}
