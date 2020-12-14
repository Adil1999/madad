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

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class AppointmentRvAdapater extends RecyclerView.Adapter<AppointmentRvAdapater.MyViewHolder> {
    private List<Appointment> ls;
    Context c;
    Runnable r;
    View.OnClickListener mMyOnClickListener;

    public AppointmentRvAdapater(List<Appointment> ls, Context c) {
        this.ls = ls;
        this.c = c;
    }

    @NonNull
    @Override
    public AppointmentRvAdapater.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(c).inflate(R.layout.appointment_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentRvAdapater.MyViewHolder holder, final int position) {
        holder.name.setText(ls.get(position).getName());
        holder.number.setText(ls.get(position).getNumber());
        holder.isAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.custom_dialog, null);
                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.show();
                Button dial = dialogView.findViewById(R.id.dial);
                dial.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+ls.get(position).getNumber()));
                        c.startActivity(intent);
                        ((Activity)c).finish();
                    }
                });
                //AlertDialog alertDialog = builder.create();
                //alertDialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, number, doctor;
        Button isAccept, status;
        CircleImageView img;
        public MyViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            doctor = itemView.findViewById(R.id.doctor);
            isAccept = itemView.findViewById(R.id.accept);
            status = itemView.findViewById(R.id.status);
            img = itemView.findViewById(R.id.person_photo);
        }
    }
}
