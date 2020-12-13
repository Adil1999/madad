package com.adil.madad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public class AmbulanceRequestsActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tv, user;
    ImageView iv;
    RecyclerView rv;

    List<AmbulanceRequest> requests;
    AmbulanceRvAdapter MyRvAdapter;

//    Runnable r = new Runnable() {
//        @Override
//        public void run(){
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            ViewGroup viewGroup = findViewById(android.R.id.content);
//            View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.custom_dialog, viewGroup, false);
//            builder.setView(dialogView);
//            AlertDialog alertDialog = builder.create();
//            alertDialog.show();
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_requests);

        rv = findViewById(R.id.ambulanceReq);
        toolbar = findViewById(R.id.myAppBar);
        user = toolbar.findViewById(R.id.name);
        iv = toolbar.findViewById(R.id.image);
        tv = toolbar.findViewById(R.id.title);
        tv.setText("PIMS Hospital");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AmbulanceRequestsActivity.this, HospitalActivity.class));
                finish();
            }
        });
        requests = new ArrayList<>();

        requests.add(new AmbulanceRequest("Adil Alam","1","","", "PIMS Hospital", "03143971614"));
        requests.add(new AmbulanceRequest("Muzamil Hussain","1","","", "Al-Shifa Hospital", "03063364241"));
        requests.add(new AmbulanceRequest("Haysam Bin Tahir","1","","", "Maroof Hospital", "+92143971614"));
        requests.add(new AmbulanceRequest("Muhammad Ashjaeen","1","","", "Islamabad Hospital", "03143971614"));
        requests.add(new AmbulanceRequest("Akash Ali","1","","", "Islamabad Hospital", "03083694161"));

        MyRvAdapter = new AmbulanceRvAdapter(requests, this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(MyRvAdapter);
    }

}