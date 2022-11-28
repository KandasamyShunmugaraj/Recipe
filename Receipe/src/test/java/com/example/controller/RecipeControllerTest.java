package com.example.controller;

import com.example.business.RecipeBusiness;
import com.example.exception.GlobalExceptionHandler;
import com.example.model.RecipeRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.CharEncoding;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RecipeControllerTest {
    public static final String RECIPE_SEARCH_API = "/recipes/search";
    public static final String RECIPE_UPDATE_API = "/recipe/update";
    public static final String RECIPE_DELETE_API = "/recipe/delete";
    public static final String RECIPE_SAVE_API = "/recipe/save";

    private MockMvc mockMvc;

    private final RecipeBusiness recipeBusiness = mock(RecipeBusiness.class);;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(new RecipeController(recipeBusiness))
            .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    @DisplayName("Success scenario for searching a recipe")
    void testSearchRecipeSuccess() throws Exception {
        final RecipeRequest recipeRequest = new RecipeRequest();
        recipeRequest.setName("test");
        when(recipeBusiness.getRecipe(recipeRequest)).thenReturn(new ArrayList<>());
        mockMvc
                .perform(MockMvcRequestBuilders.get(RECIPE_SEARCH_API).contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(recipeRequest)).characterEncoding(CharEncoding.UTF_8))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @DisplayName("Success scenario for update a recipe")
    void testUpdateRecipeSuccess() throws Exception {
        final RecipeRequest recipeRequest = new RecipeRequest();
        recipeRequest.setName("test");
      when(recipeBusiness.updateRecipe(recipeRequest)).thenReturn("updated successfully");
        mockMvc
                .perform(MockMvcRequestBuilders.put(RECIPE_UPDATE_API).contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(recipeRequest)).characterEncoding(CharEncoding.UTF_8))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @DisplayName("Success scenario for deleting a recipe")
    void testDeleteRecipeSuccess() throws Exception {
        final RecipeRequest recipeRequest = new RecipeRequest();
        recipeRequest.setName("test");
        when(recipeBusiness.deleteRecipe(recipeRequest)).thenReturn("deleted successfully");
        mockMvc
                .perform(MockMvcRequestBuilders.delete(RECIPE_DELETE_API).contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(recipeRequest)).characterEncoding(CharEncoding.UTF_8))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @DisplayName("Success scenario for saving a recipe")
    void testSaveRecipeSuccess() throws Exception {
        final RecipeRequest recipeRequest = new RecipeRequest();
        recipeRequest.setName("test");
        when(recipeBusiness.deleteRecipe(recipeRequest)).thenReturn("saved successfully");
        mockMvc
                .perform(MockMvcRequestBuilders.post(RECIPE_SAVE_API).contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(recipeRequest)).characterEncoding(CharEncoding.UTF_8))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }



    private static String toJson(final Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
