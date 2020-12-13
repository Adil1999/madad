package com.adil.madad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;

    private GoogleMap mMap;
    Toolbar toolbar;
    TextView _user;
    ImageView iv;

    DrawerLayout dl;
    ActionBarDrawerToggle t;
    NavigationView nv;

    User userData;

    Button requestBlood, requestAmbulance, findDoc;
    CircleImageView civ;
    TextView nav_email;
    TextView nav_name;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        toolbar = (Toolbar) findViewById(R.id.myAppBar);
        iv = findViewById(R.id.image);
        _user = findViewById(R.id.name);
        dl = (DrawerLayout) findViewById(R.id.nav_drawer);
        nv = (NavigationView) findViewById(R.id.nv);
        View header = nv.getHeaderView(0);
        civ = header.findViewById(R.id.profile);
        nav_email = header.findViewById(R.id.email);
        nav_name = header.findViewById(R.id.name);



        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users").child(user.getUid());

        requestBlood = findViewById(R.id.blood);
        requestAmbulance = findViewById(R.id.ambulance);
        findDoc = findViewById(R.id.doc);

        toolbar.setContentInsetsAbsolute(0,0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        get_user();

        t = new ActionBarDrawerToggle(this, dl, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dl.addDrawerListener(t);
        t.setDrawerIndicatorEnabled(true);
        t.syncState();

        set_navigation();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        requestBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, RequestBloodActivity.class);
                intent.putExtra("UserData", userData);
                startActivity(intent);
            }
        });

        requestAmbulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, RequestAmbulanceActivity.class);
                intent.putExtra("UserData", userData);
                startActivity(intent);
            }
        });

        findDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, FindDocActivity.class);
                intent.putExtra("UserData", userData);
                startActivity(intent);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void get_user() {
        reference = database.getReference("Users").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    userData = ds.getValue(User.class);
                    _user.setText(userData.getName());
                    nav_email.setText(userData.getEmail());
                    nav_name.setText(userData.getName());
                    Picasso.get().load(userData.getImg()).fit().centerCrop().into(iv);
                    Picasso.get().load(userData.getImg()).fit().centerCrop().into(civ);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MapsActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void nav_logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MapsActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        Toast.makeText(MapsActivity.this, "Logout", Toast.LENGTH_SHORT).show();
    }

    public void set_navigation() {
        nv.setItemIconTintList(null);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_logout:
                        nav_logout();
                        break;
                    default:
                        return true;
                }
                return true;

            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        t.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        t.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }

        return true;
    }
}