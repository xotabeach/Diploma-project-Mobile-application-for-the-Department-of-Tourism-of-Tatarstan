package com.example.mobile_tour;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Build;

import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.MotionEvent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;



import com.example.mobile_tour.R;
import com.example.mobile_tour.ui.home.TravelCategory;
import com.example.mobile_tour.ui.home.TravelLocation;
import com.example.mobile_tour.ui.login.LoginActivity;
import com.example.mobile_tour.ui.splash_screen.SplashScreenActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mobile_tour.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;




    protected void hideSystemBars(){
        View decorView = getWindow().getDecorView();


        // Сделаем верхнюю и нижнюю панели прозрачными и растянем экран до границы
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsController controller = decorView.getWindowInsetsController();
            if (controller != null) {
                controller.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS);
                controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
                controller.show(WindowInsets.Type.navigationBars());
            }
        } else {
            flags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        }

        decorView.setSystemUiVisibility(flags);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataBaseHelper dbHelper = new DataBaseHelper(this);

        dbHelper.clearDatabase();


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        BottomNavigationView navView = findViewById(R.id.nav_view);
        //
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        hideSystemBars();




        List<TravelLocation> travelLocations = new ArrayList<>();
        List<TravelCategory> travelCategories = new ArrayList<>();



        TravelLocation travelLocationFamilyCenter = new TravelLocation();
        travelLocationFamilyCenter.imageUrl = R.drawable.family_center_kazan;
        travelLocationFamilyCenter.title = "Центр семьи";
        System.out.println("Вот наша картинка: " + travelLocationFamilyCenter.imageUrl);
        travelLocationFamilyCenter.category = "Архитектура";
        travelLocationFamilyCenter.year = "Год основания 2013";
        travelLocationFamilyCenter.description ="Данное сооружение выполнено в форме чаши – традиционного татарского казана. Тюркское слово «казан» означает котел для приготовления пищи. Такой вид строения выбран неспроста – чаша символизирует домашний очаг и семейный уют, а также ассоциируется с культурой местного народа.\n" +
                "Интересный факт: в день открытия Чаши в ней заключили брак 27 человек в честь 27ми летия Универсиады ";
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
        travelLocationUramPark.category = "Развлечения";
        travelLocationUramPark.year = "Год основания: 2021";
        travelLocationUramPark.description = "Самый большой в России и Европе всесезонный экстрим-парк УРАМ состоит из двух частей – крытой и открытой. На открытой территории площадью 20 000 кв.м. можно заниматься скейтбордингом, BMX Freestyle, паркуром, воркаутом и стритболом. В крытом центре уличной культуры на двух этажах расположились экстрим-зоны (эйр-парк, стрит-плаза, бетонный боул и скейт-дом), а также культурные пространства – центр современной музыки, школа диджеинга, скейт-шоп, зал для танцев, лекторий и кафе. Все спортивные объекты в парке построены в соответствии с международными требованиями безопасности и открыты для всех желающих.";
        travelLocationUramPark.location = "Казань";
        travelLocationUramPark.starRating = 5.0f;
        travelLocations.add(travelLocationUramPark);

        TravelLocation travelLocationMitaka = new TravelLocation();
        travelLocationMitaka.imageUrl = R.drawable.asa_mitaka;
        travelLocationMitaka.title = "Аса Митака";
        travelLocationMitaka.location = "Казань";
        travelLocationMitaka.starRating = 5.0f;
        travelLocations.add(travelLocationMitaka);

        dbHelper.clearDatabase();

        dbHelper.insertTravelLocations(travelLocations);
        dbHelper.displayRowCount();
        dbHelper.displayAllData();

        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_question, R.id.navigation_create_route , R.id.navigation_profile , R.id.navigation_right_bar , R.id.searchView ,R.id.activity_login)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        binding.navView.setPadding(0,0,0,-20);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }



    @Override
    protected void onPause(){
        super.onPause();
        hideSystemBars();
    }


    @Override
    protected void onResume(){
        super.onResume();
        hideSystemBars();
    }

    @Override
    protected void onStop(){
        super.onStop();
        hideSystemBars();
    }
}