package com.example.integration;

import com.example.RecipeApplication;
import com.example.model.RecipeRequest;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RecipeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RecipeIT {

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    @LocalServerPort
    private int port;

    @Test
    @Order(1)
    void testSaveRecipe() {
        RecipeRequest recipe = new RecipeRequest();
        recipe.setName("test");
        HttpEntity<RecipeRequest> entity = new HttpEntity<>(recipe, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/recipe/save"),
                HttpMethod.POST, entity, String.class);
        assertEquals(SC_OK, response.getStatusCode().value());
    }

    @Test
    @Order(2)
    void testUpdateRecipe() {
        RecipeRequest recipe = new RecipeRequest();
        recipe.setName("test");
        recipe.setIsVegetarian(true);
        HttpEntity<RecipeRequest> entity = new HttpEntity<>(recipe, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/recipe/update"),
                HttpMethod.PUT, entity, String.class);
        assertEquals(SC_OK, response.getStatusCode().value());
    }


    @Test
    @Order(3)
    void testGetRecipe() {
        RecipeRequest recipe = new RecipeRequest();
        recipe.setName("test");
        HttpEntity<RecipeRequest> entity = new HttpEntity<>(recipe, headers);
        restTemplate.setRequestFactory(new CustomHttpComponentsClientHttpRequestFactory());
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/recipes/search"),
                HttpMethod.GET, entity, String.class);
        assertEquals(SC_OK, response.getStatusCode().value());
    }

    @Test
    @Order(4)
    void testDeleteRecipe() {
        RecipeRequest recipe = new RecipeRequest();
        recipe.setName("test");
        recipe.setIsVegetarian(true);
        HttpEntity<RecipeRequest> entity = new HttpEntity<>(recipe, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/recipe/delete"),
                HttpMethod.DELETE, entity, String.class);
        assertEquals(SC_OK, response.getStatusCode().value());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    //below 2 private method is required for get with post
    private static final class CustomHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {
        @Override
        protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
            if (HttpMethod.GET.equals(httpMethod)) {
                return new HttpEntityEnclosingGetRequestBase(uri);
            }
            return super.createHttpUriRequest(httpMethod, uri);
        }
    }

    private static final class HttpEntityEnclosingGetRequestBase extends HttpEntityEnclosingRequestBase {
        public HttpEntityEnclosingGetRequestBase(final URI uri) {
            super.setURI(uri);
        }
        @Override
        public String getMethod() {
            return HttpMethod.GET.name();
        }
    }
}
