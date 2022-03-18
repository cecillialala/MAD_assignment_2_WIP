package com.example.au549640_erdanazcecilliakocamanbal_a1;

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
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {

     ArrayList<DrinkModel> drinksList;
     IDrinksClickListener drinksClickListener;

    public DrinkAdapter(IDrinksClickListener DrinksClickListener, ArrayList<DrinkModel> DrinksList){
        this.drinksList = DrinksList;
        this.drinksClickListener = DrinksClickListener;
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
            rowLayout =view.findViewById(R.id.rowLayout);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            drinksClickListener.onDrinkClicked(getAdapterPosition());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position) {
        holder.ivImage.setImageResource((Constant.drinksImages[position]));
        holder.tvRating.setText(Double.toString(drinksList.get(position).getRating()));
        holder.tvCategory.setText(drinksList.get(position).category);
        holder.tvName.setText(drinksList.get(position).name);
    }

    @Override
    public int getItemCount() {
        return drinksList.size();
    }

    public void updateDrinks(ArrayList<DrinkModel> DrinksList){
        drinksList = DrinksList;
        notifyDataSetChanged();
    }


}
