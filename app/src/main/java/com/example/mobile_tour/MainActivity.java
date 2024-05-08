package com.example.mobile_tour;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Build;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;



import com.example.mobile_tour.R;
import com.example.mobile_tour.ui.home.TravelCategory;
import com.example.mobile_tour.ui.home.TravelLocation;
import com.example.mobile_tour.ui.login.LoginActivity;
import com.example.mobile_tour.ui.profile.ProfileViewModel;
import com.example.mobile_tour.ui.splash_screen.SplashScreenActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

    boolean key = true;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 1;
    private String name;
    private String password;

    private ProfileViewModel profileViewModel = new ProfileViewModel();
    private String[] profile_data = new String[2];

    private LinearLayout linearLayoutSearch;

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
        //getSupportActionBar().hide();




        //dbHelper.clearDatabase();


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        findViewById(android.R.id.content).setBackgroundColor(Color.WHITE);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        //
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        hideSystemBars();



        dbHelper.clearDatabase();

        List<TravelLocation> travelLocations = new ArrayList<>();
        List<TravelCategory> travelCategories = new ArrayList<>();


        /*


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


        travelLocationKazanKremlin.coordX = 55.797661F;
        travelLocationKazanKremlin.coordY = 49.106815F;
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

        TravelLocation travelLocationParkGorkogo = new TravelLocation();
        travelLocationParkGorkogo.imageUrl = R.drawable.gorki_park;
        travelLocationParkGorkogo.title = "ЦПКО имени Горького";
        travelLocationParkGorkogo.category = "Парки";
        travelLocationParkGorkogo.year = "Год основания: 1928";
        travelLocationParkGorkogo.description = "Крупный парк в Вахитовском районе Казани, один из многих в СССР центральных парков, традиционно названных в честь писателя Максима Горького.На площади перед парком стоит мемориал из монумента «Павшим за советскую власть» (1967 г.) и памятника неизвестному солдату. В парке обитает большое количество серых и рыжих белок.";
        travelLocationParkGorkogo.location = "Казань";
        travelLocationParkGorkogo.starRating = 4.9f;
        travelLocations.add(travelLocationParkGorkogo);


        TravelLocation travelLocationWheel = new TravelLocation();
        travelLocationWheel.imageUrl = R.drawable.wheel;
        travelLocationWheel.title = "Колесо обозрения \"Вокруг света\"";
        travelLocationWheel.category = "Развлечения";
        travelLocationWheel.year = "Год основания: 2016";
        travelLocationWheel.description = "Совсем недавно построенное колесо обозрения высотой в 65 метров находится рядом с аквапарком \"Ривьера\".Всего на колесе 36 кабинок и каждая из них символизирует какую-либо страну. Как снаружи, так и внутри в оформлении можно увидеть достопримечательности и послушать музыку этой страны.";
        travelLocationWheel.location = "Казань";
        travelLocationWheel.starRating = 4.5f;
        travelLocations.add(travelLocationWheel);

        TravelLocation travelLocationAlexPassaj = new TravelLocation();
        travelLocationAlexPassaj.imageUrl = R.drawable.passaj;
        travelLocationAlexPassaj.title = "Александровский пассаж";
        travelLocationAlexPassaj.category = "Архитектура";
        travelLocationAlexPassaj.year = "Год основания: 1880";
        travelLocationAlexPassaj.description = "Это архитектурный памятник более позднего периода истории Казани. Доходный дом в стиле необарокко построили в 1880-х годах по заказу купца Александрова. Но в провинциальной Казани не оказалось столько желающих снимать дорогое жильё. Тогда предприниматель продал здание своей сестре, которая захотела передать его городском музею. Но внутренняя планировка не подходила под музейные нужды. Помещения продолжили сдавать в наём, в том числе и под магазины.";
        travelLocationAlexPassaj.location = "Казань";
        travelLocationAlexPassaj.starRating = 5.0f;
        travelLocations.add(travelLocationAlexPassaj);

        TravelLocation travelLocationDvorecZemledelec = new TravelLocation();
        travelLocationDvorecZemledelec.imageUrl = R.drawable.zemledelec;
        travelLocationDvorecZemledelec.title = "Дворец земледельцев";
        travelLocationDvorecZemledelec.category = "Архитектура";
        travelLocationDvorecZemledelec.year = "Год основания:2010";
        travelLocationDvorecZemledelec.description = "Современная постройка расположена неподалёку от Казанского кремля и набережной реки Казанки. Здание спроектировали для Министерства сельского хозяйства и продовольствия республики Татарстан. Архитектура Дворца землевладельцев напоминает об имперских зданиях западной Европы, но после завершения работ проект вызвал неоднозначную реакцию у общественности. Несмотря это, здание пользуется популярностью у блогеров, которые часто фотографируются у портала главного корпуса, где установлено бронзовое 20-метровое дерево, символизирующее плодородие и процветание казанских земель.";
        travelLocationDvorecZemledelec.location = "Казань";
        travelLocationDvorecZemledelec.starRating = 5.0f;
        travelLocations.add(travelLocationDvorecZemledelec);


        TravelLocation travelLocationBaumanStreet = new TravelLocation();
        travelLocationBaumanStreet.imageUrl = R.drawable.bauman_street;
        travelLocationBaumanStreet.title = "Улица Баумана";
        travelLocationBaumanStreet.category = "Архитектура";
        travelLocationBaumanStreet.year = "Основание: 15 век";
        travelLocationBaumanStreet.description = "Улица Баумана — одна из самых старых улиц Казани. В эпоху Казанского ханства она называлась Ногайской дорогой. В 1552 году, во время штурма Казанского Кремля московскими войсками Ивана Грозного, обе его стены южнее и севернее улицы были проломлены взрывами, а улица была названа сначала Проломной, а затем Большой Проломной. В XVI веке, продолжая уже имевшуюся застройку северной части улицы, в её южной части возникла Новая слобода, получившая позже название Богоявленской по названию сооружённой здесь церкви. В 1930 году, улица была переименована в честь выходца из Казани революционера Баумана. Это название сохранилось до настоящего времени, хотя в постсоветское время предлагалось её переименовать в улицу Шаляпина.";
        travelLocationBaumanStreet.location = "Казань";
        travelLocationBaumanStreet.starRating = 4.8f;
        travelLocations.add(travelLocationBaumanStreet);



        TravelLocation travelLocationKulSharif = new TravelLocation();
        travelLocationKulSharif.imageUrl = R.drawable.kul_sharif;
        travelLocationKulSharif.title = "Кул Шариф";
        travelLocationKulSharif.category = "Архитектура";
        travelLocationKulSharif.year = "Год основания: 2005";
        travelLocationKulSharif.description = "Кул Шариф - главная мечеть татарстана. Она была построена в Казанском кремле в 2005 году, там, где когда-то находилась легендарная соборная мечеть, разрушенная при взятии Казани войсками Ивана Грозного. Мечеть стала одним из главных символов современного мультикультурного Татарстана.";
        travelLocationKulSharif.location = "Казань";
        travelLocationKulSharif.starRating = 4.6f;
        travelLocations.add(travelLocationKulSharif);


        TravelLocation travelLocationKabanLake = new TravelLocation();
        travelLocationKabanLake.imageUrl = R.drawable.kaban_lake;
        travelLocationKabanLake.title = "Набережная озера Кабан";
        travelLocationKabanLake.category = "Парки";
        travelLocationKabanLake.year = "Год основания: 2016";
        travelLocationKabanLake.description = "Набережная озера Кабан — пешеходная набережная в центре Казани.\n" +
                "\n" +
                "Территория имеет длину более 3,5 километров. Вдоль озера высажены 12 500 растений, кустарников и деревьев: ивы, камыш, рогоз, аир, дербенник, черёмуха, яблони, клены, ирга и другие.\n" +
                "\n" +
                "Водные растения в чаше каскада выполняют не только декоративную функцию — они чистят воду.\n" +
                "\n" +
                "На набережной проводят массовые народные гулянья во время празднования Дня республики, Дня города и других событий.";
        travelLocationKabanLake.location = "Казань";
        travelLocationKabanLake.starRating = 4.8f;
        travelLocations.add(travelLocationKabanLake);



        TravelLocation travelLocationParkMillenium = new TravelLocation();
        travelLocationParkMillenium.imageUrl = R.drawable.park_1000;
        travelLocationParkMillenium.title = "Парк Тысячелетия";
        travelLocationParkMillenium.category = "Парки";
        travelLocationParkMillenium.year = "Год основания: 2005";
        travelLocationParkMillenium.description = "Парк Тысячелетия Казани открыли в 2005 году, в честь юбилея города, это один из самых молодых парков в столице Республики Татарстан, его второе название — «Миллениум».  До 2000-х годов на месте будущего парка стояли ветхие дома (малоэтажная застройка), почти каждой весной уровень воды в озере Кабан достигал критических значений, и подтапливал слободу.\n" +
                "\n" +
                "На экскурсии по Казани гиды могут показать архивные фотографии, по которым видно, что в моменты половодья жители даже плавали на лодках по улицам.";
        travelLocationParkMillenium.location = "Казань";
        travelLocationParkMillenium.starRating = 4.7f;
        travelLocations.add(travelLocationParkMillenium);



        TravelLocation travelLocationPeterStreet = new TravelLocation();
        travelLocationPeterStreet.imageUrl = R.drawable.peter_street;
        travelLocationPeterStreet.title = "Петербургская улица";
        travelLocationPeterStreet.category = "Архитектура";
        travelLocationPeterStreet.year = "Год основания: 2005";
        travelLocationPeterStreet.description = "Петербургская улица — частично пешеходная улица в историческом центре Казани, одна из комплексных достопримечательностей города.\n" +
                "\n" +
                "Улица пролегает от центральной городской площади Тукая на северо-западе до перпендикулярной ей улицы Назарбаева на юго-востоке, пересекая улицы Айдинова, Ульянова-Ленина, Волкова, Туфана Миннуллина и Суконная.\n" +
                "\n" +
                "Протяжённость улицы — 1800 м, из них 530 м являются пешеходными.\n" +
                "\n" +
                "Основная архитектурно-художественная идея улицы — создание «уголка Петербурга». Для этого используются стилизованные мостики, ограда и рисунки мощения пешеходной эспланады, символизирующие петербургские каналы, ротонда, скульптурно-монументальные композиции и другие архитектурные приёмы и малые формы.";
        travelLocationPeterStreet.location = "Казань";
        travelLocationPeterStreet.starRating = 4.9f;
        travelLocations.add(travelLocationPeterStreet);


        TravelLocation travelLocationPyramid = new TravelLocation();
        travelLocationPyramid.imageUrl = R.drawable.pyramid;
        travelLocationPyramid.title = "Пирамида";
        travelLocationPyramid.category = "Развлечения";
        travelLocationPyramid.year = "Год основания: 1996";
        travelLocationPyramid.description = "«Пирамида» — культурно-развлекательный комплекс в центре Казани, выполненный в виде пирамиды.\n" +
                "\n" +
                "Это уникальный в России и один из немногих в мире современных крупных объектов подобной формы. Комплекс является одной из современных и знаковых достопримечательностей города как в плане архитектуры, так и в плане функциональности.\n" +
                "\n" +
                "В «Пирамиде» проходят ведомственно-отраслевые конгрессы, конференции, презентации, банкеты и прочие мероприятия.";
        travelLocationPyramid.location = "Казань";
        travelLocationPyramid.starRating = 4.5f;
        travelLocations.add(travelLocationPyramid);


        TravelLocation travelLocationBiblioteka = new TravelLocation();
        travelLocationBiblioteka.imageUrl = R.drawable.biblioteka_nac;
        travelLocationBiblioteka.title = "Парк Тысячелетия";
        travelLocationBiblioteka.category = "Развлечения";
        travelLocationBiblioteka.year = "Год основания: 1865";
        travelLocationBiblioteka.description = "Национальная библиотека Республики Татарстан — главное книгохранилище Татарстана для национальных, республиканских, русских и зарубежных изданий.\n" +
                "\n" +
                "Она содержит более трёх миллионов двухсот тысяч документов, в том числе более 100 тысяч документов на татарском языке и ещё 100 тысяч на иностранных языках.\n" +
                "\n" +
                "Библиотека была официально открыта 10 января 1865 года как городская публичная библиотека Казани.";
        travelLocationBiblioteka.location = "Казань";
        travelLocationBiblioteka.starRating = 4.9f;
        travelLocations.add(travelLocationBiblioteka);



        TravelLocation travelLocationAgafredo = new TravelLocation();
        travelLocationAgafredo.imageUrl = R.drawable.agafredo;
        travelLocationAgafredo.title = "Сеть кафе Агафредо";
        travelLocationAgafredo.category = "Кафе";
        travelLocationAgafredo.year = "Год основания: 2003";
        travelLocationAgafredo.description = "В сети кофеен «Агафредо» теплая\n" +
                "и спокойная атмосфера сочетается\n" +
                "с изысканными сортами чая,\n" +
                "профессионально приготовленным кофе\n" +
                "и превосходной кухней.";
        travelLocationAgafredo.location = "Казань";
        travelLocationAgafredo.starRating = 4.9f;
        travelLocations.add(travelLocationAgafredo);



        TravelLocation travelLocationRannyaPtashka = new TravelLocation();
        travelLocationRannyaPtashka.imageUrl = R.drawable.ptashka;
        travelLocationRannyaPtashka.title = "Кафе Ранняя Пташка";
        travelLocationRannyaPtashka.category = "Кафе";
        travelLocationRannyaPtashka.year = "Год основания: 2019";
        travelLocationRannyaPtashka.description = "«Ранняя пташка» — отличное кафе для завтрака и для работы. Там много пространства и света. Розовая неоновая подсветка, кофе, бейглы и живые цветы делают заведение популярным у казанских девушек.\n" +
                "\n" +
                "Там всегда хороший ассортимент кофе и можно купить зерно. Бариста помогут с выбором и подскажут, каким способом его лучше заваривать.";
        travelLocationRannyaPtashka.location = "Казань";
        travelLocationRannyaPtashka.starRating = 4.6f;
        travelLocations.add(travelLocationRannyaPtashka);



        TravelLocation travelLocationHotelShalyapin = new TravelLocation();
        travelLocationHotelShalyapin.imageUrl = R.drawable.shalyapin;
        travelLocationHotelShalyapin.title = "Отель Шаляпин";
        travelLocationHotelShalyapin.category = "Отели";
        travelLocationHotelShalyapin.year = "Год основания: 2009";
        travelLocationHotelShalyapin.description = "Шаляпин Палас Отель располагается в здании отреставрированного памятника архитектуры 19 века, вблизи Богоявленского Собора, в котором был крещен великий оперный певец – Федор Иванович Шаляпин.\n" +
                "\n" +
                "Отель Шаляпин это 123 уютных номера различной категории, 8 конференц-залов, фитнес-центр с бассейном, ресторан европейской и национальной кухни и лобби-бар.\n" +
                "Удобное  расположение отеля в самом историческом центре города – на пешеходной улице Баумана – является его неоспоримым преимуществом.";
        travelLocationHotelShalyapin.location = "Казань";
        travelLocationHotelShalyapin.starRating = 4.9f;
        travelLocations.add(travelLocationHotelShalyapin);




        TravelLocation travelLocationMitaka = new TravelLocation();
        travelLocationMitaka.imageUrl = R.drawable.asa_mitaka;
        travelLocationMitaka.title = "Аса Митака";
        travelLocationMitaka.location = "Казань";
        travelLocationMitaka.starRating = 5.0f;
        travelLocations.add(travelLocationMitaka);


        */


        TravelCategory travelCategoryParks = new TravelCategory();
        travelCategoryParks.title ="Парки";
        travelCategoryParks.imageResId = R.drawable.park_for_app;
        travelCategories.add(travelCategoryParks);

        TravelCategory travelCategoryCafe = new TravelCategory();
        travelCategoryCafe.title ="Кафе";
        travelCategoryCafe.imageResId = R.drawable.cafe_for_app;
        travelCategories.add(travelCategoryCafe);

        TravelCategory travelCategoryArchitecture = new TravelCategory();
        travelCategoryArchitecture.title ="Архитектура";
        travelCategoryArchitecture.imageResId = R.drawable.castle;
        travelCategories.add(travelCategoryArchitecture);


        TravelCategory travelCategoryEntertainment = new TravelCategory();
        travelCategoryEntertainment.title ="Развлечения";
        travelCategoryEntertainment.imageResId = R.drawable.entertainment;
        travelCategories.add(travelCategoryEntertainment);

        TravelCategory travelCategoryHotels = new TravelCategory();
        travelCategoryHotels.title ="Отели";
        travelCategoryHotels.imageResId = R.drawable.hotels_for_app;
        travelCategories.add(travelCategoryHotels);




        dbHelper.insertTravelLocations(travelLocations);
        dbHelper.displayRowCount();
        dbHelper.displayAllData();


        dbHelper.insertCategories(travelCategories);
        dbHelper.displayCategoryData();


        /*if(key){
            key= false;
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);

        }*/




        Intent sendProfileData = getIntent();
        name = sendProfileData.getStringExtra("name");
        password = sendProfileData.getStringExtra("password");
        profile_data[0] = name;
        profile_data[1] = password;
        System.out.println("МОИ ДАННЫЕ::::: "+name);
        System.out.println("МОИ ДАННЫЕ ПАРОЛЬ::::: "+password);


        profileViewModel.setEmail(name);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;




                AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_question, R.id.navigation_create_route , R.id.navigation_profile , R.id.navigation_right_bar , R.id.searchView ,R.id.activity_login)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        navController.navigate(R.id.navigation_home); // Перенаправление на фрагмент "Home"
                    }
                });
            }
        });
        binding.navView.setPadding(0,0,0,-20);
        //.setupActionBarWithNavController(this, navController, appBarConfiguration);
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

    public String[] getMyName() {return profile_data;}
}