package com.example.mobile_tour.ui.home;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_tour.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;



public class TravelLocationsAdapter extends RecyclerView.Adapter<TravelLocationsAdapter.TravelLocationViewHolder>{

    private List<TravelLocation> travelLocations;

    public TravelLocationsAdapter(List<TravelLocation> travelLocations) {
        this.travelLocations = travelLocations;
    }

    @NonNull
    @Override
    public TravelLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TravelLocationViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_cardview,
                        parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TravelLocationViewHolder holder, int position) {
        holder.setLocationData(travelLocations.get(position));


    }

    @Override
    public int getItemCount() {
        return travelLocations.size();
    }

    static class TravelLocationViewHolder extends RecyclerView.ViewHolder{

        //private KenBurnsView kbvLocation;
        private ImageView kbvLocation;
        private TextView textTitle, textLocation, textStarRating;

        TravelLocationViewHolder(@NonNull View itemView) {
            super(itemView);
            kbvLocation = itemView.findViewById(R.id.kbvLocation);
            textTitle = itemView.findViewById(R.id.textTitle);
            textLocation = itemView.findViewById(R.id.textLocation);
            textStarRating = itemView.findViewById(R.id.textStarRating);
        }

        void setLocationData(TravelLocation travelLocation){
            //Picasso.get().load(travelLocation.imageUrl).into(kbvLocation);
            kbvLocation.setImageResource(travelLocation.imageUrl);
            textTitle.setText(travelLocation.title);
            textLocation.setText(travelLocation.location);
            textStarRating.setText(String.valueOf(travelLocation.starRating));
        }
    }
}
