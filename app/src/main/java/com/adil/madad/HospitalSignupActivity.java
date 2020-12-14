package com.adil.madad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HospitalSignupActivity extends AppCompatActivity {

    int PICK_IMAGE = 123;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;

    Toolbar toolbar;
    TextView _user;
    ImageView iv;

    EditText name, email, password, cPassword, city;
    Button signup, login;
    CircleImageView profile;
    Uri imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_signup);

        toolbar = findViewById(R.id.myAppBar);
        iv = toolbar.findViewById(R.id.image);
        _user = toolbar.findViewById(R.id.name);
        iv.setVisibility(View.GONE);
        _user.setVisibility(View.GONE);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.user_name);
        city = findViewById(R.id.cityName);
        email = findViewById(R.id.user_email);
        password = findViewById(R.id.password);
        cPassword = findViewById(R.id.cn_password);
        signup = findViewById(R.id.register);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.i("IN SIGNUP Activity:", "Sign UP Button");
                final String txt_name = name.getText().toString().trim();
                final String txt_email = email.getText().toString().trim();
                final String txt_address = city.getText().toString().trim();
                final String txt_password = password.getText().toString().trim();
                final String txt_cpassword = cPassword.getText().toString().trim();

                if (TextUtils.isEmpty(txt_name)) {
                    Toast.makeText(HospitalSignupActivity.this, "Pleae Enter Name", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(txt_address)) {
                    Toast.makeText(HospitalSignupActivity.this, "Pleae Enter Your Address", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(txt_email)) {
                    Toast.makeText(HospitalSignupActivity.this, "Pleae Enter Email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(HospitalSignupActivity.this, "Pleae Enter Password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(txt_cpassword)) {
                    Toast.makeText(HospitalSignupActivity.this, "Pleae Enter Confirm Password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (txt_password.length() < 8) {
                    Toast.makeText(HospitalSignupActivity.this, "Password too short", Toast.LENGTH_LONG).show();
                    return;
                }
                if (txt_password.equals(txt_cpassword)) {
                    LatLng latLng = getPoints(txt_address);
                    Log.d("Points = ", latLng.toString());
                    registerOnfirebase(txt_email, txt_password, txt_name, txt_address, latLng.latitude, latLng.longitude);
                } else {
                    Toast.makeText(HospitalSignupActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
                    return;
                }

            }
        });

    }

    private void registerOnfirebase(final String txt_email, String txt_password, final String txt_name, final String address, final double lat, final double lang) {
        firebaseAuth.createUserWithEmailAndPassword(txt_email, txt_password)
                .addOnCompleteListener(HospitalSignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user = firebaseAuth.getCurrentUser();
                            database = FirebaseDatabase.getInstance();
                            reference = database.getReference("Users").child(user.getUid());
                            reference.push().setValue(new User(user.getUid(), txt_name, txt_email, "", address, true, lang, lat));
                            Intent intent = new Intent(HospitalSignupActivity.this, HospitalActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(HospitalSignupActivity.this, "Authentication Failed", Toast.LENGTH_LONG).show();
                        }
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

}