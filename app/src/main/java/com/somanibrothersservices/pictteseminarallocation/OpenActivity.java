package com.somanibrothersservices.pictteseminarallocation;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class OpenActivity extends AppCompatActivity {

    Switch mSwitch;
    Button mNextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        mSwitch = findViewById(R.id.switch1);
        mNextBtn = findViewById(R.id.nextBtn1);

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

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });




    }
}