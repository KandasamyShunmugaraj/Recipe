package com.example.business;

import com.example.entity.Recipe;
import com.example.exception.RecipeException;
import com.example.model.RecipeRequest;
import com.example.model.RecipeResponse;
import com.example.service.RecipeService;
import com.example.util.BusinessLogger;
import com.example.util.JsonMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecipeBusiness {
    private RecipeService recipeService;

    public List<RecipeResponse> getRecipe(RecipeRequest request) {
        BusinessLogger.printInfoLogs("search Recipe started in business layer with input values"+ JsonMapper.toJson(request));
        //validate the input request
        inputValidation(request);
        List<Recipe> recipeList = recipeService.getRecipeBasedOnSearchCriteria(request);
        if (!CollectionUtils.isEmpty(recipeList)) {
            BusinessLogger.printInfoLogs("values retrieved from db"+ JsonMapper.toJson(recipeList));
            return recipeList.stream().map(this::mapRecipeResponse).collect(Collectors.toList());
        } else {
            BusinessLogger.printErrorLogs("recordNotFound for get rest call");
            throw new RecipeException("Record not found");
        }

    }

    public String updateRecipe(RecipeRequest request) {
        BusinessLogger.printInfoLogs("update Recipe started in business layer with input values"+ JsonMapper.toJson(request));
        if(Objects.isNull(request.getName()) || request.getName().isEmpty()){
            throw new RecipeException("Invalid input -> name is mandatory for delete");
        }
        inputValidation(request);
        List<Recipe> recipeList = recipeService.getRecipeByName(request.getName());
        if (!CollectionUtils.isEmpty(recipeList)) {
            Recipe recipe = recipeList.get(0);
            BusinessLogger.printInfoLogs("values retrieved from db"+ JsonMapper.toJson(recipe));
            setValuesinEntity(request, recipe);
            recipeService.saveRecipe(recipe);
            return "updated successfully";
        }else {
            BusinessLogger.printErrorLogs("recordNotFound for update rest call");
            return "no such record exists";
        }

    }

    public String saveRecipe(RecipeRequest request) {
        BusinessLogger.printInfoLogs("save Recipe started in business layer with input values"+ JsonMapper.toJson(request));
        inputValidation(request);
        Recipe recipe = new Recipe();
        setValuesinEntity(request, recipe);
        Recipe recipeResponse = recipeService.saveRecipe(recipe);
        BusinessLogger.printInfoLogs("save Recipe in db"+ JsonMapper.toJson(recipeResponse));
        return recipeResponse.getId() != 0 ? "saved successfully" : "error in saving";
    }

    public String deleteRecipe(RecipeRequest request) {
        BusinessLogger.printInfoLogs("delete Recipe started in business layer with input values"+ JsonMapper.toJson(request));
        inputValidation(request);
        if(Objects.isNull(request.getName()) || request.getName().isEmpty()){
            throw new RecipeException("Invalid input -> name is mandatory for delete");
        }
        List<Recipe> recipeList = recipeService.getRecipeByName(request.getName());
        if (!CollectionUtils.isEmpty(recipeList)) {
            Recipe recipe = recipeList.get(0);
            setValuesinEntity(request, recipe);
            recipeService.deleteRecipe(recipe);
            BusinessLogger.printInfoLogs("deleted successfully");
            return "deleted successfully";
        } else {
            BusinessLogger.printErrorLogs("recordNotFound for delete rest call");
            return "no such record exists";
        }

    }

    private void inputValidation(RecipeRequest request){
        if(Objects.nonNull(request.getName()) && request.getName().isEmpty()){
            throw new RecipeException("Invalid input -> name is empty");
        }else if(Objects.nonNull(request.getInstructions()) && request.getInstructions().isEmpty()){
            throw new RecipeException("Invalid input -> instructions is empty");
        }else if(Objects.nonNull(request.getIngredients()) && request.getIngredients().isEmpty()){
            throw new RecipeException("Invalid input -> ingredients is empty");
        }else if( Objects.isNull(request.getName())
                &&Objects.isNull(request.getInstructions())
                && Objects.isNull(request.getIngredients())
                &&Objects.isNull(request.getIsVegetarian())
                &&Objects.isNull(request.getServings())
                ) {
            throw new RecipeException("Invalid input in the request parameters");
        }
    }

    private void setValuesinEntity(RecipeRequest request, Recipe recipe) {
        recipe.setName(request.getName());
        recipe.setInstructions(request.getInstructions());
        recipe.setServings(request.getServings());
        recipe.setIngredients(request.getIngredients());
        recipe.setIsVegetarian(request.getIsVegetarian());
    }

    private RecipeResponse mapRecipeResponse(Recipe recipe){
        RecipeResponse recipeResponse = new RecipeResponse();
        recipeResponse.setName(recipe.getName());
        recipeResponse.setInstructions(recipe.getInstructions());
        recipeResponse.setServings(recipe.getServings());
        recipeResponse.setIngredients(recipe.getIngredients());
        recipeResponse.setIsVegetarian(recipe.getIsVegetarian());
        return recipeResponse;
    }


}
