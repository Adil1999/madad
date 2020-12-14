package com.adil.madad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class RequestAmbulanceActivity extends AppCompatActivity {

    FirebaseUser fuser;
    DatabaseReference reference;

    Toolbar toolbar;
    TextView tv, _user;
    ImageView iv;

    User userData;

    EditText address, number, secondaryNumber;
    Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_ambulance);

        toolbar = findViewById(R.id.myAppBar);
        tv = toolbar.findViewById(R.id.title);
        iv = toolbar.findViewById(R.id.image);
        _user = toolbar.findViewById(R.id.name);
        iv.setVisibility(View.GONE);
        _user.setVisibility(View.GONE);
        tv.setText("Request Ambulance");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        address = findViewById(R.id.address);
        number = findViewById(R.id.number);
        secondaryNumber = findViewById(R.id.secondaryNumber);
        submit = findViewById(R.id.submit);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userData = (User) getIntent().getSerializableExtra("UserData"); //Obtaining data
        }

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_address = address.getText().toString().trim();
                String txt_number = number.getText().toString().trim();
                String txt_sec = secondaryNumber.getText().toString().trim();

                if (TextUtils.isEmpty(txt_address)) {
                    Toast.makeText(RequestAmbulanceActivity.this, "Pleae Enter Address", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(txt_number)) {
                    Toast.makeText(RequestAmbulanceActivity.this, "Pleae Enter Number", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(txt_sec)) {
                    Toast.makeText(RequestAmbulanceActivity.this, "Pleae Enter Secondary Number", Toast.LENGTH_LONG).show();
                    return;
                }

                confirm_request(txt_address, txt_number, txt_sec);

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.request_dialog, null);
                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.show();
                Button dial = dialogView.findViewById(R.id.btn);
                dial.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }
        });
    }


    private void confirm_request(String addr, String num1, String num2) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("AmbulanceRequests").push().setValue(
                new AmbulanceRequest(userData.getName(), userData.getId(), userData.getImg(), addr, num1, num2,"active")
        );
    }

}