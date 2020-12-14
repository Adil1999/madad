package com.adil.madad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestBloodActivity extends AppCompatActivity {

    FirebaseUser fuser;
    DatabaseReference reference;

    Toolbar toolbar;
    TextView tv, _user;
    ImageView iv;

    User userData;

    EditText address, number, secondaryNumber;
    String bloodType = "O+";
    Spinner spinner;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);
        toolbar = findViewById(R.id.myAppBar);
        tv = toolbar.findViewById(R.id.title);
        iv = toolbar.findViewById(R.id.image);
        _user = toolbar.findViewById(R.id.name);
        iv.setVisibility(View.GONE);
        _user.setVisibility(View.GONE);
        tv.setText("Request Blood");

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
        //bloodType = findViewById(R.id.bloodtype);
        submit = findViewById(R.id.submit);
        spinner = findViewById(R.id.spinner1);

        String[] items = new String[]{"O+","A+", "B+", "AB+","O-","A-", "B-", "AB-"};
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(RequestBloodActivity.this,
                android.R.layout.simple_spinner_item,items);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                bloodType = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
                    Toast.makeText(RequestBloodActivity.this, "Please Enter Address", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(txt_number)) {
                    Toast.makeText(RequestBloodActivity.this, "Please Enter Number", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(txt_sec)) {
                    Toast.makeText(RequestBloodActivity.this, "Please Enter Secondary Number", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(txt_sec)) {
                    Toast.makeText(RequestBloodActivity.this, "Please Enter Secondary Number", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(bloodType)) {
                    Toast.makeText(RequestBloodActivity.this, "Pleae Select blood type", Toast.LENGTH_LONG).show();
                    return;
                }


                confirm_request(txt_address, txt_number, txt_sec, bloodType);

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


    private void confirm_request(String addr, String num1, String num2, String bloodType) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("BloodRequests").push().setValue(
                new BloodRequest(userData.getName(), userData.getId(), userData.getImg(), addr, num1, num2, bloodType)
        );
    }
}