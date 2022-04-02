package comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.au549640_erdanazcecilliakocamanbal_a1.R;

import java.util.ArrayList;
import java.util.List;

import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.adapters.SearchAdapter;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.model.DrinkModel;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.viewmodel.SearchActivityViewModel;


public class SearchActivity extends AppCompatActivity  implements SearchAdapter.ISearchClickListener {

    private static final String TAG = "SearchActivity";
    private SearchAdapter searchAdapter;
    private List<DrinkModel> drinksList = new ArrayList<>();
    private SearchActivityViewModel vm;

    ActivityResultLauncher<Intent> launcher;
    String searchText;
    Intent intent;

    //Widgets
    Button backBtn;
    Button searchBtn;
    EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setViewModelsRecycler();
        setButtons();
    }

    private void setButtons(){
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(view -> finish());

        edtSearch = findViewById(R.id.edtSearch);

        searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(view -> searchDrink());
    }

    private void setViewModelsRecycler(){
        vm = new ViewModelProvider(this).get(SearchActivityViewModel.class);

        searchAdapter = new SearchAdapter(this);
        vm.getDrinksLiveData().observe(this, drink -> SearchActivity.this.drinksList = drink);
        vm.getDrinksResultLiveData().observe(this, drinkModelList -> {
            drinksList = drinkModelList;
            searchAdapter.updateDrinks(drinksList);
        });
        RecyclerView recyclerView = findViewById(R.id.rcViewSearchAndAdd);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(searchAdapter);
    }

    private void searchDrink() {
    searchText = edtSearch.getText().toString().toLowerCase();
    vm.setDrink(searchText);
    }

    @Override
    public void onDrinkClicked(int index) {
        DrinkModel d =   searchAdapter.getItem(index);
        Log.d(TAG, d.getStrDrink());
        vm.addDrink(d);
        Toast.makeText(SearchActivity.this, getString(R.string.addedToLibrary), Toast.LENGTH_SHORT).show();
        finish();
    }
}