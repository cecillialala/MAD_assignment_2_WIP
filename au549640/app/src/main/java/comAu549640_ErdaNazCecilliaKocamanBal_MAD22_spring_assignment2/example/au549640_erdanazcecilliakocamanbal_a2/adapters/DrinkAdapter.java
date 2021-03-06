package comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.adapters;

//EXPAND FOR SOURCES:
/*Made through watching the demo and it's code from lesson 3 and some lesson 2
* */


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.au549640_erdanazcecilliakocamanbal_a1.R;

import java.util.ArrayList;
import java.util.List;

import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.model.DrinkModel;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {

    MutableLiveData<List<DrinkModel>> drinkslist;
     //List<DrinkModel> drinksList;
     IDrinksClickListener drinksClickListener;

    public DrinkAdapter(IDrinksClickListener DrinksClickListener){
        this.drinksClickListener = DrinksClickListener;
        drinkslist = new MutableLiveData<>();
        drinkslist.setValue(new ArrayList<>());
    }

    public interface IDrinksClickListener{
        void onDrinkClicked(int index);
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drink_row,parent,false);
        return new DrinkViewHolder(view, drinksClickListener);
    }

    public class DrinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName, tvCategory, tvRating;
        ImageView ivImage;
        ConstraintLayout rowLayout;
        private IDrinksClickListener drinksClickListener;
        public DrinkViewHolder(View view, IDrinksClickListener DrinksClickListener){
            super(view);
            drinksClickListener= DrinksClickListener;
            tvName = view.findViewById(R.id.tvName);
            tvCategory = view.findViewById(R.id.tvCategory);
            tvRating = view.findViewById(R.id.tvRating);
            ivImage = view.findViewById(R.id.ivImage);
            rowLayout =view.findViewById(R.id.searchlayout);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            drinksClickListener.onDrinkClicked(getAdapterPosition());
        }
    }

    public DrinkModel getItem(int id){
        return drinkslist.getValue().get(id);
    }


    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position) {
        DrinkModel drink = drinkslist.getValue().get(position);
        Glide.with(holder.ivImage.getContext()).load(drinkslist.getValue().get(position).getStrImageSource() + "/preview").into(holder.ivImage);
        holder.tvRating.setText(Double.toString(drinkslist.getValue().get(position).getRating()));
        holder.tvCategory.setText(drinkslist.getValue().get(position).getStrCategory());
        holder.tvName.setText(drinkslist.getValue().get(position).getStrDrink());
    }

    @Override
    public int getItemCount() {
        return drinkslist.getValue().size();
    }

    public void updateDrinks(List<DrinkModel> DrinksList){
        drinkslist.setValue(DrinksList);
        notifyDataSetChanged();
    }


}
