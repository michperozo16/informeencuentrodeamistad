package com.vivalavida.informeencuentrodeamistad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class SecondActivity extends AppCompatActivity {
    DatabaseReference mRef;
    ConstraintLayout lLoading;
    ListView listInfoPersonal;
    ArrayAdapter<String> adapter;
    Button btnNext;
    String strChild;
    HashMap<String, Object>map = new HashMap<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.segundo_layout);

        mRef = FirebaseDatabase.getInstance().getReference();

        lLoading = findViewById(R.id.layout_loading);



        strChild = getIntent().getStringExtra("child");
        //upload default false all values
        HashMap<String,Object>defValue = new HashMap<>();
        defValue.put("Oré cada día y medité la palabra","no");
        defValue.put("Preparé bien el tema","no");
        defValue.put("Diezmé","no");
        defValue.put("Ofrendé","no");
        defValue.put("Asistí a mi equipo","no");
        defValue.put("Evangelicé","no");
        defValue.put("Oré en el espíritu","no");
        defValue.put("Oré por mis pastores, líderes, miembros del encuentro","no");
        defValue.put("Tomé tiempo para mi familia","no");
        defValue.put("Asistí al grupo de intercesión","no");

        mRef.child("encuentrodeamistad").child("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(strChild).child("INFROMACION PERSONAL").setValue(defValue).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Toast.makeText(SecondActivity.this, "success", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SecondActivity.this, "ocurio un error, porfavor reintentar", Toast.LENGTH_SHORT).show();
                        SecondActivity.super.onBackPressed();
                    }
                });

        btnNext = findViewById(R.id.btn_next);
        listInfoPersonal = findViewById(R.id.list_informacion_personal);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,getResources().getStringArray(R.array.array_info_personal));
        listInfoPersonal.setAdapter(adapter);



        listInfoPersonal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (listInfoPersonal.isItemChecked(i)){
                    map.put(String.valueOf(listInfoPersonal.getItemAtPosition(i)),"si");
                    //Toast.makeText(SecondActivity.this, "item checked", Toast.LENGTH_SHORT).show();
                }else{
                    map.put(String.valueOf(listInfoPersonal.getItemAtPosition(i)),"no");
                    //Toast.makeText(SecondActivity.this, "no checked", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lLoading.setVisibility(View.VISIBLE);
                uploadData();
            }
        });

        lLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }


    private void uploadData(){
        mRef.child("encuentrodeamistad").child("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(strChild).child("INFROMACION PERSONAL").updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Toast.makeText(SecondActivity.this, "suscess", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(SecondActivity.this,TirdActivity.class);
                        i.putExtra("child",strChild);
                        lLoading.setVisibility(View.GONE);
                        startActivity(i);
                        //finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SecondActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        lLoading.setVisibility(View.GONE);
                    }
                });
    }
}
