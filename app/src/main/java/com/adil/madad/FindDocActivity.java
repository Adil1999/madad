package com.adil.madad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FindDocActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tv, _user;
    ImageView iv;
    RecyclerView rv;

    List<String> categories;
    List<Doctors> doctors;
    List<String> hospitals;
    CategoriesRvAdapter MyRvAdapter;

    LatLng userLatlng;

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
            userLatlng = (LatLng) getIntent().getExtras().getParcelable("UserData"); //Obtaining data
        }

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        hospitals = new ArrayList<>();
        doctors = new ArrayList<>();
        categories = new ArrayList<>();

        categories.add("Neurologist");
        categories.add("Dermatologist");
        categories.add("Pediatrician");
        categories.add("Endocrinologist");
        categories.add("Psychiatrist");
        categories.add("Cardiologist");
        categories.add("Physician");
        categories.add("Nephrologist");


        get_user();
        get_NearbyHospitals();
        get_Doctors();

        MyRvAdapter = new CategoriesRvAdapter(categories,doctors, this);
        RecyclerView.LayoutManager lm = new GridLayoutManager(this, 2);
        rv.setLayoutManager(lm);
        rv.setAdapter(MyRvAdapter);
    }

    private void get_NearbyHospitals(){
        hospitals = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hospitals.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (!fuser.getUid().equals(ds.getKey())) {
                        for (DataSnapshot ds2 : ds.getChildren()) {
                            User hospital = ds2.getValue(User.class);
                            if("true".equals(hospital.getHospital().toString())){
                                String address = hospital.getAddress();
                                LatLng hospitalLatlng = getPoints(address);
                                double dist = distance(userLatlng.latitude, userLatlng.longitude, hospitalLatlng.latitude, hospitalLatlng.longitude);
                                if(dist <= 20.00){ //All hospital addresses within distance of 20kms will be added
                                    hospitals.add(hospital.getName());
                                }
                            }
                        }
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
                    //only the doctor from nearby hospitals will be added
                    for (String hosp : hospitals) {
                        if (doctor.getHospital().equals(hosp)) {
                            doctors.add(doctor);
                        }
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
                    _user.setText(userData.getName());
                    Picasso.get().load(userData.getImg()).fit().centerCrop().into(iv);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FindDocActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LatLng getPoints(String strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng latLng = null;
        try {
            address = coder.getFromLocationName(strAddress, 3);

            //check for null
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            //return latLng;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return latLng;
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}