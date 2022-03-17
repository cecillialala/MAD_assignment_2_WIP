package comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

//EXPAND FOR SOURCES:
/*
* From CSV:
* Name;Category;Measure1;Measure2;Measure3;Measure4;Ingredient1;Ingredient2;Ingredient3;Ingredient4;Instructions
*/


@Entity
public class DrinkModel implements Serializable {

    @PrimaryKey
    @NonNull
    @SerializedName("idDrink")
    @Expose
    public String idDrink;

    @SerializedName("strDrink")
    @Expose
    public String strDrink;

    @SerializedName("strCategory")
    @Expose
    public String strCategory;

    @SerializedName("strInstructions")
    @Expose
    public String strInstructions;

    @SerializedName("strIngredient1")
    @Expose
    public String strIngredient1;
    @SerializedName("strIngredient2")
    @Expose
    public String strIngredient2;
    @SerializedName("strIngredient3")
    @Expose
    public String strIngredient3;
    @SerializedName("strIngredient4")
    @Expose
    public String strIngredient4;
    @SerializedName("strMeasure1")
    @Expose
    public String strMeasure1;
    @SerializedName("strMeasure2")
    @Expose
    public String strMeasure2;
    @SerializedName("strMeasure3")
    @Expose
    public String strMeasure3;
    @SerializedName("strMeasure4")
    @Expose
    public String strMeasure4;

    @SerializedName("strImageSource")
    @Expose
    public String strImageSource;


    public String getIdDrink() {
        return idDrink;
    }

    public void setIdDrink(String idDrink) {
        this.idDrink = idDrink;
    }

    public String getStrDrink() {
        return strDrink;
    }

    public void setStrDrink(String strDrink) {
        this.strDrink = strDrink;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }

    public String getStrIngredient1() {
        return strIngredient1;
    }

    public void setStrIngredient1(String strIngredient1) {
        this.strIngredient1 = strIngredient1;
    }

    public String getStrIngredient2() {
        return strIngredient2;
    }

    public void setStrIngredient2(String strIngredient2) {
        this.strIngredient2 = strIngredient2;
    }

    public String getStrIngredient3() {
        return strIngredient3;
    }

    public void setStrIngredient3(String strIngredient3) {
        this.strIngredient3 = strIngredient3;
    }

    public String getStrIngredient4() {
        return strIngredient4;
    }

    public void setStrIngredient4(String strIngredient4) {
        this.strIngredient4 = strIngredient4;
    }


    public String getStrMeasure1() {
        return strMeasure1;
    }

    public void setStrMeasure1(String strMeasure1) {
        this.strMeasure1 = strMeasure1;
    }

    public String getStrMeasure2() {
        return strMeasure2;
    }

    public void setStrMeasure2(String strMeasure2) {
        this.strMeasure2 = strMeasure2;
    }

    public String getStrMeasure3() {
        return strMeasure3;
    }

    public void setStrMeasure3(String strMeasure3) {
        this.strMeasure3 = strMeasure3;
    }

    public String getStrMeasure4() {
        return strMeasure4;
    }

    public void setStrMeasure4(String strMeasure4) {
        this.strMeasure4 = strMeasure4;
    }



    public String getStrImageSource() {
        return strImageSource;
    }

    public void setStrImageSource(String strImageSource) {
        this.strImageSource = strImageSource;
    }

    @SerializedName("rating")
    @Expose
    public double rating;
    public double getRating(){
        return rating;
    }
    public void setRating(double Rating){
        this.rating = Rating;
    }


    public DrinkModel(){}





}
