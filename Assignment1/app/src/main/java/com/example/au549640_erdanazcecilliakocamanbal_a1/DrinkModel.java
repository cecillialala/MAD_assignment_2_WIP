package com.example.au549640_erdanazcecilliakocamanbal_a1;

import java.io.Serializable;

//EXPAND FOR SOURCES:
/*
* From CSV:
* Name;Category;Measure1;Measure2;Measure3;Measure4;Ingredient1;Ingredient2;Ingredient3;Ingredient4;Instructions
*/
public class DrinkModel implements Serializable {
    public String name;
    public String category;
    public String measure1;
    public String measure2;
    public String measure3;
    public String measure4;
    public String ingredient1;
    public String ingredient2;
    public String ingredient3;
    public String ingredient4;
    public String instructions;
    public double rating;

    public double getRating(){
        return rating;
    }
    public void setRating(double Rating){
        this.rating = Rating;
    }

    public DrinkModel(String Name, String Category,String Measure1,String Measure2,String Measure3,String Measure4, String Ingredient1, String Ingredient2,String Ingredient3,String Ingredient4,String Instruction){
        this.name=Name;
        this.category=Category;
        this.measure1 = Measure1;
        this.measure2 = Measure2;
        this.measure3 = Measure3;
        this.measure4 = Measure4;
        this.ingredient1 = Ingredient1;
        this.ingredient2 = Ingredient2;
        this.ingredient3 = Ingredient3;
        this.ingredient4 = Ingredient4;
        this.instructions = Instruction;
        setRating(0.0);
    }
}
