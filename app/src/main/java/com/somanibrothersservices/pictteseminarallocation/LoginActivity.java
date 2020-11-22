package com.somanibrothersservices.pictteseminarallocation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button nextbtn, nextbtn1;
    Switch mSwitch;
    EditText mRegID, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progressBar);
        nextbtn = findViewById(R.id.nextBtn);
        nextbtn1 = findViewById(R.id.nextBtn1);
        mSwitch = findViewById(R.id.switch1);
        mRegID = findViewById(R.id.registrationId);
        mPassword = findViewById(R.id.editPassword);

        mSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSwitch.isChecked()){
                    mSwitch.setText("Teacher");
                } else{
                    mSwitch.setText("Student");
                }

            }
        });

        nextbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSwitch.setVisibility(View.INVISIBLE);
                nextbtn1.setVisibility(View.INVISIBLE);
                mRegID.setVisibility(View.VISIBLE);
                mPassword.setVisibility(View.VISIBLE);
                nextbtn.setVisibility(View.VISIBLE);
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
            }
        });


    }
}
