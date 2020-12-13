package com.adil.madad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class BloodRequest {
    private String username, address, phno, bloodType;
    BloodRequest(String name, String address, String phno, String bloodType){
        this.username = name;
        this.address = address;
        this.phno = phno;
        this.bloodType = bloodType;
    }

    String getUsername(){
        return username;
    }

    void setUsername(String username){
        this.username = username;
    }

    String getAddress(){
        return address;
    }

    void setAddress(String address){
        this.address = address;
    }

    String getPhno(){
        return phno;
    }

    void setPhno(String phno){
        this.phno = phno;
    }

    String getBloodType(){
        return bloodType;
    }

    void setBloodType(String bloodType){
        this.bloodType = bloodType;
    }
}

public class BloodrequestsActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tv, user;
    ImageView iv;
    RecyclerView rv;

    List<BloodRequest> requests;
    BloodRvAdapter MyRvAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloodrequests);

        rv = findViewById(R.id.bloodReq);
        toolbar = findViewById(R.id.myAppBar);
        user = toolbar.findViewById(R.id.name);
        iv = toolbar.findViewById(R.id.image);
        tv = toolbar.findViewById(R.id.title);
        tv.setText("PIMS Hospital");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BloodrequestsActivity.this, HospitalActivity.class));
                finish();
            }
        });

        requests = new ArrayList<>();

        requests.add(new BloodRequest("Adil Alam", "PIMS Hospital", "03143971614", "O+ve"));
        requests.add(new BloodRequest("Muzamil Hussain", "Al-Shifa Hospital", "03063364241", "AB-ve"));
        requests.add(new BloodRequest("Haysam Bin Tahir", "Maroof Hospital", "+92143971614", "A+ve"));
        requests.add(new BloodRequest("Muhammad Ashjaeen", "Islamabad Hospital", "03143971614", "B-ve"));
        requests.add(new BloodRequest("Akash Ali", "Islamabad Hospital", "03083694161", "AB+ve"));

        MyRvAdapter = new BloodRvAdapter(requests, this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(MyRvAdapter);
    }
}