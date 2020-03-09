package com.example.hopinnow.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hopinnow.R;

import java.text.DecimalFormat;
import java.util.Objects;

public class RiderWaitingDriverFragment extends Fragment {
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private Chronometer chronometer;
    private boolean running;
    private Double estimate_fare = 2.68;
    private Double lowest_price = estimate_fare;
    private TextView fare_amount;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_rider_waiting_driver, container, false);
        chronometer = view.findViewById(R.id.chronometer);


        fare_amount = view.findViewById(R.id.fare_amount);
        fare_amount.setText(df2.format(estimate_fare));

        startChronometer();

        Button add_money= view.findViewById(R.id.add_money);
        add_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFare();
            }
        });

        Button reduce_money= view.findViewById(R.id.reduce_money);
        reduce_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reduceFare();
            }
        });

        Button cancel_request= view.findViewById(R.id.cancel_button);
        cancel_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RiderMapActivity) Objects.requireNonNull(getActivity())).cancelRequest();
                endChronometer();
            }
        });


        return view;
    }

    private void addFare(){

        estimate_fare += 1;
        fare_amount.setText(df2.format(estimate_fare));
    }

    private void reduceFare(){
        if(Double.parseDouble(df2.format(estimate_fare)) - 1 >= lowest_price) {
            estimate_fare -= 1;
            fare_amount.setText(df2.format(estimate_fare));
        }
    }

    private void startChronometer(){
        if(!running){
            chronometer.start();
            running = true;
        }
    }

    private void endChronometer(){
        if(running){
            chronometer.stop();
            running = false;
        }
    }
}