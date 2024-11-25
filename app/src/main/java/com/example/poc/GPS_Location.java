package com.example.poc;

import android.content.Intent;
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

import com.example.poc.Structures.FireBaseUploader;
import com.example.poc.Structures.GPXparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GPS_Location extends AppCompatActivity {
    int LOCATION_REFRESH_TIME = 1000;
    int LOCATION_REFRESH_DISTANCE = 0;
    int REQUEST_CODE_PERMISSION = 2;
    static boolean first_time = true;
    Button next_button;
    Button gpsStartButton;
    Button gpsEndButton;
    LocationManager locationManager;
    static HashMap<String,Location> two_points;
    Intent intent = null;
    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            if(!first_time)
            {
                two_points.replace("End" , location);
                int i =0;
            }
            else
            {
                two_points.replace("Start",location);
                first_time = false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_location);
        this.next_button = findViewById(R.id.next_button);
        this.gpsStartButton = findViewById(R.id.gps_Tracker_Start_bt);
        this.gpsEndButton = findViewById(R.id.gps_Tracker_End_bt);
        this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        two_points = new HashMap<String,Location>();
        two_points.put("Start",new Location("hello"));
        two_points.put("End",new Location("hello"));

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION" , "android.permission.ACCESS_COARSE_LOCATION"}, REQUEST_CODE_PERMISSION);
        }
        intent = getIntent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        two_points.replace("Start",null);
        two_points.replace("End",null);
        first_time = true;
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
        Toast.makeText(this, "Start Button pressed", Toast.LENGTH_LONG).show();
    }

    public void EndTrack(View view) {
        Toast.makeText(this, "End Button pressed", Toast.LENGTH_LONG).show();
        this.locationManager.removeUpdates(locationListener);
        if(two_points.get("Start") != null && two_points.get("End") != null)
        {
            File file = new File(this.getExternalFilesDir(null),"test_gpx" +".gpx");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                GPXparser parser = new GPXparser(fileOutputStream);
                parser.startWriting();
                parser.addPoint(two_points.get("Start"),"start point");
                parser.addPoint(two_points.get("End"),"end point");
                parser.endWriting();
                fileOutputStream.flush();
                fileOutputStream.close();
                FireBaseUploader.uploadFile(file,intent.getStringExtra("FireBaseUser UID"),".gpx",GPS_Location.this);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void Next_Activity(View view) {
        Intent si = new Intent(this, SlideBarActivity.class);
        startActivity(si);
    }
}