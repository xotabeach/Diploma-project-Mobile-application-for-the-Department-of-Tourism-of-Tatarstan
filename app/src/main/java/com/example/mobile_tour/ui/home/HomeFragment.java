package com.example.mobile_tour.ui.home;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mobile_tour.DataBaseHelper;
import com.example.mobile_tour.MainActivity;
import com.example.mobile_tour.R;
import com.example.mobile_tour.ui.SharedViewModel;
import com.example.mobile_tour.ui.create_route.Create_routeFragment;
import com.example.mobile_tour.ui.right_bar.RightBarFragment;
import com.example.mobile_tour.ui.right_bar.RightBarViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class HomeFragment extends Fragment {


    public Context getApplicationContext() {
        throw new RuntimeException("Stub!");
    }


    private void editViewPager(ViewPager2 locationsViewPager) {
        Random ran = new Random();
        locationsViewPager.setClipToPadding(false);
        locationsViewPager.setClipChildren(false);
        locationsViewPager.setOffscreenPageLimit(9);
        locationsViewPager.setCurrentItem(ran.nextInt(9));
        locationsViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
    }
    private void editViewPagerCat(ViewPager2 categoriesViewPager) {
        Random ran = new Random();
        categoriesViewPager.setClipToPadding(false);
        categoriesViewPager.setClipChildren(false);
        categoriesViewPager.setOffscreenPageLimit(5);
        categoriesViewPager.setCurrentItem(ran.nextInt(5));
        categoriesViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
    }






    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {




        View root = inflater.inflate(R.layout.fragment_home, container, false);



        ViewPager2 locationsViewPager = root.findViewById(R.id.viewpagerHomeFragment);
        ViewPager2 categoryViewPager = root.findViewById(R.id.viewpagerHomeFragment_category);


        List<TravelLocation> travelLocations = new ArrayList<>();
        DataBaseHelper dbHelper = new DataBaseHelper(this.getContext());
        dbHelper.displayAllData();
        travelLocations = dbHelper.getAllTravelLocations();
        System.out.println("Количество элементов там короче: " + travelLocations.size());

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;


        //List<TravelCategory> travelCategories = new ArrayList<>();


        int newHeight = (int) (screenHeight * 0.37);
        ViewGroup.LayoutParams params = locationsViewPager.getLayoutParams();
        params.height = newHeight;
        locationsViewPager.setLayoutParams(params);



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


        editViewPager(locationsViewPager);




        List<TravelCategory> travelCategories = dbHelper.getAllCategories();
        System.out.println("А тут сколько????  " + travelCategories.size());



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

        categoryViewPager.setMinimumHeight(120);
        TravelCategoryAdapter categoryAdapter = new TravelCategoryAdapter(travelCategories);
        categoryViewPager.setAdapter(categoryAdapter);

        categoryAdapter.setOnItemClickListener(new TravelCategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {


                if (getActivity() instanceof MainActivity) {

                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);

                    RightBarViewModel rightBarViewModel = new ViewModelProvider(requireActivity()).get(RightBarViewModel.class);

                    String selectedCategory = travelCategories.get(position).getTitle();
                    List<Landmark> landmarks = dbHelper.getLandmarksByCategory(selectedCategory);

                    // Обновление данных в RightBarViewModel
                    rightBarViewModel.setSelectedCategory(selectedCategory);
                    rightBarViewModel.setLandmarks(landmarks);

                    // Выполняем запрос к БД и получаем список landmarks для выбранной категории

                    System.out.println("Количество в категории: " + landmarks.size());


                    //rightBarFragment.setupLandmarksData(landmarks);
                    navController.popBackStack();

                    navController.navigate(R.id.navigation_right_bar);
                }

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

    @Override
    public void onResume() {
        super.onResume();
        DataBaseHelper dbHelper = new DataBaseHelper(this.getContext());
        dbHelper.displayAllData();
        LinearLayout linearLayoutSearch = requireActivity().findViewById(R.id.linearLayoutSearch);
        linearLayoutSearch.setVisibility(View.VISIBLE);
        /*ViewPager2 locationsViewPager = getView().findViewById(R.id.viewpagerHomeFragment);
        ViewPager2 categoriesViewPager = getView().findViewById(R.id.viewpagerHomeFragment_category);
        // Вызовите метод настройки еще раз

        List<TravelLocation> travelLocations = new ArrayList<>();

        travelLocations = dbHelper.getAllTravelLocations();
        List<TravelCategory> travelCategories = dbHelper.getAllCategories();
        System.out.println("А тут сколько????  " + travelCategories.size());
        System.out.println("Количество элементов там короче: " + travelLocations.size());
        editViewPager(locationsViewPager);
        editViewPagerCat(categoriesViewPager);
        */

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LinearLayout linearLayoutSearch = requireActivity().findViewById(R.id.linearLayoutSearch);
        linearLayoutSearch.setVisibility(View.GONE);
    }
}