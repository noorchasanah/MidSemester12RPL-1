package com.example.rplrus10.midsemester12rpl;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rplrus10.midsemester12rpl.database.DatabaseHelper;
import com.example.rplrus10.midsemester12rpl.database.MahasiswaHelper;
import com.example.rplrus10.midsemester12rpl.database.MahasiswaModel;

import java.util.ArrayList;

public class ModelAdapter extends RecyclerView.Adapter<recyclerHolder> {
    private ArrayList<MahasiswaModel> mahasiswaModelArrayList ;
    Context context;
    MahasiswaHelper mahasiswaHelper;

    public ModelAdapter(Context context, ArrayList<MahasiswaModel> mahasiswaModelArrayList){
        this.context = context;
        this.mahasiswaModelArrayList = mahasiswaModelArrayList;
        mahasiswaHelper = new MahasiswaHelper(context);
    }

    @Override
    public recyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        recyclerHolder rcv = new recyclerHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final recyclerHolder holder,final int position) {
        final MahasiswaModel model = mahasiswaModelArrayList.get(position);
        Glide.with(context)
                .load(model.getUrl())
                .into(holder.imgholder);
        Log.d("", "gambarku: "+model.getUrl());
        holder.person_name.setText(mahasiswaModelArrayList.get(position).getName());
        holder.btndetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nama_group = mahasiswaModelArrayList.get(position).getName();
                final String deskripsi_group = mahasiswaModelArrayList.get(position).getNim();
                final String gambar = mahasiswaModelArrayList.get(position).getUrl();
                final String id = String.valueOf(mahasiswaModelArrayList.get(position).getId());
                Intent i = new Intent(context.getApplicationContext(),detail_idol.class);
                i.putExtra("id",id);
                i.putExtra("nama_group",nama_group);
                i.putExtra("deskripsi",deskripsi_group);
                i.putExtra("gambar",gambar);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        holder.btnshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,model.getName()+"\n"+model.getNim());
                intent.setType("text/plain");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.btnhapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id = model.getId();
                mahasiswaHelper.delete(id);
                mahasiswaModelArrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,mahasiswaModelArrayList.size());
            }
        });
    }
    @Override
    public int getItemCount() {
        return mahasiswaModelArrayList.size();
    }
}
