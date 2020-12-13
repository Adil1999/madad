package com.adil.madad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RequestBloodActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tv, user;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);
        toolbar = findViewById(R.id.myAppBar);
        tv = toolbar.findViewById(R.id.title);
        iv = toolbar.findViewById(R.id.image);
        user = toolbar.findViewById(R.id.name);
        iv.setVisibility(View.INVISIBLE);
        user.setAlpha(0);
        tv.setText("Request Blood");

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RequestBloodActivity.this, MapsActivity.class));
                finish();
            }
        });

    }
}