package com.adil.madad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorsRvAdapter extends RecyclerView.Adapter<DoctorsRvAdapter.MyViewHolder> {
    List<Doctors> ls;
    Context c;

    DoctorsRvAdapter(List ls, Context c){
        this.ls = ls;
        this.c = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(c).inflate(R.layout.doctors, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(ls.get(position).getName());
        holder.hospital.setText(ls.get(position).getHospital());
        holder.timings.setText(ls.get(position).getTimmings());
        holder.area.setText(ls.get(position).getArea());
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, hospital, area, timings;
        Button contactNow;
        public MyViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            hospital = itemView.findViewById(R.id.address);
            area = itemView.findViewById(R.id.area);
            timings = itemView.findViewById(R.id.timings);
            contactNow = itemView.findViewById(R.id.contact);
        }
    }
}
