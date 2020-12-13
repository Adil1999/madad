package com.adil.madad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class category{
    String cat1, cat2;
    category(String c1, String c2){
        this.cat1 = c1;
        this.cat2 = c2;
    }

    String getCat1(){
        return cat1;
    }

    String getCat2(){
        return cat2;
    }
}


public class FindDocActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tv, user;
    ImageView iv;
    RecyclerView rv;

    List<category> categories;
    CategoriesRvAdapter MyRvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doc);

        rv = findViewById(R.id.categories);
        toolbar = findViewById(R.id.myAppBar);
        tv = toolbar.findViewById(R.id.title);
        iv = toolbar.findViewById(R.id.image);
        user = toolbar.findViewById(R.id.name);
        //iv.setVisibility(View.INVISIBLE);
        //user.setAlpha(0);
        tv.setText("Find a Doctor");

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindDocActivity.this, MapsActivity.class));
                finish();
            }
        });

        categories = new ArrayList<>();
        categories.add(new category("Neurologist","Dermatologist"));
        categories.add(new category("Pediatrician","Endocrinologist"));
        categories.add(new category("Neurologist","Cardiologist"));
        categories.add(new category("Physician","Nephnrologist"));

        MyRvAdapter = new CategoriesRvAdapter(categories, this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(MyRvAdapter);


    }
}