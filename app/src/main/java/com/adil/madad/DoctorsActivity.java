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

class Doctors {
    String name, hospital, timmings;
    Doctors(String n, String h, String t){
        this.name = n;
        this.hospital = h;
        this.timmings = t;
    }
}


public class DoctorsActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tv, user;
    ImageView iv;

    RecyclerView rv;

    List<Doctors> docs;
    DoctorsRvAdapter MyRvAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);

        rv =  findViewById(R.id.docs);
        toolbar = findViewById(R.id.myAppBar);
        tv = toolbar.findViewById(R.id.title);
        iv = toolbar.findViewById(R.id.image);
        user = toolbar.findViewById(R.id.name);
        //iv.setVisibility(View.INVISIBLE);
        //user.setAlpha(0);
        tv.setText("Neurologists");

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorsActivity.this, FindDocActivity.class));
                finish();
            }
        });

        docs = new ArrayList<>();

        docs.add(new Doctors("DR Ahmed", "PIMS Hospital", "11:00 AM to 05:00 PM"));
        docs.add(new Doctors("DR Shagufta", "Islamabad Hospital", "11:00 AM to 05:00 PM"));
        docs.add(new Doctors("DR Akash", "PIMS Hospital", "11:00 AM to 05:00 PM"));
        docs.add(new Doctors("DR Naeem", "Al-Shifa Hospita;", "11:00 AM to 05:00 PM"));
        docs.add(new Doctors("DR Mushtaq", "Medicare Hospital", "11:00 AM to 05:00 PM"));

        MyRvAdapter = new DoctorsRvAdapter(docs, this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(MyRvAdapter);




    }
}