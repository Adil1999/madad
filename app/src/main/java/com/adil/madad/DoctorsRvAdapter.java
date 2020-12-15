package com.adil.madad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorsRvAdapter extends RecyclerView.Adapter<DoctorsRvAdapter.MyViewHolder> {
    List<Doctors> ls;
    Context c;
    boolean isPatient;

    User userData;
    FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;

    DoctorsRvAdapter(List ls, Context c, boolean isPatient) {
        this.ls = ls;
        this.c = c;
        this.isPatient = isPatient;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(c).inflate(R.layout.doctors, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.name.setText(ls.get(position).getName());
        holder.hospital.setText(ls.get(position).getHospital());
        holder.timings.setText(ls.get(position).getTimmings());
        holder.area.setText(ls.get(position).getArea());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, AddApointmentActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("doctor", ls.get(position).getName());
                intent.putExtra("hospital", ls.get(position).getHospital());
                c.startActivity(intent);
            }
        });
//        if (isPatient) {
//            holder.add.setVisibility(View.VISIBLE);
//            holder.add.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    holder.add.setVisibility(View.GONE);
//                    Intent intent = new Intent(c, AddApointmentActivity.class);
//                    intent.putExtra("doctor", ls.get(position).getName());
//                    c.startActivity(intent);
//                }
//            });
//        }

    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, hospital, area, timings;
        Button add;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            hospital = itemView.findViewById(R.id.address);
            area = itemView.findViewById(R.id.area);
            timings = itemView.findViewById(R.id.timings);
        }
    }

}
