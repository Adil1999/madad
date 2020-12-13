package com.adil.madad;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class AmbulanceRvAdapter extends RecyclerView.Adapter<AmbulanceRvAdapter.MyViewHolder> {
    private List<AmbulanceRequest> ls;
    Context c;
    Runnable r;
    View.OnClickListener mMyOnClickListener;

    AmbulanceRvAdapter(List ls, Context c){
        this.ls = ls;
        this.c = c;
        this.r = null;
    }


    @NonNull
    @Override
    public AmbulanceRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(c).inflate(R.layout.ambulance_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AmbulanceRvAdapter.MyViewHolder holder, final int position) {
        holder.name.setText(ls.get(position).getUsername());
        holder.number.setText(ls.get(position).getPhno());
        holder.address.setText(ls.get(position).getAddress());
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
                        intent.setData(Uri.parse("tel:"+ls.get(position).getPhno()));
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
        TextView name, number, address;
        Button isAccept;
        public MyViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            address = itemView.findViewById(R.id.address);
            isAccept = itemView.findViewById(R.id.accept);
        }
    }
}
