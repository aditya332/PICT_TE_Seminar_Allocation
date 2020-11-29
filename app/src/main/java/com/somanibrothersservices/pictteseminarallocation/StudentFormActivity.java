package com.somanibrothersservices.pictteseminarallocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import static com.somanibrothersservices.pictteseminarallocation.LoginActivity.YEAR;

public class StudentFormActivity extends AppCompatActivity {
    String[] domain;
    String[] subDomain1 = {"D1S1","D1S2","D1S3"};
    String[] subDomain2 = {"D2S1","D2S2","D2S3"};
    String[] subDomain3 = {"D3S1","D3S2","D3S3"};
    ArrayAdapter<String> adapterDomain=null;
    ArrayAdapter<String> adapterSubDomain=null;
    AutoCompleteTextView domainTextView = null;
    AutoCompleteTextView subDomainTextView = null;
    TextView name = null;
    TextView emailID = null;

    public void submit(View view)
    {
        boolean checkAutoComplete=false;
        if(name.getText().toString().isEmpty())
        {
            name.setError("Required!");
        }
        if(emailID.getText().toString().isEmpty())
        {
            emailID.setError("Required!");
        }
        for(String d:domain) {
            if(domainTextView.getText().toString().equals(d))
            {
                checkAutoComplete=true;
            }
        }
        if(!checkAutoComplete)
        {
            domainTextView.setError("Not Available!");
        }
        checkAutoComplete=false;
        for(String d:subDomain1)
        {
            if(subDomainTextView.getText().toString().equals(d))
            {
                checkAutoComplete=true;
            }
        }
        for(String d:subDomain2)
        {
            if(subDomainTextView.getText().toString().equals(d))
            {
                checkAutoComplete=true;
            }
        }
        for(String d:subDomain3)
        {
            if(subDomainTextView.getText().toString().equals(d))
            {
                checkAutoComplete=true;
            }
        }
        if(!checkAutoComplete)
        {
            subDomainTextView.setError("Not Available!");
        }
    }
    private void setArrayAdapter(String[] subDomain)
    {
        adapterSubDomain=new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,subDomain);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);

        domainTextView =  (AutoCompleteTextView)findViewById(R.id.domainAutoCompleteTextView);
        subDomainTextView =  (AutoCompleteTextView)findViewById(R.id.subDomainAutoCompleteTextView);
        name = (TextView)findViewById(R.id.nameEditTextView);
        emailID = (TextView)findViewById(R.id.emailIDEditTextView);

        domainTextView.setAdapter(adapterDomain);

        domainTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(domainTextView.getText().toString().equals("D1"))
                {
                    setArrayAdapter(subDomain1);
                }
                else if(domainTextView.getText().toString().equals("D2"))
                {
                    setArrayAdapter(subDomain2);
                }
                else if(domainTextView.getText().toString().equals("D3"))
                {
                    setArrayAdapter(subDomain3);
                }
                subDomainTextView.setAdapter(adapterSubDomain);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        FirebaseFirestore.getInstance().collection(YEAR+"-"+(YEAR+1-2000)).document("DOMAINS").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            domain = ((List<String>) task.getResult().get("domain_list")).toArray(new String[0]);
                            adapterDomain = new ArrayAdapter<String>
                                    (StudentFormActivity.this,android.R.layout.select_dialog_item,domain);
                        } else {
                            Toast.makeText(StudentFormActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}
