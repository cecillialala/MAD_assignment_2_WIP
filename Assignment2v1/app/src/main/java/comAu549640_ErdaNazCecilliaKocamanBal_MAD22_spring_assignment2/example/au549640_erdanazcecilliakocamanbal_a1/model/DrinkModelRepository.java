package comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.model;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.DrinkAdapter;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.database.DrinkDAO;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.database.DrinkDatabase;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.utils.ApplicationClass;

/*
* Inspiration: Lecture 4
* */

public class DrinkModelRepository {

    private DrinkDatabase db;
    private ExecutorService executor;
    private LiveData<List<DrinkModel>> drinklist;
    private MutableLiveData<List<DrinkModel>> drinkResults = new MutableLiveData<>();
    DrinkAdapter drinkAdapter;
    private DrinkDAO dbDao;
    Context context;
    private static DrinkModelRepository instance;
    private DrinkModelAdditional modelAdditional;
    RequestQueue queue;
    private static final String TAG = "MainActivity";

    private DrinkModelRepository(Application app){
        db = DrinkDatabase.getInstance(ApplicationClass.getContext());
        Log.d(TAG, "DrinkModelRepository: WE GET HERE");
        executor = Executors.newSingleThreadExecutor();
        drinklist = db.drinkDAO().getAll();
        context = ApplicationClass.getContext();
    }

    //Instance for repository to use in viewModels
    public static DrinkModelRepository getInstance(final Application application){
        if(instance  == null){
            synchronized (DrinkDatabase.class){
                if(instance == null){
                    instance =  new DrinkModelRepository(application);
                }
            }
        }
        return instance;
    }


    public LiveData<List<DrinkModel>> getDrinklist(){
        return drinklist;
    }

    public LiveData<List<DrinkModel>> getDrinkResultList() { return drinkResults ;}


    public ListenableFuture<DrinkModel> getDrinkAsynch(int uid){
        return db.drinkDAO().findDrink(uid);
    }


    public void updateDrinkAsynch(DrinkModel drink){

        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.drinkDAO().updateDrink(drink);
            }
        });
    }


    public void addDrinkAsynch(DrinkModel drink){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.drinkDAO().addDrink(drink);
            }
        });
    }



    public void searchDrink(String name){
    executor.execute(() -> sendRequest(name));
    }


    public ListenableFuture<DrinkModel> searchForDrinkAsynch(String name){
        return db.drinkDAO().findDrink(name);
    }

    public void deleteAllAsynch(List<DrinkModel> drinksToDelete){

        executor.execute(() -> db.drinkDAO().deleteAll(drinksToDelete));
    }

    public void loadData(){
        List<String> drinksList =  Arrays.asList("Gin%20Tonic", "Gimlet", "Dark%20and%20Stormy", "Espresso%20Martini", "Cosmopolitan", "Daiquiri", "Negroni", "Caipirinha", "Aperol%20Spritz", "Pina%20Colada");
        for (String d : drinksList) {
            sendRequest(d);
            Log.d(TAG, "loadData: " + d);
        }}

    private void sendRequest(String drinkName){
        if(queue==null){
            queue = Volley.newRequestQueue(context);
            Log.d(TAG, "sendRequest: queue");
        }
        String base = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=";
        String url = base + drinkName;
        Log.d(TAG, "sendRequest: do we get here");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        parseJson(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "That did not work!", error);
            }
        });
        queue.add(stringRequest);
    }

    private void parseJson(String json){
        Gson gson = new GsonBuilder().create();
        DrinkModelAdditional resultList = gson.fromJson(json, DrinkModelAdditional.class);
        drinkResults.setValue(new ArrayList<>());
        if(resultList!=null && resultList.getDrinks().size()>0){
            for(DrinkModel d : resultList.getDrinks()) {
                DrinkModel drinks = new DrinkModel();
                drinks.idDrink = d.idDrink;
                drinks.strDrink = d.strDrink;
                drinks.strCategory = d.strCategory;
                Log.d(TAG, "parseJson KIG HER!!!!!!" + d);
               drinkResults.getValue().add(drinks);
                executor.submit(()->db.drinkDAO().addDrink(drinks));
            }
        }
    }
    /*
    private void addDrinkFromParse(String name){
        DrinkModelAdditional drink = new DrinkModelAdditional();
        for(int i = 0; i < drinkResults.getValue().size(); i++)
        {
            if( i==index){

                executor.submit(()->db.drinkDAO().addDrink(drinks));

            }
        }
    }

     */
}
