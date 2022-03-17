package comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.au549640_erdanazcecilliakocamanbal_a1.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;

import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.DrinkAdapter;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.database.DrinkDatabase;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.model.DrinkModel;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.model.DrinkModelRepository;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.viewmodel.FactoryViewModel;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.viewmodel.ListActivityViewModel;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.viewmodel.SearchActivityViewModel;

public class SearchActivity extends AppCompatActivity  implements DrinkAdapter.IDrinksClickListener {

    private DrinkDatabase db;
    private ExecutorService executor;
    //TO DO Make sure keyboard goes down after typing lol
    private DrinkAdapter drinkAdapter;
    private int index;
    private double rating;
    private List<DrinkModel> drinksList = new ArrayList<>();
    private static final String TAG2 = "SearchActivity";
    private SearchActivityViewModel vm;

    private DrinkModelRepository repository;

    Button backBtn;
    Button searchBtn;
    EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        vm = new ViewModelProvider(this, new FactoryViewModel(getApplication())).get(SearchActivityViewModel.class);

        drinkAdapter = new DrinkAdapter(this);
        vm.getDrinksLiveData().observe(this, drink -> SearchActivity.this.drinksList = drink);

        RecyclerView recyclerView = findViewById(R.id.rcViewSearchAndAdd);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(drinkAdapter);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(view -> finish());

        edtSearch = findViewById(R.id.edtSearch);

        searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(view -> searchDrink());

        vm.getDrinksResultLiveData().observe(this, drinkModelList -> {
            drinksList = drinkModelList;
            drinkAdapter.updateDrinks(drinksList);

        });
    }

    private void searchDrink() {
    String searchText = edtSearch.getText().toString().toLowerCase();
    vm.setDrink(searchText);
    }
    @Override
    public void onDrinkClicked(int index) {

        Intent intent = new Intent(this, SearchActivity.class);
    }
}