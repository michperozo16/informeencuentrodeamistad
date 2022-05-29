package com.vivalavida.informeencuentrodeamistad;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PersonAgregaActivity extends AppCompatActivity {
    DatabaseReference mRef;
    TextView title,tAsEncuentro,tEsMiemEncuentro,tAsisDomingo,tGuiaCreci,tDiscipulado,tDiezmoOfrendas,tCapacitacionPotenciales,
            tGanaCuidaGente,tEntiendevision;
    String strChild,strDateChild,strName;
    ConstraintLayout layoutAntes;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presonas_agregadas_layout);

        mRef = FirebaseDatabase.getInstance().getReference();

        title = findViewById(R.id.title_admin);
        tAsEncuentro = findViewById(R.id.tasistio_alencuentro);
        tEsMiemEncuentro = findViewById(R.id.tesmiembro_deencuentro);
        tAsisDomingo = findViewById(R.id.tasistencia_domingo);
        tGuiaCreci = findViewById(R.id.tguia_decrecimiento);
        tDiscipulado = findViewById(R.id.tdiscipulado);
        tDiezmoOfrendas = findViewById(R.id.tdiezmo_ofrendas);
        tCapacitacionPotenciales = findViewById(R.id.tcapacitacion_postenciales);
        tGanaCuidaGente = findViewById(R.id.tgana_yocuidagente);
        tEntiendevision = findViewById(R.id.tentiende_lavision);

        layoutAntes = findViewById(R.id.antes);

        strChild = getIntent().getStringExtra("child");
        strDateChild = getIntent().getStringExtra("datechild");
        strName = getIntent().getStringExtra("name");

        title.setText(strName);

        getInfoPredicar();
        getInfoPreparar();
        getInfoPastorear();
        getInfoPlantar();

    }

    private void getInfoPredicar(){
        mRef.child("encuentrodeamistad").child("usuarios").child(strChild).child(strDateChild).child("PROPOSITO DE LA VISION")
                .child(strName).child("PREDICAR").addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String asisEncuen = snapshot.child("Asistió al encuentro").getValue(String.class);
                String esMiembro = snapshot.child("Es miembro de encuentro").getValue(String.class);



                tAsEncuentro.setText(tAsEncuentro.getText().toString()+" - "+'"'+asisEncuen+'"');
                tEsMiemEncuentro.setText(tEsMiemEncuentro.getText().toString()+" - "+'"'+esMiembro+'"');
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getInfoPastorear(){
        mRef.child("encuentrodeamistad").child("usuarios").child(strChild).child(strDateChild).child("PROPOSITO DE LA VISION")
                .child(strName).child("PASTOREAR").addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String asisDom = snapshot.child("Asistencia domingo").getValue(String.class);
                        String discipulado = snapshot.child("Discipulado").getValue(String.class);
                        String guiacrece = snapshot.child("Guía de crecimiento").getValue(String.class);

                        tAsisDomingo.setText(tAsisDomingo.getText().toString()+" - "+'"'+asisDom+'"');
                        tDiscipulado.setText(tDiscipulado.getText().toString()+" - "+'"'+discipulado+'"');
                        tGuiaCreci.setText(tGuiaCreci.getText().toString()+" - "+'"'+guiacrece+'"');
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void getInfoPreparar(){
        mRef.child("encuentrodeamistad").child("usuarios").child(strChild).child(strDateChild).child("PROPOSITO DE LA VISION")
                .child(strName).child("PREPARAR").addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String capaMpote = snapshot.child("Capacitación M Potenciales").getValue(String.class);
                        String diezmoOfren = snapshot.child("Diezmo y ofrendas").getValue(String.class);

                        tCapacitacionPotenciales.setText(tCapacitacionPotenciales.getText().toString()+" - "+'"'+capaMpote+'"');
                        tDiezmoOfrendas.setText(tDiezmoOfrendas.getText().toString()+" - "+'"'+diezmoOfren+'"');
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void getInfoPlantar(){
        mRef.child("encuentrodeamistad").child("usuarios").child(strChild).child(strDateChild).child("PROPOSITO DE LA VISION")
                .child(strName).child("PLANTAR").addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String enteVision = snapshot.child("Entiende la visión").getValue(String.class);
                        String ganacuidagent = snapshot.child("Gana y-o cuida gente").getValue(String.class);

                        tEntiendevision.setText(tEntiendevision.getText().toString()+" - "+'"'+enteVision+'"');
                        tGanaCuidaGente.setText(tGanaCuidaGente.getText().toString()+" - "+'"'+ganacuidagent+'"');

                        layoutAntes.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
