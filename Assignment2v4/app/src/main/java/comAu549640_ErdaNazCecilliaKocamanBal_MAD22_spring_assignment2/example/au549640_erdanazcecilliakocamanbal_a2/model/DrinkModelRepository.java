package comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.model;

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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.adapters.DrinkAdapter;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.database.DrinkDAO;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.database.DrinkDatabase;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.utils.ApplicationClass;

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

    private DrinkModelRepository(Application app) {
        db = DrinkDatabase.getInstance(ApplicationClass.getContext());
        Log.d(TAG, "DrinkModelRepository: WE GET HERE");
        executor = Executors.newSingleThreadExecutor();
        drinklist = db.drinkDAO().getAll();
        //if(drinkResults.getValue() == null) {}
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


    public ListenableFuture<DrinkModel> getDrinkAsynch(final int uid){
        Future<DrinkModel> dm = executor.submit(new Callable<DrinkModel>() {
            @Override
            public DrinkModel call() {
                return db.drinkDAO().findDrink(uid);
            }
        });
        try{
            dm.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

       // return db.drinkDAO().findDrink(uid);
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
                Log.e(TAG, "Check internet connection for emulated device", error);
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
                Log.d(TAG, "Drinks has been parsed at: " + d);
               drinkResults.getValue().add(d);
            }
        }
    }
    public void addDrinkAsynch(final DrinkModel drink){
        executor.submit(() ->db.drinkDAO().addDrink(drink));
        Log.d(TAG, "A drink has been added async");
    }

    public void updateDrinkAsynch(DrinkModel drink){

        executor.execute(() -> db.drinkDAO().updateDrink(drink));
    }
}
