package com.example.poc;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.poc.Structures.FireBaseUploader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class txt_file_write extends AppCompatActivity {
    EditText textInput;
    EditText fileName;
    Button submit_bt;
    Button nextBt;
    Intent previous;

    static final int REQUEST_CODE_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_txt_file_write);
        textInput = findViewById(R.id.inputTextEditText);
        submit_bt = findViewById(R.id.textSubmitButton);
        nextBt = findViewById(R.id.gpxFileButton);
        fileName = findViewById(R.id.fileNameEditText);
        previous = getIntent();
        if(previous.getStringExtra("FireBaseUser UID") == null)
        {
            finish();
        }
    }

    public void NextActivity(View view) {
        Intent intent = new Intent(this, GPS_Location.class);
        intent.putExtra("FireBaseUser UID",previous.getStringExtra("FireBaseUser UID"));
        startActivity(intent);
    }

    public void send_text(View view) {
        if(isExternalStorageAvailable()) {
            String data = textInput.getText().toString();
            try {
                File externalFile = new File(this.getExternalFilesDir(null),fileName.getText().toString() +".txt");
                FileWriter writer = new FileWriter (externalFile);
                writer.write(data);
                writer.close();
                FireBaseUploader.uploadFile(externalFile,previous.getStringExtra("FireBaseUser UID"),"txt",txt_file_write.this);
            } catch (IOException e) {
                e.printStackTrace();
                Log.println(Log.INFO,"creating new file","ERROR");
                throw new RuntimeException(e);
            }
        }
        else
        {
            Toast.makeText(txt_file_write.this, "No Media found" ,Toast.LENGTH_SHORT).show();
        }
    }


    public boolean isExternalStorageAvailable() {

        String state = Environment.getExternalStorageState();

        return Environment.MEDIA_MOUNTED.equals(state);

    }


    private void requestPermission ()
    {
        ActivityCompat.requestPermissions(this, new String[]{"android.Manifest.permission.WRITE_EXTERNAL_STORAGE"}, REQUEST_CODE_PERMISSION);
    }
        @Override

        public void onRequestPermissionsResult (int requestCode, String[] permissions, int[] grantResults) {
            super.onRequestPermissionsResult (requestCode, permissions, grantResults);
            if (requestCode == REQUEST_CODE_PERMISSION) {
                if (grantResults.length > 0 && grantResults [0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "Permission to access external storage granted", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(this, "Permission to access external storage NOT granted", Toast.LENGTH_LONG).show();

                }

            }

        }

}