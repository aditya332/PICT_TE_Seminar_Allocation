package com.somanibrothersservices.pictteseminarallocation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button loginbtn;
    Switch mSwitch;
    EditText mRegID, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progressBar);
        loginbtn = findViewById(R.id.loginBtn);

        mSwitch = findViewById(R.id.switch1);
        mRegID = findViewById(R.id.registrationId);
        mPassword = findViewById(R.id.editPassword);

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
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
