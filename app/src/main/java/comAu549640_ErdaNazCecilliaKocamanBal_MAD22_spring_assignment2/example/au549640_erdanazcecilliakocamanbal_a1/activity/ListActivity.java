package comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.au549640_erdanazcecilliakocamanbal_a1.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.model.DrinkModelRepository;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.utils.Constant;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.DrinkAdapter;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.model.DrinkModel;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.viewmodel.ListActivityViewModel;

//EXPAND FOR SOURCES:
/*In order to implement the csv reader I've followed this youtube tutorial by Dr. Brain Fraser
* https://www.youtube.com/watch?v=i-TqNzUryn8
* Only big change is that our CSV file is separated by ; and that I don't have the set get in my DrinkModel so I'll just add them
* Also this: https://stackoverflow.com/questions/11992694/how-to-determine-if-a-list-of-string-contains-null-or-empty-elements
* And this to save on rotation: https://developer.android.com/guide/components/activities/activity-lifecycle and this https://coderanch.com/t/724810/practice-save-fragment-state-replace
 * And based on demos from class lesson 3
 * */


public class ListActivity extends AppCompatActivity implements DrinkAdapter.IDrinksClickListener {
    private DrinkAdapter drinkAdapter;
    private int index;
    private double rating;
    private List<DrinkModel> drinksList = new ArrayList<>();
    ActivityResultLauncher<Intent> launcher;
    DrinkModelRepository repository;

    Button exitBtn;
    Button addBtn;

    ListActivityViewModel vm;

    //Added for assignment 2
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        vm = new ViewModelProvider(this).get(ListActivityViewModel.class);
        drinkAdapter = new DrinkAdapter(this);

        repository = DrinkModelRepository.getInstance(getApplication());

        RecyclerView recyclerView = findViewById(R.id.drinkListRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(drinkAdapter);


        exitBtn = findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(view -> finish());

        addBtn = findViewById(R.id.addBtn);
               addBtn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       startActivity(new Intent(ListActivity.this, SearchActivity.class));
                   }
               });

        vm.getDrinksLiveData().observe(this, drinkModelList -> {
            drinksList = drinkModelList;
            drinkAdapter.updateDrinks(drinksList);
            if(drinkAdapter.getItemCount() == 0){
                vm.getData();
            }
        });
    }


    //Implemented from the adapter
    @Override
    public void onDrinkClicked(int Index) {
        Intent intent = new Intent(this, DetailedActivity.class);

        intent.putExtra(Constant.NAME, drinksList.get(Index).strDrink);
        intent.putExtra(Constant.CATEGORY, drinksList.get(Index).strCategory);
        intent.putExtra(Constant.MEASURES1, drinksList.get(Index).strMeasure1);
        intent.putExtra(Constant.MEASURES2,drinksList.get(Index).strMeasure2);
        intent.putExtra(Constant.MEASURES3,drinksList.get(Index).strMeasure3);
        intent.putExtra(Constant.MEASURES4,drinksList.get(Index).strMeasure4);
        intent.putExtra(Constant.INGREDIENTS1,drinksList.get(Index).strIngredient1);
        intent.putExtra(Constant.INGREDIENTS2,drinksList.get(Index).strIngredient2);
        intent.putExtra(Constant.INGREDIENTS3,drinksList.get(Index).strIngredient3);
        intent.putExtra(Constant.INGREDIENTS4,drinksList.get(Index).strIngredient3);
        intent.putExtra(Constant.INSTRUCTIONS,drinksList.get(Index).strInstructions);
        intent.putExtra(Constant.RATING,drinksList.get(Index).rating);
        //intent.putExtra(Constant.IMAGE,Constant.drinksImages[Index]);
        this.index = Index;
        launcher.launch(intent);

    }

}