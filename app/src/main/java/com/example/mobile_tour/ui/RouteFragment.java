package com.example.mobile_tour.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.mobile_tour.DataBaseHelper;
import com.example.mobile_tour.R;
import com.example.mobile_tour.databinding.FragmentRouteBinding;
import com.example.mobile_tour.network.OpenRouteServiceAPI;
import com.example.mobile_tour.network.RouteRequest;
import com.example.mobile_tour.network.RouteResponse;
import com.example.mobile_tour.ui.home.Landmark;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import retrofit2.Response;

public class RouteFragment extends Fragment {

    double[][] coordinates;
    private List<Landmark> landmarks;
    private FragmentRouteBinding binding;
    private boolean isHidden = true;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRouteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Typeface customTypeface = ResourcesCompat.getFont(requireContext(), R.font.gothampro);

        Bundle args = getArguments();

        int costableState = 0;
        String preferCat = args.getString("autoCompleteText");
        assert preferCat != null;
        preferCat = preferCat.trim();
        int minThumbValue = args.getInt("leftThumbValue");
        int maxThumbValue = args.getInt("rightThumbValue");
        boolean freeTravelChecked = args.getBoolean("freeTravelChecked");
        boolean transportChecked = args.getBoolean("transportChecked");
        boolean noMoneyMattersChecked = args.getBoolean("noMoneyMattersChecked");

        // Выводим полученные данные в лог для проверки
        Log.d("RouteParams", "Prefer category: " + preferCat);
        Log.d("RouteParams", "Минимум достопримечательностей: " + minThumbValue);
        Log.d("RouteParams", "Максимум достопримечательностей: " + maxThumbValue);
        Log.d("RouteParams", "Free Travel Checked: " + freeTravelChecked);
        Log.d("RouteParams", "Transport Checked: " + transportChecked);
        Log.d("RouteParams", "No Money Matters Checked: " + noMoneyMattersChecked);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(requireContext());

        Random r = new Random();
        if(!freeTravelChecked) costableState =1;
        int numOfLandmarks = minThumbValue + r.nextInt(maxThumbValue - minThumbValue);


        if (!preferCat.equals(""))
            if(!preferCat.equals("Например: кафе"))
                landmarks = dataBaseHelper.getLandmarksByCategoryCost(preferCat, costableState, numOfLandmarks);
        else landmarks = dataBaseHelper.getLandmarksByCostable(costableState, numOfLandmarks);

        coordinates = new double[landmarks.size()][2];
        for (Landmark landmark : landmarks) {

            coordinates[landmarks.indexOf(landmark)][0] = landmark.getCoordX();
            coordinates[landmarks.indexOf(landmark)][1] = landmark.getCoordY();
            Log.d("LandmarkCoordinates", landmark.getTitle() + " - CoordX: " + coordinates[landmarks.indexOf(landmark)][0] + ", CoordY: " + coordinates[landmarks.indexOf(landmark)][1]);
        }
        getRoute(coordinates);

        Log.d("RouteLandmarks", "Selected landmarks count: " + numOfLandmarks);

        LinearLayout stationsLayout = root.findViewById(R.id.stationsLayout);
        LayoutInflater layoutInflater = LayoutInflater.from(requireContext());

        for (int i = 0; i < numOfLandmarks; i++) {
            Landmark landmark = landmarks.get(i);
            LinearLayout stationCopy = createStationCopy(layoutInflater, stationsLayout, landmark, i, numOfLandmarks);
            stationsLayout.addView(stationCopy);

            LinearLayout dotLayout = (LinearLayout) layoutInflater.inflate(R.layout.station_dot_layout, stationsLayout, false);
            dotLayout.setId(View.generateViewId());
            if (numOfLandmarks > 4 && i == 0) {
                TextView dotText = dotLayout.findViewById(R.id.dotText);
                dotText.setText("2 мин");
            } else if (i == numOfLandmarks - 1) {
                TextView dotText = dotLayout.findViewById(R.id.dotText);
                dotText.setText("1 час");
                dotLayout.setVisibility(View.GONE);
            }
            stationsLayout.addView(dotLayout);
        }

        if (numOfLandmarks > 4) {
            for (int i = 2; i < numOfLandmarks * 2 - 2; i++) {
                View view = stationsLayout.getChildAt(i);
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }

        if (numOfLandmarks > 4) {

            TextView showMoreText = new TextView(requireContext());
            showMoreText.setText("Показать все");
            showMoreText.setTextColor(Color.BLACK);
            showMoreText.setTypeface(customTypeface);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 45);
            params.setMargins(0, 0, 0, 35);
            showMoreText.setLayoutParams(params);
            showMoreText.setGravity(Gravity.CENTER);



            showMoreText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isHidden = !isHidden;

                    // Анимация изменения текста
                    showMoreText.animate().alpha(0.0f).setDuration(300)
                            .withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    showMoreText.setText(isHidden ? "Показать все" : "Скрыть");
                                    showMoreText.animate().alpha(1.0f).setDuration(300);
                                }
                            });

                    // Анимация переключения видимости элементов
                    int delay = 0; // Задержка в миллисекундах для каждого элемента
                    for (int i = 2; i < numOfLandmarks * 2 - 2; i++) {
                        final View view = stationsLayout.getChildAt(i);
                        if (view != null) {
                            view.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    view.setVisibility(isHidden ? View.GONE : View.VISIBLE);
                                    view.animate().alpha(isHidden ? 0.0f : 1.0f).setDuration(500);
                                }
                            }, delay);
                            delay += 55;
                        }
                    }

                    TextView firstDotText = (TextView) stationsLayout.getChildAt(1).findViewById(R.id.dotText);
                    firstDotText.setText(isHidden ? "2 мин" : "10 мин");
                    firstDotText.animate().alpha(1.0f).setDuration(300);
                }
            });


            stationsLayout.addView(showMoreText);
        }


        return root;
    }




    private LinearLayout createStationCopy(LayoutInflater layoutInflater, LinearLayout parentLayout, Landmark landmark, int index, int numOfLandmarks) {
        LinearLayout stationCopy = (LinearLayout) layoutInflater.inflate(R.layout.station_layout, parentLayout, false);
        stationCopy.setId(View.generateViewId());
        ImageView stationIcon = stationCopy.findViewById(R.id.stationIcon1);
        TextView stationName = stationCopy.findViewById(R.id.stationName1);
        stationIcon.setImageResource(R.drawable.circle_point);
        stationName.setText(landmark.getTitle());
        stationCopy.setTag("station_" + index);

        return stationCopy;
    }




    private void getRoute(double[][] coordinates) {
        // Создаем объект Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openrouteservice.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Получаем ключ API
        String apiKey = "5b3ce3597851110001cf6248fa4ef474da3e495da7eddcf7fff4288b";

        // Создаем экземпляр интерфейса OpenRouteServiceAPI
        OpenRouteServiceAPI serviceAPI = retrofit.create(OpenRouteServiceAPI.class);

        // Создаем объект запроса
        RouteRequest request = new RouteRequest(coordinates);

        // Выводим запрос в консоль
        System.out.println("Request URL: " + serviceAPI.getWalkingRoute("Bearer " + apiKey, request).request().url());
        System.out.println("Request Body: " + new Gson().toJson(request));

        // Отправляем запрос с ключом API
        Call<RouteResponse> call = serviceAPI.getWalkingRoute("Bearer " + apiKey, request);
        call.enqueue(new Callback<RouteResponse>() {
            @Override
            public void onResponse(Call<RouteResponse> call, Response<RouteResponse> response) {
                if (response.isSuccessful()) {
                    RouteResponse routeResponse = response.body();
                    if (routeResponse != null) {
                        // Обработка успешного ответа
                    }
                } else {
                    // Обработка неуспешного ответа
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("RouteRequest", "Failed to get route. Error code: " + response.code() + ", Error message: " + errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RouteResponse> call, Throwable t) {
                // Обработка ошибки
                Log.e("RouteRequest", "Failed to get route: " + t.getMessage());
            }
        });
    }
















}
