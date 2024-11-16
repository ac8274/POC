package com.example.poc;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class txt_file_write extends AppCompatActivity {
    EditText textInput;
    EditText fileName;
    Button submit_bt;
    Button nextBt;
    FirebaseStorage storage;
    StorageReference rootRef;

    static final int REQUEST_CODE_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_txt_file_write);
        textInput = findViewById(R.id.inputTextEditText);
        submit_bt = findViewById(R.id.textSubmitButton);
        nextBt = findViewById(R.id.gpxFileButton);
        fileName = findViewById(R.id.fileNameEditText);
        storage = FirebaseStorage.getInstance();
//        if(!checkPremission())
//        {
//            requestPermission();
//        }
    }

    public void NextActivity(View view) {
    }

    public void send_text(View view) {
        if(isExternalStorageAvailable()) {
            String data = textInput.getText().toString();
            //File file = new File(Environment.getExternalStorageDirectory() + "/" + File.separator + fileName.getText().toString() + ".txt");
            try {
                File externalFile = new File(this.getExternalFilesDir(null),fileName.getText().toString() +".txt");
                FileWriter writer = new FileWriter (externalFile);
                writer.write(data);
                writer.close();
                uploadData(externalFile);
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


    private boolean checkPremission()
    {
        int result = ContextCompat.checkSelfPermission(this,"android.permission.WRITE_EXTERNAL_STORAGE");
        return result== PackageManager.PERMISSION_GRANTED;
    }

    public boolean isExternalStorageAvailable() {

        String state = Environment.getExternalStorageState();

        return Environment.MEDIA_MOUNTED.equals(state);

    }

    private void uploadData(File file)
    {
        StorageReference ref = storage.getReference().child("txtFiles/"+fileName.getText().toString() + ".txt");
        Uri fileUri = Uri.fromFile(file);
        UploadTask uploadTask = ref.putFile(fileUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.println(Log.INFO,"dataUploadFailure",exception.getMessage().toString());
                Toast.makeText(txt_file_write.this, "failure" ,Toast.LENGTH_SHORT).show();
                deleteFile(file);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Toast.makeText(txt_file_write.this, "Success", Toast.LENGTH_SHORT).show();
                deleteFile(file);
            }
        });
    }

    public void deleteFile(File file)
    {
        if(file.exists()) {
            file.delete();
            System.out.println("file deleted");
        }
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