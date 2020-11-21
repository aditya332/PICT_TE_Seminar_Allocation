package com.somanibrothersservices.pictteseminarallocation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class TeacherFormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_form);

        String[] domain = {"D1","D2","D3"};

        Spinner dropdown1 = findViewById(R.id.spinner1);

        ArrayAdapter<String> dom = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, domain);
        dropdown1.setAdapter(dom);
        dropdown1.setOnItemSelectedListener(this);

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner dropdown2 = findViewById(R.id.spinner2);
        String[] sd1 = {"D1S1","D1S2","D1S3"};
        String[] sd2 = {"D2S1","D2S2","D2S3"};
        String[] sd3 = {"D3S1","D3S2","D3S3"};
        String item = parent.getItemAtPosition(position).toString();
        if(item=="D1") {
            ArrayAdapter<String> subdom = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sd1);
            dropdown2.setAdapter(subdom);
        }
        else if(item=="D2") {
            ArrayAdapter<String> subdom = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sd2);
            dropdown2.setAdapter(subdom);
        }
        else{
            ArrayAdapter<String> subdom = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sd3);
            dropdown2.setAdapter(subdom);
        }
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        Toast.makeText(this, "Domain not selected", Toast.LENGTH_LONG).show();
    }
}
