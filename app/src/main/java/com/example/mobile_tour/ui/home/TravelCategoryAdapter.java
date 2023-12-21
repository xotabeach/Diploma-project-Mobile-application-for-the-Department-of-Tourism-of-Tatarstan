package com.example.mobile_tour.ui.home;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TravelCategoryAdapter.TravelCategoryViewHolder holder, int position) {
        holder.setCategoryData(travelCategories.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class TravelCategoryViewHolder extends RecyclerView.ViewHolder{

        private KenBurnsView kbvLocation;
        private TextView textTitle;

        TravelCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            kbvLocation = itemView.findViewById(R.id.kbvLocation);
            textTitle = itemView.findViewById(R.id.textTitle);


        }

        void setCategoryData(TravelCategory travelcategory){
            kbvLocation.setImageResource(travelcategory.imageResId);
            textTitle.setText(travelcategory.title);


        }
    }
}
