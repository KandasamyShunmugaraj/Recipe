package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("name")
    private String name;
    @JsonProperty("isVegetarian")
    private Boolean isVegetarian;
    @JsonProperty("servings")
    private Integer servings;
    @JsonProperty("ingredients")
    private String ingredients;
    @JsonProperty("instructions")
    private String instructions;
}
