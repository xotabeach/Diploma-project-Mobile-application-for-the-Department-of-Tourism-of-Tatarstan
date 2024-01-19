package com.example.mobile_tour.ui.home;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mobile_tour.R;

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



        TravelLocation travelLocationFamilyCenter = new TravelLocation();
        travelLocationFamilyCenter.imageUrl = R.drawable.family_center_kazan;
        travelLocationFamilyCenter.title = "Центр семьи";
        travelLocationFamilyCenter.location = "Казань";
        travelLocationFamilyCenter.starRating = 5.0f;
        travelLocations.add(travelLocationFamilyCenter);

        TravelLocation travelLocationKazanKremlin = new TravelLocation();
        travelLocationKazanKremlin.imageUrl = R.drawable.kazan_kremlin;
        travelLocationKazanKremlin.title = "Казанский Кремль";
        travelLocationKazanKremlin.category = "Архитектура";
        travelLocationKazanKremlin.year = "Год основания: 1438";
        travelLocationKazanKremlin.description = "Казанский кремль - это историческая крепость и культурный комплекс, расположенный в центре города Казань, Россия. Кремль был построен в 16 веке и имеет большое историческое значение.\n" +
                "\n" +
                "Здесь можно увидеть различные архитектурные памятники, такие как Казанский кафедральный собор, который служил различными религиозными общинами на протяжении столетий, и Кул-Шариф мечеть, построенную в 2005 году и ставшую символом современного Казани.\n" +
                "\n" +
                "Кремль также включает в себя резиденцию Президента Республики Татарстан, музей и различные выставочные залы, представляющие историю и культуру региона.\n" +
                "\n" +
                "Кроме того, кремль находится на берегу реки Казанка и предлагает живописные виды на окружающую местность.";


        travelLocationKazanKremlin.location = "Казань";
        travelLocationKazanKremlin.starRating = 4.8f;
        travelLocations.add(travelLocationKazanKremlin);

        TravelLocation travelLocationRiveraAqua = new TravelLocation();
        travelLocationRiveraAqua.imageUrl = R.drawable.rivera_aqua;
        travelLocationRiveraAqua.title = "Аквапарк \"Ривьера\"";
        travelLocationRiveraAqua.category = "Развлечения";
        travelLocationRiveraAqua.year = "Год основания: 2008";
        travelLocationRiveraAqua.description = "Крупный развлекательный комплекс с аквапарком и бассейнами.\n" +
                "Расположен в центре Казани, на берегу реки Казанки.\n" +
                "Обширная инфраструктура: горки, водные аттракционы, спа-зоны.\n" +
                "Идеальное место для семейного отдыха и активного времяпровождения.\n" +
                "Хостинг различных мероприятий, включая концерты и фестивали.\n" +
                "Аквапарк \"Ривьера\" стал популярным местом для местных жителей и туристов, предлагая увлекательные водные развлечения в самом сердце города.";
        travelLocationRiveraAqua.location = "Казань";
        travelLocationRiveraAqua.starRating = 4.7f;
        travelLocations.add(travelLocationRiveraAqua);

        TravelLocation travelLocationTatarVilage = new TravelLocation();
        travelLocationTatarVilage.imageUrl = R.drawable.tugan_avil;
        travelLocationTatarVilage.title = "Татарская деревня \"Туган Авыл\"";
        travelLocationTatarVilage.category = "Культура";
        travelLocationTatarVilage.year = "Год основания: 2005";
        travelLocationTatarVilage.description = "Татарская деревня \"Туган Авыл\" представляет собой уникальный этнографический комплекс, являющийся отражением богатой культуры и традиций татарского народа. Этот аутентичный комплекс является популярным местом для туристов и жителей города, желающих погрузиться в атмосферу татарской деревни.\n" +
                "\n" +
                "Состоящий из старинных деревенских домов, мастерских и интересных музеев, \"Туган Авыл\" предоставляет посетителям уникальную возможность познакомиться с традиционным татарским образом жизни. Дома представляют собой аутентичные строения, передающие архитектурные особенности татарских поселений.\n" +
                "\n" +
                "Кроме того, в этом этнографическом комплексе проводятся разнообразные мероприятия и фестивали, позволяя посетителям не только увидеть традиции, но и активно участвовать в них. Мастер-классы по традиционным ремеслам, культурные события и выставки создают привлекательную атмосферу для тех, кто стремится глубже понять татарскую культуру.";
        travelLocationTatarVilage.location = "Казань";
        travelLocationTatarVilage.starRating = 4.7f;
        travelLocations.add(travelLocationTatarVilage);

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


        TravelLocationsAdapter adapter = new TravelLocationsAdapter(travelLocations);
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
                TravelLocation clickedLocation = travelLocations.get(position);



                ClickedLocationDialog dialog = new ClickedLocationDialog(requireContext(), clickedLocation);
                dialog.show();



                /*String message = "Название: " + clickedLocation.title + ", Местоположение: " + clickedLocation.location;
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();



                for (int i = 0; i < locationsViewPager.getAdapter().getItemCount(); i++) {
                    float r = 1 - Math.abs(position);
                    View view = ((RecyclerView) locationsViewPager.getChildAt(0)).getLayoutManager().findViewByPosition(i);
                    if (view != null) {
                        if (i == position) {
                            //view.setScaleX((float) (screenWidth*0.8));
                            //view.setScaleY((float) (screenHeight*0.8));


                        } else {


                        }
                    }
                }*/
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
                page.setScaleX((float) ((0.8f + r * 0.2f)*0.6));
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