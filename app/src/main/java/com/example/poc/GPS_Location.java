package com.example.poc;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.CarrierConfigManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GPS_Location extends AppCompatActivity {
    int LOCATION_REFRESH_TIME = 1000;
    int LOCATION_REFRESH_DISTANCE = 0;
    int REQUEST_CODE_PERMISSION = 2;
    static boolean first_time = false;
    Button gpsStartButton;
    Button gpsEndButton;
    LocationManager locationManager;
    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            if(!first_time)
            {

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_location);
        this.gpsStartButton = findViewById(R.id.gps_Tracker_Start_bt);
        this.gpsEndButton = findViewById(R.id.gps_Tracker_End_bt);
        this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION" , "android.permission.ACCESS_COARSE_LOCATION"}, REQUEST_CODE_PERMISSION);
        }
    }


    @Override

    public void onRequestPermissionsResult (int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult (requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults [0] == PackageManager.PERMISSION_GRANTED && grantResults [1] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permission to access external storage granted", Toast.LENGTH_LONG).show();

            }
            else
            {
                Toast.makeText(this, "Permission to access external storage NOT granted", Toast.LENGTH_LONG).show();

            }

        }

    }


    public void StartTrack(View view) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION" , "android.permission.ACCESS_COARSE_LOCATION"}, REQUEST_CODE_PERMISSION);
            return;
        }
        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,LOCATION_REFRESH_TIME,LOCATION_REFRESH_DISTANCE,this.locationListener);
    }

    public void EndTrack(View view) {
    }
}