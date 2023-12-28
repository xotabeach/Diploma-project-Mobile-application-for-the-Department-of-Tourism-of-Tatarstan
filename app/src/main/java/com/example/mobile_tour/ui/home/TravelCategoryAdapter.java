package com.example.mobile_tour.ui.home;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_tour.R;
import com.flaviofaria.kenburnsview.KenBurnsView;

import java.util.List;

public class TravelCategoryAdapter extends RecyclerView.Adapter<TravelCategoryAdapter.TravelCategoryViewHolder>{

    private List<TravelCategory> travelCategories;

    public TravelCategoryAdapter(List<TravelCategory> travelCategories) {
        this.travelCategories = travelCategories;
    }


    @NonNull
    @Override
    public TravelCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TravelCategoryAdapter.TravelCategoryViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.category_cardview,
                        parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TravelCategoryAdapter.TravelCategoryViewHolder holder, int position) {
        holder.setCategoryData(travelCategories.get(position));
    }

    @Override
    public int getItemCount() {
        return travelCategories.size();
    }

    static class TravelCategoryViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageLocation;
        private TextView textTitle;

        TravelCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageLocation = itemView.findViewById(R.id.kbvLocation);
            textTitle = itemView.findViewById(R.id.textTitle);


        }

        void setCategoryData(TravelCategory travelcategory){
            imageLocation.setImageResource(travelcategory.imageResId);
            textTitle.setText(travelcategory.title);


        }
    }
}
