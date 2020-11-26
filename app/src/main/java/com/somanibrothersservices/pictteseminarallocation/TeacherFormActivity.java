package com.somanibrothersservices.pictteseminarallocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import static com.somanibrothersservices.pictteseminarallocation.LoginActivity.YEAR;

public class TeacherFormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_form);

        FirebaseFirestore.getInstance().collection(YEAR+"-"+(YEAR+1-2000)).document("DOMAINS").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            String[] domain = ((List<String>) task.getResult().get("domain_list")).toArray(new String[0]);

                            Spinner dropdown1 = findViewById(R.id.spinner1);

                            ArrayAdapter<String> dom = new ArrayAdapter<String>(TeacherFormActivity.this, android.R.layout.simple_spinner_dropdown_item, domain);
                            dropdown1.setAdapter(dom);
                            dropdown1.setOnItemSelectedListener(TeacherFormActivity.this);
                        } else {
                            Toast.makeText(TeacherFormActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


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
