
package com.somanibrothersservices.pictteseminarallocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.somanibrothersservices.pictteseminarallocation.LoginActivity.REC_ID;
import static com.somanibrothersservices.pictteseminarallocation.LoginActivity.YEAR;

public class TeacherFormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String[] domain;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_form);

        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection(YEAR+"-"+(YEAR+1-2000)+"/DOMAINS/domain_list").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<String> d = new ArrayList<>();
//                            d.add("Select");
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                d.add(documentSnapshot.getId().toString());
                            }
                            domain = d.toArray(new String[0]);

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
        final Spinner dropdown2 = findViewById(R.id.spinner2);
        String item = parent.getItemAtPosition(position).toString();
        if (Arrays.asList(domain).contains(item)) {
            firebaseFirestore.collection(YEAR+"-"+(YEAR+1-2000)+"/DOMAINS/domain_list").document(item).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                String[] sd ;
                                sd = ((List<String>) task.getResult().get("sub_domain_list")).toArray(new String[0]);
                                dropdown2.setVisibility(View.VISIBLE);
                                ArrayAdapter<String> subdom = new ArrayAdapter<String>(TeacherFormActivity.this, android.R.layout.simple_spinner_dropdown_item, sd);
                                dropdown2.setAdapter(subdom);
                            } else {
                                Toast.makeText(TeacherFormActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
//        if(item=="D1") {
//            ArrayAdapter<String> subdom = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sd1);
//            dropdown2.setAdapter(subdom);
//        }
//        else if(item=="D2") {
//            ArrayAdapter<String> subdom = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sd2);
//            dropdown2.setAdapter(subdom);
//        }
//        else{
//            ArrayAdapter<String> subdom = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sd3);
//            dropdown2.setAdapter(subdom);
//        }
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        Toast.makeText(this, "Domain not selected", Toast.LENGTH_LONG).show();
    }

    public void onClickSubmitTeacher(View view) {
        Spinner dropdown1 = findViewById(R.id.spinner1);
        Spinner dropdown2 = findViewById(R.id.spinner2);
        Map<String , Object> domainMap = new HashMap<>();
        domainMap.put("name",dropdown1.getSelectedItem().toString());
        domainMap.put("sub",dropdown2.getSelectedItem().toString());
        List<Map<String , Object>> domainArray = new ArrayList<>();
        domainArray.add(domainMap);
        Map<String , Object> map = new HashMap<>();
        map.put("domain",domainArray);
        firebaseFirestore.collection(YEAR+"-"+(YEAR+1-2000)+"/TEACHERS/TEACHERS").document(REC_ID)
                .update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(TeacherFormActivity.this, "Form Filled :)", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TeacherFormActivity.this , LoginActivity.class));
                } else {
                    Toast.makeText(TeacherFormActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
