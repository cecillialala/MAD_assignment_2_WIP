package com.example.au549640_erdanazcecilliakocamanbal_a1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

//EXPAND FOR SOURCES:
/*In order to implement the csv reader I've followed this youtube tutorial by Dr. Brain Fraser
* https://www.youtube.com/watch?v=i-TqNzUryn8
* Only big change is that our CSV file is separated by ; and that I don't have the set get in my DrinkModel so I'll just add them
* Also this: https://stackoverflow.com/questions/11992694/how-to-determine-if-a-list-of-string-contains-null-or-empty-elements
* And this to save on rotation: https://developer.android.com/guide/components/activities/activity-lifecycle and this https://coderanch.com/t/724810/practice-save-fragment-state-replace
 * And based on demos from class lesson 3
 * Another thing I would've liked to do is make folders for the activities, models etc.
 * but came across a peculiar error that I didn't have the ability to circumvent,
 * otherwise I would've done that. :)
 * */


public class ListActivity extends AppCompatActivity implements DrinkAdapter.IDrinksClickListener {
    private DrinkAdapter drinkAdapter;
    private int index;
    private double rating;
    private ArrayList<DrinkModel> drinksList = new ArrayList<>();
    ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        readCSVData();

        launcher = this.<Intent, ActivityResult>registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                rating = result.getData().getDoubleExtra(Constant.RATING, 0.0);
                drinksList.get(index).setRating(rating/10); //Because of seekbar's max being 100 and to get the double to work
                drinkAdapter.updateDrinks(drinksList);
            } else {
                //Toast.makeText(getApplicationContext(), "Something happened", Toast.LENGTH_SHORT).show();
            }
        });

        if (savedInstanceState != null) {
            drinksList = (ArrayList<DrinkModel>) savedInstanceState.getSerializable("Drinks");
        }


        Button exitBtn = findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(view -> finish());

        drinkAdapter = new DrinkAdapter(this,drinksList);
        RecyclerView recyclerView = findViewById(R.id.drinkListRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(drinkAdapter);
        drinkAdapter.updateDrinks(drinksList);

    }

    //Instead of onResume etc.
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("Drinks",drinksList);
    }

    private void readCSVData(){
        InputStream inputStream = getResources().openRawResource(R.raw.drinks_data);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line = "";

        try{
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split(";");

                //In case of something in the CSV file being null it will be set to ""
                for (int i = 0; i < tokens.length; i++) {
                    if(tokens[i].contains("null")){
                        tokens[i] = "";
                    }
                }
                drinksList.add(new DrinkModel(
                        tokens[0],
                        tokens[1],
                        tokens[2],
                        tokens[3],
                        tokens[4],
                        tokens[5],
                        tokens[6],
                        tokens[7],
                        tokens[8],
                        tokens[9],
                        tokens[10]
                )
                );
            }
        }catch (IOException e) {
            Log.wtf("ListActivity", "Error reading from line: " + line,e);
            e.printStackTrace();
        }
    }

    //Implemented from the adapter
    @Override
    public void onDrinkClicked(int Index) {
        Intent intent = new Intent(this,DetailedActivity.class);
        intent.putExtra(Constant.NAME, drinksList.get(Index).name);
        intent.putExtra(Constant.CATEGORY, drinksList.get(Index).category);
        intent.putExtra(Constant.MEASURES1, drinksList.get(Index).measure1);
        intent.putExtra(Constant.MEASURES2,drinksList.get(Index).measure2);
        intent.putExtra(Constant.MEASURES3,drinksList.get(Index).measure3);
        intent.putExtra(Constant.MEASURES4,drinksList.get(Index).measure4);
        intent.putExtra(Constant.INGREDIENTS1,drinksList.get(Index).ingredient1);
        intent.putExtra(Constant.INGREDIENTS2,drinksList.get(Index).ingredient2);
        intent.putExtra(Constant.INGREDIENTS3,drinksList.get(Index).ingredient3);
        intent.putExtra(Constant.INGREDIENTS4,drinksList.get(Index).ingredient4);
        intent.putExtra(Constant.INSTRUCTIONS,drinksList.get(Index).instructions);
        intent.putExtra(Constant.RATING,drinksList.get(Index).rating);
        intent.putExtra(Constant.IMAGE,Constant.drinksImages[Index]);
        this.index = Index;
        launcher.launch(intent);
    }

}