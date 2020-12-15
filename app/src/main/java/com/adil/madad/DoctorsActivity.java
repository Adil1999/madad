package com.adil.madad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;
import java.util.List;

public class DoctorsActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tv, _user;

    RecyclerView rv;

    List<Doctors> doctors;
    DoctorsRvAdapter MyRvAdapter;

    FirebaseUser fuser;
    FirebaseDatabase database;
    DatabaseReference reference;

    User userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);

        rv = findViewById(R.id.docs);
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

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();

        doctors = new ArrayList<Doctors>();
        get_user();
        Bundle b = getIntent().getExtras();
        if( b!=null ){
            doctors = (ArrayList<Doctors>) getIntent().getSerializableExtra("Doctors");

//            Log.d("Doctor is", doctors.get(0).getName());
//            Log.d("Doctor is", doctors.get(1).getName());


            MyRvAdapter = new DoctorsRvAdapter(doctors, DoctorsActivity.this, true);
            RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
            rv.setLayoutManager(lm);
            rv.setAdapter(MyRvAdapter);
        }
        else {
            get_Doctors();
            MyRvAdapter = new DoctorsRvAdapter(doctors, DoctorsActivity.this, false);
            RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
            rv.setLayoutManager(lm);
            rv.setAdapter(MyRvAdapter);
        }

    }

    private void get_Doctors(){
        doctors = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Doctors");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                doctors.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Doctors doctor = ds.getValue(Doctors.class);
                    if(userData.getName().equals(doctor.getHospital())){
                        doctors.add(doctor);
                    }
                }
                MyRvAdapter = new DoctorsRvAdapter(doctors, DoctorsActivity.this, false);
                rv.setAdapter(MyRvAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                Toast.makeText(DoctorsActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}