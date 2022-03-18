package comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.au549640_erdanazcecilliakocamanbal_a1.R;

import java.util.ArrayList;
import java.util.List;

import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.DrinkAdapter;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.SearchAdapter;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.model.DrinkModel;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.viewmodel.FactoryViewModel;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.viewmodel.SearchActivityViewModel;

public class SearchActivity extends AppCompatActivity  implements SearchAdapter.ISearchClickListener {

    private SearchAdapter searchAdapter;
    private List<DrinkModel> drinksList = new ArrayList<>();
    private SearchActivityViewModel vm;

    private static final String TAG = SearchActivity.class.getSimpleName();
    String searchText;
    Intent intent;


    Button backBtn;     //Back button
    Button searchBtn;   //Search Button
    EditText edtSearch; //Search field

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        vm = new ViewModelProvider(this, new FactoryViewModel(getApplication())).get(SearchActivityViewModel.class);

        searchAdapter = new SearchAdapter(this);
        vm.getDrinksLiveData().observe(this, drink -> SearchActivity.this.drinksList = drink);
        vm.getDrinksResultLiveData().observe(this, drinkModelList -> {
            drinksList = drinkModelList;
            searchAdapter.updateDrinks(drinksList);
        });
        RecyclerView recyclerView = findViewById(R.id.rcViewSearchAndAdd);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(searchAdapter);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(view -> finish());

        edtSearch = findViewById(R.id.edtSearch);

        searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(view -> searchDrink());
    }

    private void searchDrink() {
    searchText = edtSearch.getText().toString().toLowerCase();

    vm.setDrink(searchText);
    }

    @Override
    public void onDrinkClicked(int index) {
    DrinkModel d =   searchAdapter.getItem(index);
        Log.d(TAG, d.getStrDrink());
    vm.addDrink(d);}
}