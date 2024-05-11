package com.example.mobile_tour.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mobile_tour.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class TravelLocationsAdapter extends RecyclerView.Adapter<TravelLocationsAdapter.TravelLocationViewHolder>{

    private RecyclerView recyclerView;

    OnActivePositionChangedListener activePositionChangedListener;
    private int activePosition = RecyclerView.NO_POSITION;
    private int oldPosition = RecyclerView.NO_POSITION;

    private List<TravelLocation> travelLocations;
    private OnItemClickListener onItemClickListener;



    public TravelLocationsAdapter(List<TravelLocation> travelLocations) {
        this.travelLocations = travelLocations;
    }

    public TravelLocationsAdapter(RecyclerView recyclerView, List<TravelLocation> travelLocations) {
        this.recyclerView = recyclerView;

    }

    public void setActivePosition(int position) {
        oldPosition = activePosition;
        notifyItemChanged(oldPosition);


        activePosition = position;
        if (activePositionChangedListener != null) {
            activePositionChangedListener.onActivePositionChanged(position);
        }
        notifyItemChanged(position);

    }
    public void setOnActivePositionChangedListener(OnActivePositionChangedListener listener) {
        this.activePositionChangedListener = listener;
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
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
    public void onBindViewHolder(@NonNull TravelLocationViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.setLocationData(travelLocations.get(position));


        if (position == activePosition) {
            holder.kbvLocation.resume();
        } else {
            holder.kbvLocation.pause();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }


    public void stopAllKenBurnsViews(ViewPager2 viewPager) {
        for (int i = 0; i < viewPager.getChildCount(); i++) {
            View child = viewPager.getChildAt(i);
            if (child instanceof RecyclerView) {
                RecyclerView recyclerView = (RecyclerView) child;
                RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(i);
                if (holder instanceof TravelLocationViewHolder) {
                    TravelLocationViewHolder viewHolder = (TravelLocationViewHolder) holder;
                    viewHolder.kbvLocation.pause();
                }
            }
        }
    }



    @Override
    public int getItemCount() {

        if (travelLocations != null) {
            return travelLocations.size();
        } else {
            return 0;
        }
    }

    static class TravelLocationViewHolder extends RecyclerView.ViewHolder{

        //private KenBurnsView kbvLocation;
        private KenBurnsView kbvLocation;
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

        void stopKenBurnsView() {
            kbvLocation.pause();
        }

    }
}
