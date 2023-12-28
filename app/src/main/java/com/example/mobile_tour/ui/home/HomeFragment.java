package com.example.mobile_tour.ui.home;




import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mobile_tour.R;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ViewPager2 locationsViewPager = root.findViewById(R.id.viewpagerHomeFragment);
        ViewPager2 categoryViewPager = root.findViewById(R.id.viewpagerHomeFragment_category);

        List<TravelLocation> travelLocations = new ArrayList<>();
        List<TravelCategory> travelCategories = new ArrayList<>();

        TravelLocation travelLocationKazanKremlin = new TravelLocation();
        travelLocationKazanKremlin.imageUrl = R.drawable.kazan_kremlin;
        travelLocationKazanKremlin.title = "Казанский Кремль";
        travelLocationKazanKremlin.location = "Казань";
        travelLocationKazanKremlin.starRating = 4.8f;
        travelLocations.add(travelLocationKazanKremlin);

        TravelLocation travelLocationFamilyCenter = new TravelLocation();
        travelLocationFamilyCenter.imageUrl = R.drawable.family_center_kazan;
        travelLocationFamilyCenter.title = "Центр семьи";
        travelLocationFamilyCenter.location = "Казань";
        travelLocationFamilyCenter.starRating = 5.0f;
        travelLocations.add(travelLocationFamilyCenter);

        TravelLocation travelLocationUramPark = new TravelLocation();
        travelLocationUramPark.imageUrl = R.drawable.park_uram;
        travelLocationUramPark.title = "Парк Урам";
        travelLocationUramPark.location = "Казань";
        travelLocationUramPark.starRating = 5.0f;
        travelLocations.add(travelLocationUramPark);

        TravelLocation travelLocationMitaka = new TravelLocation();
        travelLocationMitaka.imageUrl = R.drawable.asa_mitaka;
        travelLocationMitaka.title = "Аса Митака";
        travelLocationMitaka.location = "Казань";
        travelLocationMitaka.starRating = 5.0f;
        travelLocations.add(travelLocationMitaka);

        locationsViewPager.setAdapter(new TravelLocationsAdapter(travelLocations));

        locationsViewPager.setClipToPadding(false);
        locationsViewPager.setClipChildren(false);
        locationsViewPager.setOffscreenPageLimit(3);
        locationsViewPager.setCurrentItem(1);
        locationsViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);


        TravelCategory travelCategoryParks = new TravelCategory();
        travelCategoryParks.title ="Парки";
        travelCategoryParks.imageResId = R.drawable.park_for_app;
        travelCategories.add(travelCategoryParks);

        TravelCategory travelCategoryCafe = new TravelCategory();
        travelCategoryCafe.title ="Кафе";
        travelCategoryCafe.imageResId = R.drawable.cafe_for_app;
        travelCategories.add(travelCategoryCafe);

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


                float offsetX = -400 * position;
                page.setTranslationX(offsetX);
                page.setScaleX((float) ((0.8f + r * 0.2f)*0.5));
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
        return root;
    }
}