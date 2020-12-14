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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class AmbulanceRvAdapter extends RecyclerView.Adapter<AmbulanceRvAdapter.MyViewHolder> {
    private List<AmbulanceRequest> ls;
    Context c;
    Runnable r;
    View.OnClickListener mMyOnClickListener;

    FirebaseUser fuser;

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
        holder.name.setText(ls.get(position).getName());
        holder.number.setText(ls.get(position).getNumber());
        holder.address.setText(ls.get(position).getAddress());
        Picasso.get().load(ls.get(position).getImg()).fit().centerCrop().into(holder.img);
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
            }
        });

        holder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fuser = FirebaseAuth.getInstance().getCurrentUser();
                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("AmbulanceRequests");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            AmbulanceRequest br = ds.getValue(AmbulanceRequest.class);
                            if (br.getName().equals(ls.get(position).getName())
                                    && br.getAddress().equals(ls.get(position).getAddress())
                                    && br.getNumber().equals(ls.get(position).getNumber()))
                            {
                                String key = ds.getKey();
                                ref.child(key).child("status").setValue("completed");
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, number, address;
        Button isAccept, status;
        CircleImageView img;
        public MyViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            address = itemView.findViewById(R.id.address);
            isAccept = itemView.findViewById(R.id.accept);
            status = itemView.findViewById(R.id.status);
            img = itemView.findViewById(R.id.person_photo);
        }
    }
}
