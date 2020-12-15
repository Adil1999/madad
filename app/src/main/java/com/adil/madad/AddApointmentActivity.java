package com.adil.madad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AddApointmentActivity extends AppCompatActivity {

    FirebaseUser fuser;
    FirebaseDatabase database;
    DatabaseReference reference;

    Toolbar toolbar;
    TextView tv, _user;
    ImageView iv;

    User userData;

    EditText address, number, patient;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apointment);

        toolbar = findViewById(R.id.myAppBar);
        tv = toolbar.findViewById(R.id.title);
        iv = toolbar.findViewById(R.id.image);
        _user = toolbar.findViewById(R.id.name);
        iv.setVisibility(View.GONE);
        _user.setVisibility(View.GONE);
        tv.setText("Add Apointment");

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        patient = findViewById(R.id.patient);
        address = findViewById(R.id.address);
        number = findViewById(R.id.number);
        submit = findViewById(R.id.submit);

        final Intent intent = getIntent();
        final String docName = intent.getStringExtra("doctor");
        Log.d("Doctor is ", docName);


        fuser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");

        get_user();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_address = address.getText().toString().trim();
                String txt_number = number.getText().toString().trim();
                String txt_name = patient.getText().toString().trim();

                if (TextUtils.isEmpty(txt_address)) {
                    Toast.makeText(AddApointmentActivity.this, "Please Enter Address", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(txt_number)) {
                    Toast.makeText(AddApointmentActivity.this, "Please Enter Number", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(txt_name)) {
                    Toast.makeText(AddApointmentActivity.this, "Please Enter Patient's Name", Toast.LENGTH_LONG).show();
                    return;
                }
                addAppointment(txt_name, docName, txt_address);

                AlertDialog.Builder builder = new AlertDialog.Builder(AddApointmentActivity.this);
                View dialogView = LayoutInflater.from(AddApointmentActivity.this).inflate(R.layout.request_dialog, null);
                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.show();
                Button dial = dialogView.findViewById(R.id.btn);
                dial.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AddApointmentActivity.this, MapsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

    }

    public void get_user() {
        reference = database.getReference("Users").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    userData = ds.getValue(User.class);
                    _user.setText(userData.getName());
                    Picasso.get().load(userData.getImg()).fit().centerCrop().into(iv);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddApointmentActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addAppointment(String patient, String doc, String address) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Appointments").push().setValue(
                new Appointment(fuser.getUid(), patient, userData.getNumber(), userData.getImg(), address, doc, "active")
        );
    }
}