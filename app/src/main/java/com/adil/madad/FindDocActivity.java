package com.adil.madad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FindDocActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tv, _user;
    ImageView iv;
    RecyclerView rv;

    List<String> categories;
    List<Doctors> doctors;
    List<User> hospitals;
    CategoriesRvAdapter MyRvAdapter;

    FirebaseUser fuser;
    FirebaseDatabase database;
    DatabaseReference reference;

    User userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doc);

        rv = findViewById(R.id.categories);
        toolbar = findViewById(R.id.myAppBar);
        tv = toolbar.findViewById(R.id.title);
        iv = toolbar.findViewById(R.id.image);
        _user = toolbar.findViewById(R.id.name);
        tv.setText("Find a Doctor");

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userData = (User) getIntent().getSerializableExtra("UserData"); //Obtaining data
        }
        _user.setText(userData.getName());
        Picasso.get().load(userData.getImg()).fit().centerCrop().into(iv);

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();

        categories = new ArrayList<>();
        categories.add("Neurologist");
        categories.add("Dermatologist");
        categories.add("Pediatrician");
        categories.add("Endocrinologist");
        categories.add("Neurologist");
        categories.add("Cardiologist");
        categories.add("Physician");
        categories.add("Nephnrologist");

        MyRvAdapter = new CategoriesRvAdapter(categories,doctors, this);
        RecyclerView.LayoutManager lm = new GridLayoutManager(this, 2);
        rv.setLayoutManager(lm);
        rv.setAdapter(MyRvAdapter);
    }

    private void get_Hospitals(){
        hospitals = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hospitals.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User hospital = ds.getValue(User.class);
                    if("true".equals(hospital.getHospital().toString())){
                        hospitals.add(hospital);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                MyRvAdapter = new CategoriesRvAdapter(categories, doctors, FindDocActivity.this);
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
                Toast.makeText(FindDocActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}