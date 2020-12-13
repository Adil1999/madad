package com.adil.madad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;

    Toolbar toolbar;
    TextView user;
    ImageView iv;

    EditText name, email, password, cPassword;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        toolbar = findViewById(R.id.myAppBar);
        iv = toolbar.findViewById(R.id.image);
        user = toolbar.findViewById(R.id.name);
        iv.setVisibility(View.INVISIBLE);
        user.setAlpha(0);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");
        firebaseAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.user_name);
        email = findViewById(R.id.email_address);
        password = findViewById(R.id.user_password);
        cPassword = findViewById(R.id.cn_password);
        signup = findViewById(R.id.sign_up);

        Toast.makeText(SignupActivity.this, "Sign Up Activity", Toast.LENGTH_LONG).show();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.i("IN SIGNUP Activity:", "Sign UP Button");
                final String n = name.getText().toString();
                final String e = email.getText().toString();
                final String pass = password.getText().toString();
                final String cPass = cPassword.getText().toString();

                if(TextUtils.isEmpty(n)){
                    Toast.makeText(SignupActivity.this,"Pleae Enter Name", Toast.LENGTH_LONG).show();
                }
                if(TextUtils.isEmpty(e)){
                    Toast.makeText(SignupActivity.this,"Pleae Enter Email", Toast.LENGTH_LONG).show();
                }
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(SignupActivity.this,"Pleae Enter Password", Toast.LENGTH_LONG).show();
                }
                if(TextUtils.isEmpty(cPass)){
                    Toast.makeText(SignupActivity.this,"Pleae Enter Confirm Password", Toast.LENGTH_LONG).show();
                }

                firebaseAuth.createUserWithEmailAndPassword(e, pass).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Log.i("IN SIGNUP Activity:", "Sign UP Button");
                            firebaseDatabase.getReference("User")
                                    .child(firebaseAuth.getCurrentUser().getUid())
                                    .setValue(new User(n,e,pass,cPass))
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(SignupActivity.this,"You are signed up on madad", Toast.LENGTH_LONG);
                                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                        }
                                    });
                        }
                        else {
                            Log.i("IN SIGNUP Activity:", "Sign UP UnSuccessful");

                        }
                    }
                });
            }
        });

    }
}