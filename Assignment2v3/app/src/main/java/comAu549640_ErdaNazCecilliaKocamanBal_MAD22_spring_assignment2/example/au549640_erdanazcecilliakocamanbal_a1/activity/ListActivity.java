package comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.viewmodel.FactoryViewModel;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.viewmodel.FactoryViewModelListActivity;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.viewmodel.ListActivityViewModel;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.viewmodel.SearchActivityViewModel;

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
    //ActivityResultLauncher<Intent> launcher;
    ActivityResultLauncher<Intent> launcher;
    private ListActivityViewModel vm;

    Context context;

    Button exitBtn;
    Button addBtn;

    //Added for assignment 2
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    final int resultCode = result.getResultCode();
                    if (resultCode == 1) {
                        final Intent i = result.getData();

                        /*
                        if (i != null) {
                            final Bundle b = i.getExtras();
                            Log.d(TAG, ((DrinkModel) b.getSerializable("drink")).GetDrinkData());
                            vm.updateDrink((Drink) b.getSerializable("drink"));
                        }

                         */
                    }
                });



        //vm = new ViewModelProvider(this).get(ListActivityViewModel.class);

        vm = new ViewModelProvider(ListActivity.this, new FactoryViewModelListActivity(getApplication())).get(ListActivityViewModel.class);

        drinkAdapter = new DrinkAdapter(ListActivity.this);

        vm.getDrinksLiveData().observe(ListActivity.this, drinkModelList -> {
            drinksList = drinkModelList;
            drinkAdapter.updateDrinks(drinksList);

        });

        if(vm.getDrinksLiveData() == null ) {
          Intent passedIntent = getIntent();
          int drinks = passedIntent.getIntExtra("idDrink", -1);
         // vm.drinkByID(drinks);
        }

        RecyclerView recyclerView = findViewById(R.id.drinkListRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));
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
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



        //Implemented from the adapter
    @Override
    public void onDrinkClicked(final int Index) {
        final DrinkModel drink = drinkAdapter.getItem(Index);
        final Intent intent = new Intent(ListActivity.this, DetailedActivity.class);
        final Bundle b = new Bundle();
        b.putSerializable("drink", drink);
        b.putInt("position", Index);
        intent.putExtras(b);
        Log.d(TAG, "Drink Clicked");
        launcher.launch(intent);
        Log.d(TAG, "Navigating to drink");
    }




}