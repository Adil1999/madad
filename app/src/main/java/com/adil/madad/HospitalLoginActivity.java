package com.adil.madad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HospitalLoginActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView iv;
    TextView user;

    Button login, signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_login);

        toolbar = findViewById(R.id.myAppBar);
        iv = toolbar.findViewById(R.id.image);
        user = toolbar.findViewById(R.id.name);
        iv.setVisibility(View.INVISIBLE);
        user.setAlpha(0);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HospitalLoginActivity.this, MainActivity.class));
                finish();
            }
        });

        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HospitalLoginActivity.this, HospitalActivity.class));
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HospitalLoginActivity.this, HospitalSignupActivity.class));
                finish();
            }
        });
    }
}