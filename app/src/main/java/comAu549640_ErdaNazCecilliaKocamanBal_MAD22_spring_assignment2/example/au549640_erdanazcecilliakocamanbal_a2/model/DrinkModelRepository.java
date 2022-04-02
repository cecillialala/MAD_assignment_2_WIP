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
* Inspiration: Lecture 4 and by looking at the demo roomdemoasynch from L5
* */

public class DrinkModelRepository {

    private static final String TAG = "MainActivity";
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


    private DrinkModelRepository(Application app) {
        db = DrinkDatabase.getInstance(ApplicationClass.getContext());
        Log.d(TAG, "DrinkModelRepository: We are in!");
        executor = Executors.newSingleThreadExecutor();
        drinklist = db.drinkDAO().getAll();
        context = ApplicationClass.getContext();

    }

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

    //Executing actions------------------------------------------------------------------

    public void addDrinkAsynch(final DrinkModel drink){
        executor.submit(() ->db.drinkDAO().addDrink(drink));
    }

    public void updateDrinkAsynch(DrinkModel drink){
        executor.execute(() -> db.drinkDAO().updateDrink(drink));
    }

    public void deleteDrinks(DrinkModel drink){
        executor.execute(() -> db.drinkDAO().deleteDrink(drink));
    }

    public LiveData<List<DrinkModel>> getDrinklist(){
        return drinklist;
    }

    public LiveData<List<DrinkModel>> getDrinkResultList() { return drinkResults ;}

    public void searchDrink(String name){
    executor.execute(() -> sendRequest(name, true));
    }



    //Setting the default drinks, sending request and parsing-------------------------------

    public void defaultDrinks(){
        if(drinklist != null || drinklist.getValue() != null || drinklist.getValue().size() < 1){
            List<String> list= new ArrayList<String>();
            list.add("Gin_Tonic");
            list.add("Gimlet");
            list.add("Dark_and_Stormy");
            list.add("Espresso_Martini");
            list.add("Pina_Colada");
            for (String d : list) {
                sendRequest(d, false);
                Log.d(TAG, "loadData: " + d);
            }
        }
    }

    private void sendRequest(String drinkName, boolean search){
        if(queue==null){
            queue = Volley.newRequestQueue(context);
            Log.d(TAG, "sendRequest: queue");

        }
        String base = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=";
        String url = base + drinkName;
        Log.d(TAG, "sendRequest: We in sendRequest");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        parseJson(response, search);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Check internet connection for emulated device", error);
            }
        });
        queue.add(stringRequest);
    }

    private void parseJson(String json, boolean search){
        Gson gson = new GsonBuilder().create();
        DrinkModelAdditional resultList = gson.fromJson(json, DrinkModelAdditional.class);
        drinkResults.setValue(new ArrayList<>());
        if(resultList!=null && resultList.getDrinks().size()>0){
            for(DrinkModel d : resultList.getDrinks()) {
                Log.d(TAG, "Drinks has been parsed at: " + d);
               if(search){
                   drinkResults.getValue().add(d);
               }
               else{
                   addDrinkAsynch(d);
               }

            }
        }
    }

}
