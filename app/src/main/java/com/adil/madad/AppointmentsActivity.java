package com.adil.madad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentsActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tv, _user;
    RecyclerView rv;

    List<Appointment> appointments;
    AppointmentRvAdapater MyRvAdapter;

    FirebaseUser fuser;
    FirebaseDatabase database;
    DatabaseReference reference;

    User userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        rv = findViewById(R.id.ambulanceReq);
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

        get_user();

        appointments = new ArrayList<>();
        getAppointments();



        MyRvAdapter = new AppointmentRvAdapater(appointments, this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(MyRvAdapter);
    }

    public void getAppointments() {
        appointments = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Appointments");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointments.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Appointment appointment = ds.getValue(Appointment.class);
                    if(appointment.getStatus().equals("active")){
                        appointments.add(appointment);
                    }
                }
                MyRvAdapter = new AppointmentRvAdapater(appointments, AppointmentsActivity.this);
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
                Toast.makeText(AppointmentsActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
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