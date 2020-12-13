package com.adil.madad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button patientLogin, hospitalLogin;
    TextView user;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.myAppBar);
        user = toolbar.findViewById(R.id.name);
        iv = toolbar.findViewById(R.id.image);

        user.setAlpha(0);
        iv.setVisibility(View.INVISIBLE);

        patientLogin = findViewById(R.id.btn2);
        hospitalLogin = findViewById(R.id.btn3);

        setSupportActionBar(toolbar);

        patientLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        hospitalLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HospitalLoginActivity.class));
            }
        });

    }
}