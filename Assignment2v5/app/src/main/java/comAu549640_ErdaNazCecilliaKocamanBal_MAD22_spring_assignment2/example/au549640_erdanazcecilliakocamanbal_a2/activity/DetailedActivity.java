package comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.au549640_erdanazcecilliakocamanbal_a1.R;

import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.model.DrinkModel;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.utils.Constant;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.viewmodel.DetailedActivityViewModel;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.viewmodel.FactoryViewModelDetailedActivity;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.viewmodel.FactoryViewModelListActivity;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.viewmodel.ListActivityViewModel;

//EXPAND FOR SOURCES:
/*
* Seekbar is made from this: https://abhiandroid.com/ui/seekbar && https://stackoverflow.com/questions/33349424/android-seekbar-changing-the-value
* Buttons from this: https://stackoverflow.com/questions/33962220/how-to-return-seekbar-value-to-previous-activity
* And inspiration from the demos from our lectures lection 2 and 3
*/

public class DetailedActivity extends AppCompatActivity {

    private TextView tvName, tvCategory,tvInstructions, tvRating;
    private TextView tvMeasure1, tvMeasure2, tvMeasure3, tvMeasure4;
    private TextView tvIngredient1, tvIngredient2, tvIngredient3, tvIngredient4;
    private Button backBtn, saveBtn, deleteBtn;
    private SeekBar seekBar;
    private ImageView ivImage;
   // private DrinkModel drink;
    DrinkModel drink = new DrinkModel();
    Intent intent;
    int position = 0;

    private DetailedActivityViewModel vm;

    private static final String TAG = "DetailedActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        Intent intent = getIntent();

        vm = new ViewModelProvider(DetailedActivity.this, new FactoryViewModelDetailedActivity(getApplication())).get(DetailedActivityViewModel.class);


        final Bundle extras = intent.getExtras();

        if(extras != null) {
            position = extras.getInt("position");
            drink = (DrinkModel) extras.getSerializable("drink");
        }

        //Name
        tvName = findViewById(R.id.tvName);
        tvName.setText(drink.getStrDrink());

        //Category
        tvCategory = findViewById(R.id.tvCategory);
        tvCategory.setText(drink.getStrCategory());

        //Instructions
        tvInstructions = findViewById(R.id.tvInstructions);
        tvInstructions.setText(drink.getStrInstructions());

        //Images
        ivImage  = findViewById(R.id.ivImage);
        Glide.with(ivImage.getContext()).load(drink.getStrImageSource() + "/preview").into(ivImage);

        //Measures
        tvMeasure1  = findViewById(R.id.tvMeasure1);
        tvMeasure1.setText(drink.strMeasure1);

        tvMeasure2  = findViewById(R.id.tvMeasure2);
        tvMeasure2.setText(drink.strMeasure2);

        tvMeasure3  = findViewById(R.id.tvMeasure3);
        tvMeasure3.setText(drink.strMeasure3);

        tvMeasure4  = findViewById(R.id.tvMeasure4);
        tvMeasure4.setText(drink.strMeasure4);

        //Ingredients
        tvIngredient1  = findViewById(R.id.tvIngredient1);
        tvIngredient1.setText(drink.strIngredient1);

        tvIngredient2  = findViewById(R.id.tvIngredient2);
        tvIngredient2.setText(drink.strIngredient2);

        tvIngredient3  = findViewById(R.id.tvIngredient3);
        tvIngredient3.setText(drink.strIngredient3);

        tvIngredient4  = findViewById(R.id.tvIngredient4);
        tvIngredient4.setText(drink.strIngredient4);

        //Rating
        tvRating = findViewById(R.id.tvRating);
        double Rating = intent.getDoubleExtra(Constant.RATING, 0);
        tvRating.setText(Double.toString(Rating));

        //Seekbar
        seekBar = findViewById(R.id.seekBar);
        seekBar.setProgress((int)Rating*10);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(final SeekBar seekBar, final int progress,final boolean b) {
                double decimal =  (double)progress/10;
                tvRating.setText(Double.toString(decimal));
                Log.d(TAG, "Sat the seekbar");
            }
            @Override
            public void onStartTrackingTouch(final SeekBar seekBar) {
                Log.d(TAG, "Tracking Touch");
            }
            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {
                Log.d(TAG, "Stopped Tracking Touch");
            }
        });

        //Buttons
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(view -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(view -> {
           drink.setRating(Double.valueOf(seekBar.getProgress()/10.0));
            final Bundle bundle = new Bundle();
            bundle.putSerializable("drink", drink);
            bundle.putInt("position",position);
            intent.putExtras(bundle);
            setResult(1, intent);
            finish();
        });

        deleteBtn = findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(view -> {
            vm.deleteDrink(drink);
            finish();
        });
    }
}