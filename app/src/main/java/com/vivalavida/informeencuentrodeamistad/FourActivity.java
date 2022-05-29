package com.vivalavida.informeencuentrodeamistad;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Locale;

public class FourActivity extends AppCompatActivity {
    DatabaseReference mRef;
    ConstraintLayout lLoading;
    TextView tCount;
    int Counter = 0;
    EditText editName;
    CheckBox checkAsistioEncuentro,checkMienbroDeEncuentro,checkAsistenciaDomingo,checkGuiaCrecimiento,checkDiscipulado1,
             checkDiscipulado2,checkDiezmoOfrendas1,checkDiezmoOfrendas2,checkCapacitacionPotencial,checkGanaCuidaGente,
             checkEntiendeLaVision;
    Button btnAdd;
    String strChild,strAsEncue = "no",strMieEncue = "no",strAsDom = "no",strGuiaCre = "no",strDis1 = "",strDis2 = "",
            strDiezOfre1 = "",strDiezOfre2 = "",strCapaPote = "no",strGanaCuidaG = "no",strEntVisi = "no",nameuser = "no";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cuarto_layout);

        mRef = FirebaseDatabase.getInstance().getReference();

        lLoading = findViewById(R.id.layout_loading);
        tCount = findViewById(R.id.tcount);
        strChild = getIntent().getStringExtra("child");



        CompoundButton.OnCheckedChangeListener multilistener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switch (compoundButton.getId()){
                    case R.id.check_asistio_encuentro:
                        if (b){
                            strAsEncue = "si";
                        }else{
                            strAsEncue = "no";
                        }
                        break;
                    case R.id.check_miembro_encuentro:
                        if (b){
                            strMieEncue = "si";
                        }else{
                            strMieEncue = "no";
                        }
                        break;
                    case R.id.check_asistencia_domingo:
                        if (b){
                            strAsDom = "si";
                        }else{
                            strAsDom = "no";
                        }
                        break;
                    case R.id.check_guia_crecimiento:
                        if (b){
                            strGuiaCre = "si";
                        }else{
                            strGuiaCre = "no";
                        }
                        break;
                    case R.id.check_discipulado_1:
                        if (b){
                            strDis1 = "1";
                        }else{
                            strDis1 = "";
                        }
                        break;
                    case R.id.check_discipulado_2:
                        if (b){
                            strDis2 = "2";
                        }else{
                            strDis2 = "";
                        }
                        break;
                    case R.id.check_diezmo_ofrendas_1:
                        if (b){
                            strDiezOfre1 = "D";
                        }else{
                            strDiezOfre1 = "";
                        }
                        break;
                    case R.id.check_diezmo_ofrendas_2:
                        if (b){
                            strDiezOfre2 = "O";
                        }else{
                            strDiezOfre2 = "";
                        }
                        break;
                    case R.id.check_capacitacion_potenciales:
                        if (b){
                            strCapaPote = "si";
                        }else{
                            strCapaPote = "no";
                        }
                        break;
                    case R.id.check_gana_yocuidagente:
                        if (b){
                            strGanaCuidaG = "si";
                        }else{
                            strGanaCuidaG = "no";
                        }
                        break;
                    case R.id.check_entiende_lavision:
                        if (b){
                            strEntVisi = "si";
                        }else{
                            strEntVisi = "no";
                        }
                        break;
                }
            }
        };

        editName = findViewById(R.id.edit_name);
        //((CheckBox)findViewById(R.id.check_asistio_encuentro)).setOnCheckedChangeListener(multilistener);
        checkAsistioEncuentro = findViewById(R.id.check_asistio_encuentro);
        checkAsistioEncuentro.setOnCheckedChangeListener(multilistener);

        checkMienbroDeEncuentro  = findViewById(R.id.check_miembro_encuentro);
        checkMienbroDeEncuentro.setOnCheckedChangeListener(multilistener);

        checkAsistenciaDomingo = findViewById(R.id.check_asistencia_domingo);
        checkAsistenciaDomingo.setOnCheckedChangeListener(multilistener);

        checkGuiaCrecimiento = findViewById(R.id.check_guia_crecimiento);
        checkGuiaCrecimiento.setOnCheckedChangeListener(multilistener);

        checkDiscipulado1 = findViewById(R.id.check_discipulado_1);
        checkDiscipulado1.setOnCheckedChangeListener(multilistener);

        checkDiscipulado2 = findViewById(R.id.check_discipulado_2);
        checkDiscipulado2.setOnCheckedChangeListener(multilistener);

        checkDiezmoOfrendas1 = findViewById(R.id.check_diezmo_ofrendas_1);
        checkDiezmoOfrendas1.setOnCheckedChangeListener(multilistener);

        checkDiezmoOfrendas2 = findViewById(R.id.check_diezmo_ofrendas_2);
        checkDiezmoOfrendas2.setOnCheckedChangeListener(multilistener);

        checkCapacitacionPotencial = findViewById(R.id.check_capacitacion_potenciales);
        checkCapacitacionPotencial.setOnCheckedChangeListener(multilistener);

        checkGanaCuidaGente = findViewById(R.id.check_gana_yocuidagente);
        checkGanaCuidaGente.setOnCheckedChangeListener(multilistener);

        checkEntiendeLaVision = findViewById(R.id.check_entiende_lavision);
        checkEntiendeLaVision.setOnCheckedChangeListener(multilistener);

        btnAdd = findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lLoading.setVisibility(View.VISIBLE);
                uploadUsers();

            }
        });

        lLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void uploadUsers() {
        nameuser = editName.getText().toString().trim();
        if (nameuser.isEmpty()) {
            lLoading.setVisibility(View.GONE);
            editName.setError("Se necesita un nombre");
            Toast.makeText(this, "Se necesita un nombre", Toast.LENGTH_SHORT).show();
        } else {

            HashMap<String, Object> map = new HashMap<>();
            map.put("Nombre", nameuser);
            mRef.child("encuentrodeamistad").child("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(strChild).child("PROPOSITO DE LA VISION").child(nameuser).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            uploadPredicar();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FourActivity.this, "error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            lLoading.setVisibility(View.GONE);
                        }
                    });
        }
    }
    private void uploadPredicar(){
        HashMap<String, Object>map = new HashMap<>();
        map.put("Asistió al encuentro",strAsEncue);
        map.put("Es miembro de encuentro",strMieEncue);
        mRef.child("encuentrodeamistad").child("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(strChild).child("PROPOSITO DE LA VISION").child(nameuser).child("PREDICAR").updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        uploadPastorear();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FourActivity.this, "error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        lLoading.setVisibility(View.GONE);
                    }
                });
    }
    private void uploadPastorear() {
        String isDiscipul = "";
        isDiscipul = strDis1+" y "+strDis2;

        switch (isDiscipul) {
            case " y ":
                isDiscipul = "sin datos";
                break;
            case "1 y ":
                isDiscipul = "1";
                break;
            case " y 2":
                isDiscipul = "2";
                break;
        }

        HashMap<String, Object>map = new HashMap<>();
        map.put("Asistencia domingo",strAsDom);
        map.put("Guía de crecimiento",strGuiaCre);
        map.put("Discipulado",isDiscipul);
        mRef.child("encuentrodeamistad").child("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(strChild).child("PROPOSITO DE LA VISION").child(nameuser).child("PASTOREAR").updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        uploadPreparar();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FourActivity.this, "error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        lLoading.setVisibility(View.GONE);
                    }
                });
    }
    private void uploadPreparar(){
        String isDiezmoyOfrend = "";
        isDiezmoyOfrend = strDiezOfre1+" y "+strDiezOfre2;

        switch (isDiezmoyOfrend) {
            case " y ":
                isDiezmoyOfrend = "sin datos";
                break;
            case "D y ":
                isDiezmoyOfrend = "D";
                break;
            case " y O":
                isDiezmoyOfrend = "O";
                break;
        }


        HashMap<String, Object>map = new HashMap<>();
        map.put("Diezmo y ofrendas",isDiezmoyOfrend);
        map.put("Capacitación M Potenciales",strCapaPote);
        mRef.child("encuentrodeamistad").child("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(strChild).child("PROPOSITO DE LA VISION").child(nameuser).child("PREPARAR").updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        uploadPlantar();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FourActivity.this, "error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        lLoading.setVisibility(View.GONE);
                    }
                });
    }
    private void uploadPlantar(){
        HashMap<String, Object>map = new HashMap<>();
        map.put("Gana y-o cuida gente",strGanaCuidaG);
        map.put("Entiende la visión",strEntVisi);
        mRef.child("encuentrodeamistad").child("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(strChild).child("PROPOSITO DE LA VISION").child(nameuser).child("PLANTAR").updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(Void unused) {
                        editName.getText().clear();

                        checkAsistioEncuentro.setChecked(false);
                        checkMienbroDeEncuentro.setChecked(false);
                        checkAsistenciaDomingo.setChecked(false);
                        checkGuiaCrecimiento.setChecked(false);
                        checkDiscipulado1.setChecked(false);
                        checkDiscipulado2.setChecked(false);
                        checkDiezmoOfrendas1.setChecked(false);
                        checkDiezmoOfrendas2.setChecked(false);
                        checkCapacitacionPotencial.setChecked(false);
                        checkGanaCuidaGente.setChecked(false);
                        checkEntiendeLaVision.setChecked(false);

                        Counter++;
                        tCount.setText("+"+Counter+" "+"personas");
                        lLoading.setVisibility(View.GONE);

                        //Toast.makeText(FourActivity.this, ""+strAsEncue, Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FourActivity.this, "error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        lLoading.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(FourActivity.this)
                .setTitle("Salir de la app?")
                .setMessage("Esta seguro de salir de la aplicación. Si presiona si ya no podra continuar")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("No",null)
                .show();
    }
}
