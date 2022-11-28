package com.example.repository;

import com.example.entity.Recipe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RecipeRepositoryTest {

    @Autowired
    private RecipeRepository repository;

    @Test
    void find_no_recipe_if_repo_is_empty() {
        Recipe recipe = getRecipe();
        repository.save(recipe);
        Iterable<Recipe> recipeList = repository.findAll();
        assertThat(recipeList).isNotEmpty();
    }

    @Test
    void should_store_a_recipe() {
        Recipe recipe = getRecipe();
        Recipe recipeResponse = repository.save(recipe);
        assertThat(recipeResponse).hasFieldOrPropertyWithValue("name", "test")
                .hasFieldOrPropertyWithValue("servings", 5)
                .hasFieldOrPropertyWithValue("ingredients", "rice")
                .hasFieldOrPropertyWithValue("instructions", "oven")
                .hasFieldOrPropertyWithValue("isVegetarian", true);

    }

    @Test
    void should_get_a_recipe() {
        Recipe recipe = getRecipe();
        repository.save(recipe);
        List<Recipe>  recipeList = repository.findByName("test");
        assertEquals("test",recipeList.get(0).getName());
    }

    @Test
    void should_update_a_recipe() {
        Recipe recipe = getRecipe();
        repository.save(recipe);
        recipe.setName("test1");
        Recipe recipeResponse = repository.save(recipe);
        assertThat(recipeResponse).hasFieldOrPropertyWithValue("name", "test1");

    }

    @Test
    void should_delete_a_recipe() {
        Recipe recipe = getRecipe();
        repository.save(recipe);
        repository.delete(recipe);
        Iterable<Recipe> recipeList = repository.findAll();
        assertThat(recipeList).isEmpty();
    }


    private Recipe getRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName("test");
        recipe.setServings(5);
        recipe.setIsVegetarian(true);
        recipe.setInstructions("oven");
        recipe.setIngredients("rice");
        return recipe;
    }

}
