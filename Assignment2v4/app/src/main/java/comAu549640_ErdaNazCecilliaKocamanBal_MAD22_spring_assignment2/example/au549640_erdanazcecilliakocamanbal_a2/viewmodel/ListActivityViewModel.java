package comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.model.DrinkModel;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.model.DrinkModelRepository;

public class ListActivityViewModel extends AndroidViewModel {

    private Application application;
    private LiveData<List<DrinkModel>> drinklist;
    MutableLiveData<DrinkModel> drinksListMUT;
    private DrinkModelRepository repository;
    //private DrinkModel selectedDrink;


    public ListActivityViewModel(@NonNull Application application) {
        super(application);
        this.repository = DrinkModelRepository.getInstance(application);
        drinklist = repository.getDrinklist();
    }
    public LiveData<List<DrinkModel>> getDrinksLiveData(){

        return drinklist;
    }

    public void getData(){
        repository.loadData();
    }

    public List<DrinkModel> getDrinks(){
        return getDrinksLiveData().getValue();
    }
/*
    public void drinkByID(int uid) {
        DrinkModel drinks = repository.getDrinkAsynch(uid);
        this.drinksListMUT.setValue(drinks);
    }

 */

    public LiveData<List<DrinkModel>> getDrinksResultLiveData(){

        return repository.getDrinkResultList();
    }

    public void setDrink(String name){
        repository.searchDrink(name);
    }

public void updateDrink(DrinkModel drink){
        repository.updateDrinkAsynch(drink);
}
}
