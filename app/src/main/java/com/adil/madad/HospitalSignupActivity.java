package com.adil.madad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HospitalSignupActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView user;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_signup);

        toolbar = findViewById(R.id.myAppBar);
        iv = toolbar.findViewById(R.id.image);
        user = toolbar.findViewById(R.id.name);
        iv.setVisibility(View.INVISIBLE);
        user.setAlpha(0);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HospitalSignupActivity.this, HospitalLoginActivity.class));
                finish();
            }
        });
    }
}