package com.adil.madad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AddDoctorActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tv, _user;

    FirebaseUser fuser;
    FirebaseDatabase database;
    DatabaseReference reference;

    User userData;

    EditText name,email,licenseId,number,area,timings;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);

        toolbar = findViewById(R.id.myAppBar);
        _user = toolbar.findViewById(R.id.name);
        _user.setVisibility(View.GONE);
        tv = toolbar.findViewById(R.id.title);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        name = findViewById(R.id.user_name);
        email = findViewById(R.id.user_email);
        number = findViewById(R.id.user_number);
        area = findViewById(R.id.area);
        timings = findViewById(R.id.timings);
        licenseId = findViewById(R.id.licenseID);
        add = findViewById(R.id.user_add);

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();

        get_user();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_name = name.getText().toString().trim();
                String txt_email = email.getText().toString().trim();
                String txt_number = number.getText().toString().trim();
                String txt_license = licenseId.getText().toString().trim();
                String txt_area = area.getText().toString().trim();
                String txt_timings = timings.getText().toString().trim();

                if(TextUtils.isEmpty(txt_name)){
                    Toast.makeText(AddDoctorActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(txt_email)){
                    Toast.makeText(AddDoctorActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(txt_number)){
                    Toast.makeText(AddDoctorActivity.this, "Please Enter Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(txt_license)){
                    Toast.makeText(AddDoctorActivity.this, "Please Enter License Id", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(txt_area)){
                    Toast.makeText(AddDoctorActivity.this, "Please Enter Area of Specialization", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(txt_timings)){
                    Toast.makeText(AddDoctorActivity.this, "Please Enter Timings", Toast.LENGTH_SHORT).show();
                    return;
                }

                add_doctor(txt_name,txt_license,txt_email,txt_number,txt_area,txt_timings);
                onBackPressed();
                Toast.makeText(AddDoctorActivity.this, "Doctor Added", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void add_doctor(String txt_name, String txt_license, String txt_email, String txt_number, String txt_area, String txt_timings) {
        DatabaseReference reference = database.getReference();
        reference.child("Doctors").push().setValue(
                new Doctors(fuser.getUid(), txt_name,txt_license,txt_email,txt_number,txt_area,userData.getName(),txt_timings)
        );
    }

    private void get_user() {
        reference = database.getReference("Users").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    userData = ds.getValue(User.class);
                    tv.setText(userData.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddDoctorActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}