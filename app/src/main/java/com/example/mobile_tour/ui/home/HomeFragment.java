package com.example.mobile_tour.ui.home;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mobile_tour.DataBaseHelper;
import com.example.mobile_tour.R;
import com.example.mobile_tour.ui.SharedViewModel;
import com.example.mobile_tour.ui.create_route.Create_routeFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {


    public Context getApplicationContext() {
        throw new RuntimeException("Stub!");
    }





    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ViewPager2 locationsViewPager = root.findViewById(R.id.viewpagerHomeFragment);
        ViewPager2 categoryViewPager = root.findViewById(R.id.viewpagerHomeFragment_category);






        List<TravelLocation> travelLocations = new ArrayList<>();
        List<TravelCategory> travelCategories = new ArrayList<>();

        DataBaseHelper dbHelper = new DataBaseHelper(this.getContext());
        travelLocations = dbHelper.getAllTravelLocations();
        System.out.println("Количество элементов там короче: " + travelLocations.size());



        locationsViewPager.setAdapter(new TravelLocationsAdapter(travelLocations));


        TravelLocationsAdapter adapter = new TravelLocationsAdapter(travelLocations);
        List<TravelLocation> finalTravelLocations = travelLocations;
        adapter.setOnItemClickListener(new TravelLocationsAdapter.OnItemClickListener() {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), "channel_id")
                    .setContentTitle("Заголовок уведомления")
                    .setContentText("Это текст уведомления");

            @Override
            public void onItemClick(int position) {

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screenWidth = displayMetrics.widthPixels;
                int screenHeight = displayMetrics.heightPixels;
                TravelLocation clickedLocation = finalTravelLocations.get(position);



                ClickedLocationDialog dialog = new ClickedLocationDialog(requireContext(), clickedLocation);
                dialog.show();




            }


        });
        locationsViewPager.setAdapter(adapter);


        locationsViewPager.setClipToPadding(false);
        locationsViewPager.setClipChildren(false);
        locationsViewPager.setOffscreenPageLimit(5);
        locationsViewPager.setCurrentItem(2);
        locationsViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);


        TravelCategory travelCategoryParks = new TravelCategory();
        travelCategoryParks.title ="Парки";
        travelCategoryParks.imageResId = R.drawable.park_for_app;
        travelCategories.add(travelCategoryParks);

        TravelCategory travelCategoryCafe = new TravelCategory();
        travelCategoryCafe.title ="Кафе";
        travelCategoryCafe.imageResId = R.drawable.cafe_for_app;
        travelCategories.add(travelCategoryCafe);

        TravelCategory travelCategoryEntertainment = new TravelCategory();
        travelCategoryEntertainment.title ="Развлечения";
        travelCategoryEntertainment.imageResId = R.drawable.entertainment;
        travelCategories.add(travelCategoryEntertainment);

        TravelCategory travelCategoryHotels = new TravelCategory();
        travelCategoryHotels.title ="Отели";
        travelCategoryHotels.imageResId = R.drawable.hotels_for_app;
        travelCategories.add(travelCategoryHotels);


        categoryViewPager.setAdapter(new TravelCategoryAdapter(travelCategories));

        categoryViewPager.setClipToPadding(false);
        categoryViewPager.setClipChildren(false);
        categoryViewPager.setOffscreenPageLimit(3);
        categoryViewPager.setCurrentItem(1);


        CompositePageTransformer categoryPageTransformer = new CompositePageTransformer();
        categoryPageTransformer.addTransformer(new MarginPageTransformer(10) );
        categoryPageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);


                float offsetX = -600 * position;
                page.setTranslationX(offsetX);
                page.setScaleX((float) ((0.8f + r * 0.2f)*0.55));

            }
        });
        locationsViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                View viewDefault = ((RecyclerView) locationsViewPager.getChildAt(0)).getLayoutManager().findViewByPosition(0);
                float defSizeX = viewDefault.getScaleX();
                float defSizeY = viewDefault.getScaleY();
                for (int i = 0; i < locationsViewPager.getAdapter().getItemCount(); i++) {
                    float r = 1 - Math.abs(position);
                    View view = ((RecyclerView) locationsViewPager.getChildAt(0)).getLayoutManager().findViewByPosition(i);
                    if (view != null) {
                        if (i == position) {
                            view.setTranslationZ(150);
                            view.setAlpha(1f);

                        } else {
                            //view.setScaleY(0.8f);
                            view.setTranslationZ(10);
                            view.setAlpha(0.75f);

                        }
                    }
                }
            }
        });

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {

            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                //page.setScaleX((float) ((0.8f + r * 0.2f)*0.8));
                page.setScaleY(0.85f + r * 0.2f);
                float offsetX = -80 * position;
                page.setTranslationX(offsetX);


            }
        });

        categoryViewPager.setPageTransformer(categoryPageTransformer);
        locationsViewPager.setPageTransformer(compositePageTransformer);


        //SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        //sharedViewModel.setTravelLocations(travelLocations);
        HomeViewModel homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        homeViewModel.setTravelLocations(travelLocations);


        return root;
    }
}