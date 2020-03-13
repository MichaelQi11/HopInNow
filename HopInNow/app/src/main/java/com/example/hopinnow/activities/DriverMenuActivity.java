package com.example.hopinnow.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hopinnow.R;
import com.example.hopinnow.database.DriverDatabaseAccessor;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.User;
import com.example.hopinnow.statuslisteners.DriverProfileStatusListener;
import com.example.hopinnow.statuslisteners.UserProfileStatusListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
/**
 * Author: Peter Zhou
 * This class helps show the driver's menu when clicking the menu button on the top left cornor
 * This class 
 */
public class DriverMenuActivity extends AppCompatActivity implements DriverProfileStatusListener {
    public static final String TAG = "DriverMenuActivity";
    private TextView driverMenuTextView;
    private DriverDatabaseAccessor userDatabaseAccessor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_menu);
        //initialize buttons
        Button driverMyProfileBtn = (Button) findViewById(R.id.driverMyProfileBtn);
        Button driverMyTripsBtn = (Button) findViewById(R.id.driverMyTripsBtn);
        Button vehicleInfoBtn = (Button) findViewById(R.id.vehicleInfoBtn);
        FloatingActionButton offlineBtn = (FloatingActionButton) findViewById(R.id.offlineBtn);
        this.userDatabaseAccessor = new DriverDatabaseAccessor();

        driverMyProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set driver check my profile button listener
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        driverMyTripsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set driver check trip button listener
                Intent intent = new Intent(getApplicationContext(), TripListActivity.class);
                startActivity(intent);
            }
        });
        //
        vehicleInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Car info btn clicked!");
                userDatabaseAccessor.getDriverProfile(DriverMenuActivity.this);
            }
        });
        // set driver offline when click on the offline button
        offlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DriverMapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

    }

    /**
     * when retrieve the driver profile successful,
     * open vehicle view activity to display the car information
     * @param driver
     */
    @Override
    public void onDriverProfileRetrieveSuccess(Driver driver) {
        // when retrieve the driver profile successful,
        // open vehicle view activity to display the car information
        Log.v(TAG, "Driver info retrieved!");
        Intent intent = new Intent(getApplicationContext(),  VehicleViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("DriverObject", driver);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onDriverProfileRetrieveFailure() {

    }

    @Override
    public void onDriverProfileUpdateSuccess(Driver driver) {

    }

    @Override
    public void onDriverProfileUpdateFailure() {

    }
}
