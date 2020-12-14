package com.adil.madad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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

public class BloodrequestsActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tv, _user;
    RecyclerView rv;

    FirebaseUser fuser;
    DatabaseReference reference;


    List<BloodRequest> requests;
    BloodRvAdapter MyRvAdapter;

    User userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloodrequests);

        rv = findViewById(R.id.bloodReq);
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
        reference = FirebaseDatabase.getInstance().getReference("BloodRequests");

        get_user();
        getBloodRequests();

        MyRvAdapter = new BloodRvAdapter(requests, this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(MyRvAdapter);
    }

    public void getBloodRequests() {
        requests = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("BloodRequests");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requests.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    BloodRequest br = ds.getValue(BloodRequest.class);
                    if(br.getStatus().equals("active")){
                        requests.add(br);
                    }
                }
                MyRvAdapter = new BloodRvAdapter(requests, BloodrequestsActivity.this);
                rv.setAdapter(MyRvAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void get_user() {
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
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
                Toast.makeText(BloodrequestsActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}