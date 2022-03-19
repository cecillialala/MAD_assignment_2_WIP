package comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.au549640_erdanazcecilliakocamanbal_a1.R;

import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.model.DrinkModel;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.utils.Constant;

//EXPAND FOR SOURCES:
/*
* Seekbar is made from this: https://abhiandroid.com/ui/seekbar && https://stackoverflow.com/questions/33349424/android-seekbar-changing-the-value
* Buttons from this: https://stackoverflow.com/questions/33962220/how-to-return-seekbar-value-to-previous-activity
* And inspiration from the demos from our lectures lection 2 and 3
* And further help to the seekbar from a classmate named Yuhu.
*/

public class DetailedActivity extends AppCompatActivity {

    private TextView tvName, tvCategory,tvInstructions, tvRating;
    private TextView tvMeasure1, tvMeasure2, tvMeasure3, tvMeasure4;
    private TextView tvIngredient1, tvIngredient2, tvIngredient3, tvIngredient4;
    private Button backBtn, saveBtn;
    private SeekBar seekBar;
    private ImageView ivImage;
    private DrinkModel drink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        Intent intent = getIntent();

        final Bundle extras = intent.getExtras();

        if(extras != null) {
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
        //ivImage.setImageResource(Integer.parseInt(drink.strImageSource));
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
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                double decimal =  (double)progress/10;
                tvRating.setText(Double.toString(decimal));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(DetailedActivity.this, "Something stopped",
                        //Toast.LENGTH_LONG).show();
                //Commented out because it interferes when moving the seekbar
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
            Intent intent1 =new Intent();
            intent1.putExtra(Constant.RATING,Double.valueOf(seekBar.getProgress()));
            setResult(RESULT_OK, intent1);
            finish();
        });
    }
}