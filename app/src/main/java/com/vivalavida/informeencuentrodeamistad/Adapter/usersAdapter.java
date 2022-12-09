package com.vivalavida.informeencuentrodeamistad.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.vivalavida.informeencuentrodeamistad.Model.userModel;
import com.vivalavida.informeencuentrodeamistad.R;

import java.util.ArrayList;
import java.util.List;

public class usersAdapter extends RecyclerView.Adapter<usersAdapter.MyViewHolder> {
    Context context;
    List<userModel>mlist;
    ArrayList<String> keylist;
    TitleClickListener titleClickListener;
    ClearClicklistener clearClicklistener;
    String correo;
    String WhatIs;

    public usersAdapter(Context context, List<userModel> mlist, TitleClickListener titleClickListener, ClearClicklistener clearClicklistener, String whatIs) {
        this.context = context;
        this.mlist = mlist;
        this.titleClickListener = titleClickListener;
        this.clearClicklistener = clearClicklistener;
        WhatIs = whatIs;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_users_item,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final userModel um = mlist.get(position);
        correo = um.getCorreo();

        if (WhatIs.equals("date")){
            holder.img.setBackground(context.getResources().getDrawable(R.drawable.ic_baseline_calendar_month_24));
            holder.title.setText(um.getNombre());
        }else if (WhatIs.equals("user")){
            holder.img.setBackground(context.getResources().getDrawable(R.drawable.ic_baseline_person_24));
            holder.title.setText(holder.getAdapterPosition()+1+". "+um.getNombre());
        }else if (WhatIs.equals("")){
            holder.title.setText(holder.getAdapterPosition()+1+". "+um.getNombre());
        }
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView title;
        ConstraintLayout clear,rootTitle;
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            title = itemView.findViewById(R.id.title);
            clear = itemView.findViewById(R.id.clear);
            rootTitle = itemView.findViewById(R.id.root_title);
            img = itemView.findViewById(R.id.img);

            rootTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    titleClickListener.onTitleClick(mlist.get(getAdapterPosition()),getAdapterPosition(),rootTitle,title);
                }
            });
            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clearClicklistener.onClearClick(mlist.get(getAdapterPosition()),getAdapterPosition(),clear,correo);
                }
            });
        }
    }
}
