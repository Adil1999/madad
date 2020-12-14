package com.adil.madad;

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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FindDocActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tv, _user;
    ImageView iv;
    RecyclerView rv;

    List<String> categories;
    CategoriesRvAdapter MyRvAdapter;

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


        categories = new ArrayList<>();
        categories.add("Neurologist");
        categories.add("Dermatologist");
        categories.add("Pediatrician");
        categories.add("Endocrinologist");
        categories.add("Neurologist");
        categories.add("Cardiologist");
        categories.add("Physician");
        categories.add("Nephnrologist");

        MyRvAdapter = new CategoriesRvAdapter(categories, this);
        RecyclerView.LayoutManager lm = new GridLayoutManager(this, 2);
        rv.setLayoutManager(lm);
        rv.setAdapter(MyRvAdapter);


    }
}