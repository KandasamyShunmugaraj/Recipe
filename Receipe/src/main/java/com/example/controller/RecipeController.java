package com.example.controller;

import com.example.business.RecipeBusiness;
import com.example.model.RecipeRequest;
import com.example.model.RecipeResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@AllArgsConstructor
@RestController
public class RecipeController {

    private RecipeBusiness recipeBusiness;


    @GetMapping("/recipes/search")
    public List<RecipeResponse> searchMovie(@RequestBody RecipeRequest request) {
        return recipeBusiness.getRecipe(request);
    }

    @PutMapping("/recipe/update")
    public String updateRating(@RequestBody RecipeRequest request) {
       return recipeBusiness.updateRecipe(request);
    }


    @PostMapping("/recipe/save")
    public String saveMovie(@RequestBody RecipeRequest request) {
        return recipeBusiness.saveRecipe(request);
    }

    @DeleteMapping("/recipe/delete")
    public String deleteMovie(@RequestBody RecipeRequest request) {
        return recipeBusiness.deleteRecipe(request);
    }


}
