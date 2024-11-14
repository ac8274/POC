package com.example.poc;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
    }

    public void NextActivity(View view) {
    }

    public void send_text(View view) {
        String data = textInput.getText().toString();
        File file = new File(Environment.getExternalStorageDirectory() + "/" + File.separator + fileName.getText().toString() + ".txt");
        try {
            file.createNewFile();
            if(file.exists())
            {
                OutputStream fo = new FileOutputStream(file);
                //write the bytes in file
                fo.write(data.getBytes());
                fo.close();
                uploadData(file);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
                exception.getMessage();
                Toast.makeText(txt_file_write.this, "failure" ,Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
    }

    public void deleteFile()
    {
        File file = new File(Environment.getExternalStorageDirectory() + "/" + File.separator + fileName.getText().toString() + ".txt");
        if(file.exists()) {
            file.delete();
            System.out.println("file deleted");
        }
    }
}