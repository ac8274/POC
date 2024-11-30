package com.example.poc;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.poc.CustomViews.Joystick;

public class Joystick_View extends AppCompatActivity {
    private Joystick joystick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick_view);
         joystick = new Joystick(this);
    }
}