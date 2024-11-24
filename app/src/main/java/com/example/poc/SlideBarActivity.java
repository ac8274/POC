package com.example.poc;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class SlideBarActivity extends AppCompatActivity {
    static TextView SlideBarOther;
    static SeekBar seekBar;
    int lastProgress=0;
    static boolean messageRecived = false;
    Thread listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_bar);
        SlideBarOther = findViewById(R.id.SlideBarOther);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(seek);
        listener = new Thread(); // don't forget to start new runnable
    }


    private OnSeekBarChangeListener seek = new OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                if (!messageRecived) {
                    seekBar.setProgress(lastProgress);
                } else {
                    seekBar.setProgress(progress);
                }
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            lastProgress = seekBar.getProgress();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            messageRecived = true;
        }
    };
    public static int GetProgress()
    {
        return seekBar.getProgress();
    }
    public static void OtherUser(String score)
    {
        SlideBarOther.setText(score);
    }
}