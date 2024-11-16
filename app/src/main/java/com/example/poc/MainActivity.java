package com.example.poc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private Button Submit_bt;
    private TextView signup_text;
    private EditText emailInputEditText;
    private EditText editTextPassword;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Submit_bt = findViewById(R.id.Submit_bt);
        signup_text = findViewById(R.id.signup_text);
        emailInputEditText = findViewById(R.id.emailInputEditText);
        editTextPassword = findViewById(R.id.editTextPassword);
        auth = FirebaseAuth.getInstance();
    }

    public void Send_Auth(View view) {
        sign_in();
    }
    public void sign_up()
    {
        auth.createUserWithEmailAndPassword(emailInputEditText.getText().toString() + "@gmail.com", editTextPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            user = auth.getCurrentUser();

                        } else {
                            task.getException().printStackTrace();
                            Toast.makeText(MainActivity.this, "failure", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void sign_in()
    {
        auth.signInWithEmailAndPassword(emailInputEditText.getText().toString() + "@gmail.com", editTextPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Success",Toast.LENGTH_SHORT).show();
                            user = auth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            task.getException().printStackTrace();

                            sign_up();
                        }
                    }
                });
    }

    public void NextActivity(View view) {
        Intent intent = new Intent(this, txt_file_write.class);
        startActivity(intent);
    }
}