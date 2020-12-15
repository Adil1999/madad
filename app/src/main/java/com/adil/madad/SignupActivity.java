package com.adil.madad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class SignupActivity extends AppCompatActivity {

    int PICK_IMAGE = 123;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;

    Toolbar toolbar;
    TextView _user;
    ImageView iv;

    EditText name, email, number, password, cPassword;
    Button signup, login;
    CircleImageView profile;
    Uri imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

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
        email = findViewById(R.id.email_address);
        password = findViewById(R.id.user_password);
        cPassword = findViewById(R.id.cn_password);
        signup = findViewById(R.id.sign_up);
        number = findViewById(R.id.number);
        profile = findViewById(R.id.imag);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image."), PICK_IMAGE);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.i("IN SIGNUP Activity:", "Sign UP Button");
                final String txt_name = name.getText().toString().trim();
                final String txt_email = email.getText().toString().trim();
                final String txt_number = number.getText().toString().trim();
                final String txt_password = password.getText().toString().trim();
                final String txt_cpassword = cPassword.getText().toString().trim();

                if (TextUtils.isEmpty(txt_name)) {
                    Toast.makeText(SignupActivity.this, "Pleae Enter Name", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(txt_email)) {
                    Toast.makeText(SignupActivity.this, "Pleae Enter Email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(txt_number)) {
                    Toast.makeText(SignupActivity.this, "Pleae Enter Number", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(SignupActivity.this, "Pleae Enter Password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(txt_cpassword)) {
                    Toast.makeText(SignupActivity.this, "Pleae Enter Confirm Password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (txt_password.length() < 8) {
                    Toast.makeText(SignupActivity.this, "Password too short", Toast.LENGTH_LONG).show();
                    return;
                }

                if (imagePath != null) {
                    if (txt_password.equals(txt_cpassword)) {
                        registerOnfirebase(txt_email, txt_password, txt_name, txt_number);
                    } else {
                        Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
                        return;
                    }
                } else {
                    Toast.makeText(SignupActivity.this, "Please select picture", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                profile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void registerOnfirebase(final String txt_email, String txt_password, final String txt_name, final String txt_number) {
        firebaseAuth.createUserWithEmailAndPassword(txt_email, txt_password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            user = firebaseAuth.getCurrentUser();
                            database = FirebaseDatabase.getInstance();
                            reference = database.getReference("Users").child(user.getUid());

                            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                            storageReference = storageReference.child(user.getUid());
                            storageReference.putFile(imagePath)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                                            task
                                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            String img = uri.toString();
                                                            reference.push().setValue(
                                                                    new User(user.getUid(), txt_name, txt_email, img, "", false, -1.0, -1.0, txt_number)
                                                            );
                                                            Intent intent = new Intent(SignupActivity.this, MapsActivity.class);
                                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    });
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(
                                                    SignupActivity.this,
                                                    "Failed to Upload Image",
                                                    Toast.LENGTH_LONG
                                            ).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(SignupActivity.this, "Authentication Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}