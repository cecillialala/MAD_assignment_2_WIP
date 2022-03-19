package comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.model.DrinkModel;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.model.DrinkModelRepository;

public class DetailedActivityViewModel extends AndroidViewModel {

    private Application application;
    private LiveData<List<DrinkModel>> drinklist;

    private DrinkModelRepository repository;
    private DrinkModel selectedDrink;

    public DetailedActivityViewModel(@NonNull Application application) {
        super(application);
        this.repository = DrinkModelRepository.getInstance(application);
        drinklist = repository.getDrinklist();
    }

    public void deleteDrink(DrinkModel drink){
        repository.deleteDrinks(drink);
    }
}
