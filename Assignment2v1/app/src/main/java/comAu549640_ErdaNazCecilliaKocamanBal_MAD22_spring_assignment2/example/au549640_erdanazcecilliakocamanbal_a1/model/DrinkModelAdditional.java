package comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class DrinkModelAdditional {

    @SerializedName("drinks")
    @Expose
    private List<DrinkModel> drinks = null;
    public List<DrinkModel> getDrinks() {
        return drinks;
    }
    public void setDrinks(List<DrinkModel> drinks) {
        this.drinks = drinks;
    }
}
