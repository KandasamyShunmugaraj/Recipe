package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.NamedQuery;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Recipe")
@NamedQuery(name = "Recipe.findByName",
        query = "SELECT m FROM Recipe m WHERE m.name = ?1")
public class Recipe {

    @Id
    @GeneratedValue
    @Column(name = "Id")
    private int id;
    @Column(name = "Name")
    private String name;
    @Column(name = "Vegetarian")
    private Boolean isVegetarian;
    @Column(name = "Servings")
    private Integer servings;
    @Column(name = "Ingredients")
    private String ingredients;
    @Column(name = "Instructions")
    private String instructions;

}
