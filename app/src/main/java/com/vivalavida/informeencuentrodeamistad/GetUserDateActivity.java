package com.vivalavida.informeencuentrodeamistad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vivalavida.informeencuentrodeamistad.Adapter.ClearClicklistener;
import com.vivalavida.informeencuentrodeamistad.Adapter.TitleClickListener;
import com.vivalavida.informeencuentrodeamistad.Adapter.usersAdapter;
import com.vivalavida.informeencuentrodeamistad.Model.userModel;

import java.util.ArrayList;
import java.util.List;

public class GetUserDateActivity extends AppCompatActivity implements TitleClickListener, ClearClicklistener {
    DatabaseReference mRef;
    RecyclerView rvDate;
    TextView title,subtitle;
    String strChild,strDateChild,strName,provider,whatthis;
    List<userModel>dateList,propoMisionList;
    ConstraintLayout layoutAntes;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout);

        mRef = FirebaseDatabase.getInstance().getReference();
        rvDate = findViewById(R.id.rv_users);
        title = findViewById(R.id.title_admin);
        subtitle = findViewById(R.id.t_users);

        layoutAntes = findViewById(R.id.antes);

        provider = getIntent().getStringExtra("provider");
        strChild = getIntent().getStringExtra("child");
        strDateChild = getIntent().getStringExtra("datechild");
        strName = getIntent().getStringExtra("name");

        title.setText(strName);

        if (provider.equalsIgnoreCase("adminFragment")){
            subtitle.setText("Date");
            whatthis = "date";
            getDateUsers();
        }else{
            subtitle.setText("Propósito de la visión");
            whatthis = "user";
            getPropositoDeLaVision();
        }
    }

    private void getDateUsers(){
        dateList = new ArrayList<>();
        mRef.child("encuentrodeamistad").child("usuarios").child(strChild).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    userModel um = new userModel();
                    um.setNombre(ds.getKey());

                    if (!um.getNombre().equalsIgnoreCase("correo")) {
                        if (!um.getNombre().equalsIgnoreCase("nombre")){
                            if (!um.getNombre().contains("contraseña")) {
                                if (!um.getNombre().equalsIgnoreCase("rol")) {
                                    dateList.add(um);
                                }
                            }
                        }
                    }
                }
                layoutAntes.setVisibility(View.GONE);
                setRvDate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getPropositoDeLaVision(){
        dateList = new ArrayList<>();
        mRef.child("encuentrodeamistad").child("usuarios").child(strChild).child(strDateChild).child("PROPOSITO DE LA VISION").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    userModel mm = new userModel();
                    mm.setNombre(ds.getKey());
                    dateList.add(mm);
                    setRvDate();

                    layoutAntes.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setRvDate(){
        usersAdapter usersAdapter = new usersAdapter(this,dateList,this,this,whatthis);
        rvDate.setAdapter(usersAdapter);
        LinearLayoutManager lm = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvDate.setLayoutManager(lm);
        usersAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClearClick(userModel userModel, int adapterPosition, ConstraintLayout clear, String correo) {

    }

    @Override
    public void onTitleClick(userModel userModel, int adapterPosition, ConstraintLayout rootTitle, TextView title) {
        //Toast.makeText(this, ""+userModel.getNombre(), Toast.LENGTH_SHORT).show();
        Intent i;
        if (provider.equalsIgnoreCase("adminFragment")) {
            i = new Intent(GetUserDateActivity.this, GetUserDetailsActivity.class);
            i.putExtra("keychild", strChild);
            i.putExtra("datechild", userModel.getNombre());
            i.putExtra("title", strName);
        }else {
            i = new Intent(GetUserDateActivity.this, PersonAgregaActivity.class);
            i.putExtra("child",strChild);
            i.putExtra("datechild",strDateChild);
            i.putExtra("name",userModel.getNombre());
        }
        startActivity(i);
    }
}
