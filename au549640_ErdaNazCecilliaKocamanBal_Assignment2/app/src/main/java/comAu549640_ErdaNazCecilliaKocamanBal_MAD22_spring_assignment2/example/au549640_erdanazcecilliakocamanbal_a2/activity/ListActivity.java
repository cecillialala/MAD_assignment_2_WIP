package comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import java.util.ArrayList;
import java.util.List;

import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.DrinkService;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.adapters.DrinkAdapter;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.model.DrinkModel;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.utils.Constant;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.viewmodel.FactoryViewModelListActivity;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.viewmodel.ListActivityViewModel;

//EXPAND FOR SOURCES:
/*The following code is based on the Rick and Morty demo
 In the spirit of wubba lubba dub dub the preloaded drinks can be found in search activty.
 Also a big thanks to the student workers and Marius Hambro who showed me how to greatly optimize my code
 * */


public class ListActivity extends AppCompatActivity implements DrinkAdapter.IDrinksClickListener {
    private static final String TAG = "ListActivity";

    private DrinkAdapter drinkAdapter;
    private int index;
    private List<DrinkModel> drinksList = new ArrayList<>();
    ActivityResultLauncher<Intent> launcher;
    private ListActivityViewModel vm;

    //Widgets
    Button exitBtn;
    Button addBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);
        setupUI();
        startForegroundService();
    }

    private void startForegroundService(){
        Intent intent = new Intent(this, DrinkService.class);
        startService(intent);
    }

    private void setupUI(){
        setLauncher();
        setViewModelRecyclerView();
        setButtons();
    }

    public void setLauncher(){
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    final int resultCode = result.getResultCode();
                    if (resultCode == 1) {
                        final Intent i = result.getData();

                        if (i != null) {
                            final Bundle b = i.getExtras();
                            vm.updateDrink((DrinkModel) b.getSerializable("drink"));
                        }
                    }
                });
    }


    public void setViewModelRecyclerView(){
        vm = new ViewModelProvider(ListActivity.this, new FactoryViewModelListActivity(getApplication())).get(ListActivityViewModel.class);
        drinkAdapter = new DrinkAdapter(ListActivity.this);

        vm.getDrinksLiveData().observe(this, drink -> ListActivity.this.drinksList = drink);
        vm.getDrinksLiveData().observe(ListActivity.this, drinkModelList -> {
            drinksList = drinkModelList;
            drinkAdapter.updateDrinks(drinksList);
        });


        RecyclerView recyclerView = findViewById(R.id.drinkListRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));
        recyclerView.setAdapter(drinkAdapter);
    }

    public void setButtons(){
        exitBtn = findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(view -> finish());

        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(view -> startActivity(new Intent(ListActivity.this, SearchActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onDrinkClicked(final int Index) {
        final DrinkModel drink = drinkAdapter.getItem(Index);
        final Intent intent = new Intent(ListActivity.this, DetailedActivity.class);
        final Bundle b = new Bundle();
        b.putSerializable("drink", drink);
        b.putInt("position", Index);
        intent.putExtras(b);
        intent.putExtra(Constant.RATING,drinksList.get(Index).rating);
        this.index = Index;
        Log.d(TAG, "Drink Clicked");
        launcher.launch(intent);
        Log.d(TAG, "Navigating to drink");
    }
}