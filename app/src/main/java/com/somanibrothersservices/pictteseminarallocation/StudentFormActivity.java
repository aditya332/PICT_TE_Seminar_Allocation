package com.somanibrothersservices.pictteseminarallocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
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

public class StudentFormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] domain;
    String[] subDomain ;
    TextView name = null;
    TextView emailID = null;
    private FirebaseFirestore firebaseFirestore;

    public void submit(View view)
    {
//        boolean checkAutoComplete=false;
        boolean checkTextView=true;
        if(name.getText().toString().isEmpty()) {
            name.setError("Required!");
            checkTextView=false;
        }
        if(emailID.getText().toString().isEmpty()) {
            emailID.setError("Required!");
            checkTextView=false;
        }
//        for(String d:domain)
//        {
//            if(domainTextView.getText().toString().equals(d))
//            {
//                checkAutoComplete=true;
//            }
//        }
//        if(!checkAutoComplete)
//        {
//            domainTextView.setError("Not Available!");
//        }
//        checkAutoComplete=false;
//        for(String d:subDomain)
//        {
//            if(subDomainTextView.getText().toString().equals(d))
//            {
//                checkAutoComplete=true;
//            }
//        }
////        for(String d:subDomain2)
////        {
////            if(subDomainTextView.getText().toString().equals(d))
////            {
////                checkAutoComplete=true;
////            }
////        }
////        for(String d:subDomain3)
////        {
////            if(subDomainTextView.getText().toString().equals(d))
////            {
////                checkAutoComplete=true;
////            }
////        }
//        if(!checkAutoComplete)
//        {
//            subDomainTextView.setError("Not Available!");
//        }
        if(/*checkAutoComplete &&*/ checkTextView) {
            Spinner dropdown1 = findViewById(R.id.spinner3);
            Spinner dropdown2 = findViewById(R.id.spinner4);
            Map<String , Object> map = new HashMap<>();
            map.put("domain" , dropdown1.getSelectedItem().toString());
            map.put("subDomain" , dropdown2.getSelectedItem().toString());
            map.put("assigned" , false);
            firebaseFirestore.collection(YEAR+"-"+(YEAR+1-2000)+"/STUDENTS/STUDENTS").document(REC_ID).update(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(StudentFormActivity.this, "Form filled :)", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(StudentFormActivity.this , LoginActivity.class));
                            } else {
                                Toast.makeText(StudentFormActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
//    private void setArrayAdapter(String[] subDomain)
//    {
//        adapterSubDomain=new ArrayAdapter<String>
//                (this,android.R.layout.select_dialog_item,subDomain);
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);

        name = (TextView)findViewById(R.id.nameEditTextView);
        emailID = (TextView)findViewById(R.id.emailIDEditTextView);

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

                            Spinner dropdown1 = findViewById(R.id.spinner3);

                            ArrayAdapter<String> dom = new ArrayAdapter<String>(StudentFormActivity.this, android.R.layout.simple_spinner_dropdown_item, domain);
                            dropdown1.setAdapter(dom);
                            dropdown1.setOnItemSelectedListener(StudentFormActivity.this);
                        } else {
                            Toast.makeText(StudentFormActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//        firebaseFirestore.collection(YEAR+"-"+(YEAR+1-2000)+"/DOMAINS/domain_list").get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            final ArrayList<String> d = new ArrayList<>();
//                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
//                                d.add(documentSnapshot.getId().toString());
//                            }
//                            domain = d.toArray(new String[0]);
//                            adapterDomain = new ArrayAdapter<String>
//                                    (StudentFormActivity.this,android.R.layout.select_dialog_item,domain);
//                            domainTextView.setAdapter(adapterDomain);
//
//
//                            domainTextView.addTextChangedListener(new TextWatcher() {
//                                @Override
//                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                                }
//
//                                @Override
//                                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                                    if (d.contains(domainTextView.getText().toString())) {
//                                        firebaseFirestore.collection(YEAR+"-"+(YEAR+1-2000)+"/DOMAINS/domain_list")
//                                                .document(domainTextView.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                                if (task.isSuccessful()) {
//                                                    subDomain = ((List<String>) task.getResult().get("sub_domain_list")).toArray(new String[0]);
//                                                    setArrayAdapter(subDomain);
//                                                } else {
//                                                    Toast.makeText(StudentFormActivity.this, task.getException() .getMessage(), Toast.LENGTH_SHORT).show();
//                                                }
//                                            }
//                                        });
//                                    }
////                                    if(domainTextView.getText().toString().equals("D1"))
////                                    {
////                                        setArrayAdapter(subDomain1);
////                                    }
////                                    else if(domainTextView.getText().toString().equals("D2"))
////                                    {
////                                        setArrayAdapter(subDomain2);
////                                    }
////                                    else if(domainTextView.getText().toString().equals("D3"))
////                                    {
////                                        setArrayAdapter(subDomain3);
////                                    }
//                                    subDomainTextView.setAdapter(adapterSubDomain);
//                                }
//                                @Override
//                                public void afterTextChanged(Editable s) {
//                                }
//                            });
//                        } else {
//                            Toast.makeText(StudentFormActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final Spinner dropdown2 = findViewById(R.id.spinner4);
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
                                ArrayAdapter<String> subdom = new ArrayAdapter<String>(StudentFormActivity.this, android.R.layout.simple_spinner_dropdown_item, sd);
                                dropdown2.setAdapter(subdom);
                            } else {
                                Toast.makeText(StudentFormActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
