package com.example.mobile_tour.ui;

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
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.mobile_tour.DataBaseHelper;
import com.example.mobile_tour.R;
import com.example.mobile_tour.databinding.FragmentRouteBinding;
import com.example.mobile_tour.ui.home.Landmark;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class RouteFragment extends Fragment {

    private List<Landmark> landmarks;
    private FragmentRouteBinding binding;
    private boolean isHidden = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRouteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Typeface customTypeface = ResourcesCompat.getFont(requireContext(), R.font.gothampro);

        Bundle args = getArguments();

        int costableState = 0;
        String preferCat = args.getString("autoCompleteText");
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

        if(!freeTravelChecked) costableState =1;


        if(preferCat != "" || preferCat != "Например: кафе"){
            landmarks = dataBaseHelper.getLandmarksByCategoryCost(preferCat, costableState);
        }
        else{
            landmarks = dataBaseHelper.getLandmarksByCostable(costableState);
        }

        int numOfLandmarks = Math.min(landmarks.size(), minThumbValue + new Random().nextInt(maxThumbValue - minThumbValue + 1));
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
                    for (int i = 2; i < numOfLandmarks * 2 - 2; i++) {
                        View view = stationsLayout.getChildAt(i);
                        if (view != null) {
                            view.setVisibility(isHidden ? View.GONE : View.VISIBLE);
                        }
                    }
                    TextView firstDotText = (TextView) stationsLayout.getChildAt(1).findViewById(R.id.dotText);
                    firstDotText.setText(isHidden ? "2 мин" : "10 мин");
                    showMoreText.setText(isHidden ? "Показать все" : "Скрыть");



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
}
