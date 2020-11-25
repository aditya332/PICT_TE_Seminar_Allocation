package com.somanibrothersservices.pictteseminarallocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button loginbtn;
    Switch mSwitch;
    EditText mRegID, mPassword;
    private int year;
    private String firePass, pass;

    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Calendar calendar = Calendar.getInstance();
        year =calendar.get(Calendar.YEAR);

        progressBar = findViewById(R.id.progressBar);
        loginbtn = findViewById(R.id.loginBtn);

        mSwitch = findViewById(R.id.switch1);
        mRegID = findViewById(R.id.registrationId);
        mPassword = findViewById(R.id.editPassword);

        fStore = FirebaseFirestore.getInstance();

        mSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSwitch.isChecked()){
                    mSwitch.setText("Teacher");
                    mRegID.setHint("Teacher's ID");
                } else{
                    mSwitch.setText("Student");
                    mRegID.setHint("Registration ID");
                }

            }
        });


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String regId = mRegID.getText().toString().trim().toUpperCase();
                pass = mPassword.getText().toString();

                DocumentReference dRef = fStore.document(year+"-"+(year+1-2000)+"/STUDENTS/STUDENTS/"+regId);
                dRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        if(document.exists()){
                            firePass = document.getString("pass");
                            Log.d("TAG", "Document snapshot data: "+ document.getData());
                            if (pass.equals(firePass)) {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Login unsuccessful. Check credentials", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "RegistrationID doesn't exit.", Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "get failed with"+ task.getException());
                        }
                    }
                });



            }
        });




    }
}
