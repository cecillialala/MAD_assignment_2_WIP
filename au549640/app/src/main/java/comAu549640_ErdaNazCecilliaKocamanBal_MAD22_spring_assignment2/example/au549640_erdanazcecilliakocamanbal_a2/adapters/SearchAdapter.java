package comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.adapters;

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

public class SearchAdapter  extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    MutableLiveData<List<DrinkModel>> searchList;
    ISearchClickListener searchClickListener;

    public SearchAdapter(ISearchClickListener searchClickListener){
        this.searchClickListener = searchClickListener;
        searchList = new MutableLiveData<>();
        searchList.setValue(new ArrayList<>());
    }

    public interface ISearchClickListener {
        void onDrinkClicked(int index);
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_row,parent,false);
        return new SearchViewHolder(view);
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;
        ImageView ivImage;
        ConstraintLayout searchlayout;
        public SearchViewHolder(View view){
            super(view);
            tvName = view.findViewById(R.id.tvName);
            ivImage = view.findViewById(R.id.ivImage);
            searchlayout =view.findViewById(R.id.searchlayout);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            searchClickListener.onDrinkClicked(getAdapterPosition());
        }
    }


    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Glide.with(holder.ivImage.getContext()).load(searchList.getValue().get(position).getStrImageSource() + "/preview").into(holder.ivImage);
        holder.tvName.setText(searchList.getValue().get(position).getStrDrink());
    }

    public DrinkModel getItem(int id){
        return searchList.getValue().get(id);
    }

    @Override
    public int getItemCount() {
        return searchList.getValue().size();
    }

    public void updateDrinks(List<DrinkModel> DrinksList){
        searchList.setValue(DrinksList);
        notifyDataSetChanged();
    }


}
