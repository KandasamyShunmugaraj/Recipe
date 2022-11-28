package com.example.business;

import com.example.entity.Recipe;
import com.example.model.RecipeRequest;
import com.example.model.RecipeResponse;
import com.example.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class RecipeBusinessTest {

    private RecipeBusiness recipeBusines;
    private RecipeService recipeService;

    @BeforeEach
    void setUp() {
        recipeService=mock(RecipeService.class);
        recipeBusines=new RecipeBusiness(recipeService);
    }


    @Test
    void getRecipe() {
        //assign
        RecipeRequest request=new RecipeRequest();
        request.setName("test");
        List<Recipe> recipeList = formMockResponseObject();

        when(recipeService.getRecipeBasedOnSearchCriteria(request)).thenReturn(recipeList);
        //act
        List<RecipeResponse> recipeResponseList= recipeBusines.getRecipe(request);
        //assert
        assertEquals("test",recipeResponseList.get(0).getName());
    }

    @Test
    void updateRecipe() {
        //assign
        RecipeRequest request=new RecipeRequest();
        request.setName("test");
        List<Recipe> recipeList = formMockResponseObject();
        when(recipeService.getRecipeByName("test")).thenReturn(recipeList);
        //act and assert
        assertEquals("updated successfully",recipeBusines.updateRecipe(request));
    }

    @Test
    void saveRecipe() {
        //assign
        RecipeRequest request=new RecipeRequest();
        request.setName("test");
        Recipe recipe = new Recipe();
        recipe.setName("test");
        Recipe recipeResponse = new Recipe();
        recipeResponse.setName("test");
        recipeResponse.setId(1);
        //act
        when(recipeService.saveRecipe(recipe)).thenReturn(recipeResponse);
        //assert
        assertEquals("saved successfully",recipeBusines.saveRecipe(request));
    }


    @Test
    void deleteRecipe() {
        //assign
        RecipeRequest request=new RecipeRequest();
        request.setName("test");
        List<Recipe> recipeList = formMockResponseObject();
        when(recipeService.getRecipeByName("test")).thenReturn(recipeList);
        //act and assert
        assertEquals("deleted successfully",recipeBusines.deleteRecipe(request));
    }


    private List<Recipe> formMockResponseObject(){
        RecipeRequest request=new RecipeRequest();
        request.setName("test");
        List<Recipe> recipeList =new ArrayList<>();
        Recipe recipe = new Recipe();
        recipe.setName("test");
        recipeList.add(recipe);
        return recipeList;
    }


    }
