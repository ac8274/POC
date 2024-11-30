package com.example.poc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.poc.Structures.HotSpot;


public class SlideBarActivity extends AppCompatActivity {
    static TextView SlideBarOther;
    static SeekBar seekBar;
    HotSpot listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_bar);
        SlideBarOther = findViewById(R.id.SlideBarOther);
        seekBar = findViewById(R.id.seekBar);
        listener = new HotSpot("0.0.0.0",4454); // don't forget to start new runnable
        listener.start();
    }

    public static int GetProgress()
    {
        int g=seekBar.getProgress();
        return g;
    }
    public static void OtherUser(String score)
    {
        SlideBarOther.setText(score);
    }

    public void NextActivity(View view) {
        Intent intent = new Intent(this, Joystick_View.class);
        startActivity(intent);
    }
}