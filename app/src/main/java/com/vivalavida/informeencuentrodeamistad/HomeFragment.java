package com.vivalavida.informeencuentrodeamistad;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

public class HomeFragment extends Fragment {
    DatabaseReference mRef;
    FirebaseApp mApp;
    ConstraintLayout lLoading,laExiste,progreExiste;
    TextView tVuelva,tOmitir;
    View view;
    NestedScrollView rootLayout;
    EditText editNameMinistro,editMinistrosPotenciales1,editMinistrosPotenciales2,editMinistrosPotenciales3,
            editDireccionDeEncuentro,editHorarioDeEncuentro,editFechaDeEncuentro,editTemaCompartido;
    Button btnNext;
    Calendar calendar;
    int year,month,numOfTheWeek,dayOfTheMont,dayOfTheWeek;
    String nombreMes;
    int leftDays;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.primer_layout, container, false);

        mRef = FirebaseDatabase.getInstance().getReference();

        lLoading = view.findViewById(R.id.layout_loading);
        laExiste = view.findViewById(R.id.layout_existe);
        tVuelva = view.findViewById(R.id.t_leftdays);
        tOmitir = view.findViewById(R.id.t_omitir);
        progreExiste = view.findViewById(R.id.progress_existe);
        rootLayout = view.findViewById(R.id.root_layout);

        editNameMinistro = view.findViewById(R.id.edit_name_ministro);
        editMinistrosPotenciales1 = view.findViewById(R.id.edit_ministros_potenciales_1);
        editMinistrosPotenciales2 = view.findViewById(R.id.edit_ministros_potenciales_2);
        editMinistrosPotenciales3 = view.findViewById(R.id.edit_ministros_potenciales_3);
        editDireccionDeEncuentro = view.findViewById(R.id.edit_direccion_de_encuentro);
        editHorarioDeEncuentro = view.findViewById(R.id.edit_horario_de_encuentro);
        editFechaDeEncuentro = view.findViewById(R.id.edit_fecha_de_encuentro);
        editTemaCompartido = view.findViewById(R.id.edit_tema_compartido);

        btnNext = view.findViewById(R.id.btn_next);

        calendar = Calendar.getInstance(TimeZone.getDefault());
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        dayOfTheMont = calendar.get(Calendar.DAY_OF_MONTH);
        numOfTheWeek = calendar.get(Calendar.WEEK_OF_MONTH);
        dayOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK);

        switch(month) {
            case 1:
                nombreMes = "enero";
                break;
            case 2:
                nombreMes = "febrero";
                break;
            case 3:
                nombreMes = "marzo";
                break;
            case 4:
                nombreMes = "abril";
                break;
            case 5:
                nombreMes = "mayo";
                break;
            case 6:
                nombreMes = "junio";
                break;
            case 7:
                nombreMes = "julio";
                break;
            case 8:
                nombreMes = "agosto";
                break;
            case 9:
                nombreMes = "septiembre";
                break;
            case 10:
                nombreMes = "octubre";
                break;
            case 11:
                nombreMes = "noviembre";
                break;
            case 12:
                nombreMes = "diciembre";
                break;
        }

        checkIfExist();

        if (dayOfTheWeek == 7){
            tVuelva.setText("Vuelva mañana");
        }else{
            leftDays = 7-dayOfTheWeek;
            tVuelva.setText("Vuelva despues de "+leftDays+" dias");
        }

        tOmitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootLayout.setVisibility(View.VISIBLE);
                laExiste.setVisibility(View.GONE);
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

        return view;
    }

    private void uploadData(){
        //FirebaseAuth.getInstance().getCurrentUser().getUid()
        HashMap<String, Object>map = new HashMap<>();
        map.put("Nombre del ministro",editNameMinistro.getText().toString().trim());
        map.put("Ministros potenciales 1",editMinistrosPotenciales1.getText().toString().trim());
        map.put("Ministros potenciales 2",editMinistrosPotenciales2.getText().toString().trim());
        map.put("Ministros potenciales 3",editMinistrosPotenciales3.getText().toString().trim());
        map.put("Direccion del encuentro",editDireccionDeEncuentro.getText().toString().trim());
        map.put("Horario del encuentro",editHorarioDeEncuentro.getText().toString().trim());
        map.put("Fecha del encuentro",editFechaDeEncuentro.getText().toString().trim());
        map.put("Tema compartido",editTemaCompartido.getText().toString().trim());
        mRef.child("encuentrodeamistad").child("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Semana N° "+numOfTheWeek+" - "+nombreMes+" "+"del"+" "+year).child("FIRST").setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Toast.makeText(getActivity(), "suscess", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getActivity(),SecondActivity.class);
                        i.putExtra("child","Semana N° "+numOfTheWeek+" - "+nombreMes+" "+"del"+" "+year);
                        lLoading.setVisibility(View.GONE);
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        lLoading.setVisibility(View.GONE);
                    }
                });
    }

    private void checkIfExist(){
        mRef.child("encuentrodeamistad").child("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Semana N° "+numOfTheWeek+" - "+nombreMes+" "+"del"+" "+year).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            progreExiste.setVisibility(View.GONE);
                        }else {
                            laExiste.setVisibility(View.GONE);
                            rootLayout.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
