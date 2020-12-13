package com.adil.madad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HospitalActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tv, user;
    ImageView iv;
    Button addDoc, ambulanceRequests, bloodRequests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        toolbar = findViewById(R.id.myAppBar);
        addDoc = findViewById(R.id.AddDoc);
        ambulanceRequests = findViewById(R.id.AmbulanceRequests);
        bloodRequests = findViewById(R.id.BloodRequests);
        user = toolbar.findViewById(R.id.name);
        iv = toolbar.findViewById(R.id.image);
        tv = toolbar.findViewById(R.id.title);
        tv.setText("PIMS Hospital");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HospitalActivity.this, LoginActivity.class));
                finish();
            }
        });

        addDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HospitalActivity.this, AddDoctorActivity.class));
            }
        });

        ambulanceRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HospitalActivity.this, AmbulanceRequestsActivity.class));
            }
        });

        bloodRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HospitalActivity.this, BloodrequestsActivity.class));
            }
        });
    }
}