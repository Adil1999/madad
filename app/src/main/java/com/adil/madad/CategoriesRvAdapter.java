package com.adil.madad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class CategoriesRvAdapter extends RecyclerView.Adapter<CategoriesRvAdapter.MyViewHolder> {

    public static final int left = 0;
    public static final int right = 1;

    List<String> ls;
    List<Doctors> doctors;
    Context c;

    CategoriesRvAdapter(List ls, List<Doctors> doctors, Context c) {
        this.ls = ls;
        this.c = c;
        this.doctors = doctors;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(c).inflate(R.layout.category_right, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.btn1.setText(ls.get(position));
        holder.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String area = ls.get(position);
                List<Doctors> doc = new ArrayList<>();
                for(Doctors doctor: doctors){
                    if(doctor.getArea().equals(area)){
                        doc.add(doctor);
                        Log.d("Doctor is", doctor.getName());
                    }
                }
                Intent intent = new Intent(v.getRootView().getContext(), DoctorsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("Doctors", (Serializable) doc);
                v.getRootView().getContext().startActivity(intent);
            }
        });
    }




    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button btn1;
        public MyViewHolder(View itemView){
            super(itemView);
            btn1 = itemView.findViewById(R.id.btn1);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(position % 2 == 0){
            return right;
        } else {
            return  left;
        }
    }

    private void get_Doctors(){

    }

}
