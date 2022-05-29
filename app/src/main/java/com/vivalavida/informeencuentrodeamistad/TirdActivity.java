package com.vivalavida.informeencuentrodeamistad;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Locale;

public class TirdActivity extends AppCompatActivity {
    DatabaseReference mRef;
    ConstraintLayout lLoading;
    EditText editNombre1,editNombre2,editNombre3,editNombre4,editNombre5,editNombre6,editCodigo1,editCodigo2,editCodigo3,
            editCodigo4,editCodigo5,editCodigo6,editCuantasCartasRecibio,editMotivoNoPudoEntregarla,editHorarioContactar,
            editUseEsteESpacio;
    CheckBox checkRecibidoCartas,checkLaEntregoEstaSemana,checkHablarPastores;
    ImageView imgHelp;
    Button btnNext;
    String strChild,strReciCartas = "no",strEntregoSemana = "no",strHablarPastores = "no";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tercer_layout);

        mRef = FirebaseDatabase.getInstance().getReference();

        lLoading = findViewById(R.id.layout_loading);
        strChild = getIntent().getStringExtra("child");
        //uploadDefaultData
        HashMap<String, Object>map = new HashMap<>();
        map.put("Ha recibido cartas para entregar esta semana","no");
        map.put("Cuántas cartas recibió","");
        map.put("Las entregó esta semana","no");
        map.put("Motivo por el cual no pudo entregarla","");
        map.put("Necesita hablar con sus pastores","no");
        map.put("Horario en el que se le puede contactar","");
        map.put("Espacio para testimonios","");
        mRef.child("encuentrodeamistad").child("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(strChild).child("CONTACTO ENTREGA DE CARTAS").updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Toast.makeText(TirdActivity.this, "suscess Default data", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        TirdActivity.super.onBackPressed();
                        Toast.makeText(TirdActivity.this, "Ocurrio un error, porfavor reintentar", Toast.LENGTH_SHORT).show();
                    }
                });

        editNombre1 = findViewById(R.id.edit_nombre1);
        editNombre2 = findViewById(R.id.edit_nombre2);
        editNombre3 = findViewById(R.id.edit_nombre3);
        editNombre4 = findViewById(R.id.edit_nombre4);
        editNombre5 = findViewById(R.id.edit_nombre5);
        editNombre6 = findViewById(R.id.edit_nombre6);

        editCodigo1 = findViewById(R.id.edit_codigo1);
        editCodigo2 = findViewById(R.id.edit_codigo2);
        editCodigo3 = findViewById(R.id.edit_codigo3);
        editCodigo4 = findViewById(R.id.edit_codigo4);
        editCodigo5 = findViewById(R.id.edit_codigo5);
        editCodigo6 = findViewById(R.id.edit_codigo6);

        editCuantasCartasRecibio = findViewById(R.id.edit_cuantas_cartasrecibio);
        editMotivoNoPudoEntregarla = findViewById(R.id.edit_motivo_nopudoentrgarla);
        editHorarioContactar = findViewById(R.id.edit_horario_contactar);
        editUseEsteESpacio = findViewById(R.id.edit_use_este_espacio);

        checkRecibidoCartas = findViewById(R.id.check_recibidocartas);
        checkLaEntregoEstaSemana = findViewById(R.id.check_laentregoestasemana);
        checkHablarPastores = findViewById(R.id.check_hablar_pastores);

        imgHelp = findViewById(R.id.img_help);
        btnNext = findViewById(R.id.btn_next);

        imgHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogHelp();
            }
        });

        lLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        checkRecibidoCartas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    strReciCartas = "si";
                }else {
                    strReciCartas = "no";
                }
            }
        });
        checkLaEntregoEstaSemana.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    strEntregoSemana = "si";
                }else{
                    strEntregoSemana = "no";
                }
            }
        });
        checkHablarPastores.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    strHablarPastores = "si";
                }else{
                    strHablarPastores = "no";
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lLoading.setVisibility(View.VISIBLE);
                uploadDataPersonasMinistro();
            }
        });
    }

    private void uploadDataPersonasMinistro(){
        HashMap<String, Object>map = new HashMap<>();

        String nombre1 = editNombre1.getText().toString().trim();
        String nombre2 = editNombre2.getText().toString().trim();
        String nombre3 = editNombre3.getText().toString().trim();
        String nombre4 = editNombre4.getText().toString().trim();
        String nombre5 = editNombre5.getText().toString().trim();
        String nombre6 = editNombre6.getText().toString().trim();

        String codigo1 = " --- "+editCodigo1.getText().toString().trim();
        String codigo2 = " --- "+editCodigo2.getText().toString().trim();
        String codigo3 = " --- "+editCodigo3.getText().toString().trim();
        String codigo4 = " --- "+editCodigo4.getText().toString().trim();
        String codigo5 = " --- "+editCodigo5.getText().toString().trim();
        String codigo6 = " --- "+editCodigo6.getText().toString().trim();

        //////
        if (nombre1.isEmpty()){
            map.put("persona1","");
        }else{
            map.put("persona1",nombre1+codigo1);
        }
        //////
        if (nombre2.isEmpty()){
            map.put("persona2","");
        }else{
            map.put("persona2",nombre2+codigo2);
        }
        /////
        if (nombre3.isEmpty()){
            map.put("persona3","");
        }else{
            map.put("persona3",nombre3+codigo3);
        }
        //////
        if (nombre4.isEmpty()){
            map.put("persona4","");
        }else{
            map.put("persona4",nombre4+codigo4);
        }
        //////
        if (nombre5.isEmpty()){
            map.put("persona5","");
        }else{
            map.put("persona5",nombre5+codigo5);
        }
        ////////
        if (nombre6.isEmpty()){
            map.put("persona6","");
        }else{
            map.put("persona6",nombre6+codigo6);
        }

        mRef.child("encuentrodeamistad").child("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(strChild).child("PERSONAS QUE MINISTRO").setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        uploadDataEntregaCartas();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TirdActivity.this, "error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        lLoading.setVisibility(View.GONE);
                    }
                });
    }

    private void uploadDataEntregaCartas(){
        HashMap<String, Object>map = new HashMap<>();
        map.put("Ha recibido cartas para entregar esta semana",strReciCartas);
        map.put("Cuántas cartas recibió",editCuantasCartasRecibio.getText().toString().trim());
        map.put("Las entregó esta semana",strEntregoSemana);
        map.put("Motivo por el cual no pudo entregarla",editMotivoNoPudoEntregarla.getText().toString().trim());
        map.put("Necesita hablar con sus pastores",strHablarPastores);
        map.put("Horario en el que se le puede contactar",editHorarioContactar.getText().toString().trim());
        map.put("Espacio para testimonios",editUseEsteESpacio.getText().toString().trim());
        mRef.child("encuentrodeamistad").child("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(strChild).child("CONTACTO ENTREGA DE CARTAS").updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Toast.makeText(TirdActivity.this, "suscess", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(TirdActivity.this,FourActivity.class);
                        i.putExtra("child",strChild);
                        lLoading.setVisibility(View.GONE);
                        startActivity(i);
                        //finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        lLoading.setVisibility(View.GONE);
                        Toast.makeText(TirdActivity.this, "error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void openDialogHelp(){
        new AlertDialog.Builder(TirdActivity.this)
                .setTitle("Codigos")
                .setMessage(getResources().getString(R.string.codigos))

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("OK", null)

                // A null listener allows the button to dismiss the dialog and take no further action.
                //.setNegativeButton(android.R.string.no, null)
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
