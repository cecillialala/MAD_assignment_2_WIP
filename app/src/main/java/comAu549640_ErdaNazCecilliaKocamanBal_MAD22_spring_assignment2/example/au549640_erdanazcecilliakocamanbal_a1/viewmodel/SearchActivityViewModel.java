package comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.model.DrinkModel;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.model.DrinkModelRepository;

public class SearchActivityViewModel extends AndroidViewModel {

    private Application application;
    private LiveData<List<DrinkModel>> drinklist;

    private DrinkModelRepository repository;
    private DrinkModel selectedDrink;

    public SearchActivityViewModel(@NonNull Application application) {
        super(application);
        this.repository = DrinkModelRepository.getInstance(application);
        drinklist = repository.getDrinklist();
    }

    public LiveData<List<DrinkModel>> getDrinksLiveData(){

        return drinklist;
    }

    public LiveData<List<DrinkModel>> getDrinksResultLiveData(){

        return repository.getDrinkResultList();
    }

    public void setDrink(String name){
    repository.searchDrink(name);
    }

    public void addDrink(){
        repository.loadData();
    }
}