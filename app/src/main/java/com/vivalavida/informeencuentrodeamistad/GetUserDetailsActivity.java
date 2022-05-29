package com.vivalavida.informeencuentrodeamistad;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GetUserDetailsActivity extends AppCompatActivity {
    DatabaseReference mRef;
    TextView rootTitle,tNombredeMinistro,tMinistrosPotenciales,tDirecciondeEncuentro,
    tHorariodeEncuentro,tFechadeEncuentro,tTemaCompartido,tInformacionPersonal,tPersonaMinistro,tContactoEntregaCartas,tEspacioTestimonios;
    Button btnVerPersonas;
    String strKeyChild,strDateChild,strTitle;

    ConstraintLayout layoutAntes;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_details_layout);

        mRef = FirebaseDatabase.getInstance().getReference();

        rootTitle = findViewById(R.id.title_admin);
        tNombredeMinistro = findViewById(R.id.t_nombre_ministro);
        tMinistrosPotenciales = findViewById(R.id.t_ministros_potenciales);
        tDirecciondeEncuentro = findViewById(R.id.t_direccion_encuentro);
        tHorariodeEncuentro = findViewById(R.id.t_horario_encuentro);
        tFechadeEncuentro = findViewById(R.id.t_fecha_encuentro);
        tTemaCompartido = findViewById(R.id.t_tema_compartido);
        tInformacionPersonal = findViewById(R.id.t_informacion_personal);
        tPersonaMinistro = findViewById(R.id.t_persona_ministro);
        tContactoEntregaCartas = findViewById(R.id.t_contacto_porentregacartas);
        tEspacioTestimonios = findViewById(R.id.t_espacio_testimonios);

        layoutAntes = findViewById(R.id.antes);

        strKeyChild = getIntent().getStringExtra("keychild");
        strDateChild = getIntent().getStringExtra("datechild");
        strTitle = getIntent().getStringExtra("title");

        rootTitle.setText(strTitle);

        btnVerPersonas = findViewById(R.id.btn_ver_personas);

        getDetailsFirst();
        getDetailsInfoPersonal();
        getDetailsPersonasMinistro();
        getDetailsContactoCartas();

        btnVerPersonas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GetUserDetailsActivity.this,GetUserDateActivity.class);
                i.putExtra("child",strKeyChild);
                i.putExtra("datechild",strDateChild);
                i.putExtra("name",strTitle);
                i.putExtra("provider","userDetails");
                startActivity(i);
            }
        });
    }

    private void getDetailsFirst(){
        mRef.child("encuentrodeamistad").child("usuarios").child(strKeyChild).child(strDateChild).child("FIRST").addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String direEncuentro = snapshot.child("Direccion del encuentro").getValue(String.class);
                String feEncuentro = snapshot.child("Fecha del encuentro").getValue(String.class);
                String hoEncuentro = snapshot.child("Horario del encuentro").getValue(String.class);
                String miPo1 = "1. "+snapshot.child("Ministros potenciales 1").getValue(String.class);
                String miPo2 = "2. "+snapshot.child("Ministros potenciales 2").getValue(String.class);
                String miPo3 = "3. "+snapshot.child("Ministros potenciales 3").getValue(String.class);
                String noMini = snapshot.child("Nombre del ministro").getValue(String.class);
                String teCom = snapshot.child("Tema compartido").getValue(String.class);

                String nPo1 = miPo1+"\n"+"\n";
                String nPo2 = miPo2+"\n"+"\n";
                String nPo3 = miPo3;

                if (nPo1.equals("1. "+"\n"+"\n")){
                    nPo1 = "";
                }
                if (nPo2.equals("2. "+"\n"+"\n")){
                    nPo2 = "";
                }
                if (nPo3.equals("3. ")){
                    nPo3 = "";
                }

                tDirecciondeEncuentro.setText(direEncuentro);
                tFechadeEncuentro.setText(feEncuentro);
                tHorariodeEncuentro.setText(hoEncuentro);
                tMinistrosPotenciales.setText(nPo1+nPo2+nPo3);
                tNombredeMinistro.setText(noMini);
                tTemaCompartido.setText(teCom);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDetailsInfoPersonal(){
        mRef.child("encuentrodeamistad").child("usuarios").child(strKeyChild).child(strDateChild).child("INFROMACION PERSONAL").addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String asisEquip = snapshot.child("Asistí a mi equipo").getValue(String.class);
                String asisGrupInter = snapshot.child("Asistí al grupo de intercesión").getValue(String.class);
                String diezme = snapshot.child("Diezmé").getValue(String.class);
                String evange = snapshot.child("Evangelicé").getValue(String.class);
                String ofrende = snapshot.child("Ofrendé").getValue(String.class);
                String oreDiaMediteP = snapshot.child("Oré cada día y medité la palabra").getValue(String.class);
                String oreEspiri = snapshot.child("Oré en el espíritu").getValue(String.class);
                String orePastores = snapshot.child("Oré por mis pastores, líderes, miembros del encuentro").getValue(String.class);
                String preBienTema = snapshot.child("Preparé bien el tema").getValue(String.class);
                String tomTiemFam = snapshot.child("Tomé tiempo para mi familia").getValue(String.class);

                tInformacionPersonal.setText("* Oré cada día y medité la palabra - "+'"'+oreDiaMediteP+'"'+"\n"+"\n"
                        +"* Preparé bien el tema - "+'"'+preBienTema+'"'+"\n"+"\n"
                        +"* Diezmé - "+'"'+diezme+'"'+"\n"+"\n"
                        +"* Ofrendé - "+'"'+ofrende+'"'+"\n"+"\n"
                        +"* Asistí a mi equipo - "+'"'+asisEquip+'"'+"\n"+"\n"
                        +"* Evangelicé - "+'"'+evange+'"'+"\n"+"\n"
                        +"* Oré en el espíritu - "+'"'+oreEspiri+'"'+"\n"+"\n"
                        +"* Oré por mis pastores, líderes, miembros del encuentro - "+'"'+orePastores+'"'+"\n"+"\n"
                        +"* Tomé tiempo para mi familia - "+'"'+tomTiemFam+'"'+"\n"+"\n"
                        +"* Asistí al grupo de intercesión - "+'"'+asisGrupInter+'"');
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDetailsPersonasMinistro() {
        mRef.child("encuentrodeamistad").child("usuarios").child(strKeyChild).child(strDateChild).child("PERSONAS QUE MINISTRO").addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String per1 = "* "+snapshot.child("persona1").getValue(String.class);
                String per2 = "* "+snapshot.child("persona2").getValue(String.class);
                String per3 = "* "+snapshot.child("persona3").getValue(String.class);
                String per4 = "* "+snapshot.child("persona4").getValue(String.class);
                String per5 = "* "+snapshot.child("persona5").getValue(String.class);
                String per6 = "* "+snapshot.child("persona6").getValue(String.class);

                String nper1 = per1+ "\n" + "\n";
                String nper2 = per2+ "\n" + "\n";
                String nper3 = per3+ "\n" + "\n";
                String nper4 = per4+ "\n" + "\n";
                String nper5 = per5+ "\n" + "\n";
                String nper6 = per6;


                if (nper1.equals("* "+"\n" + "\n")){
                    nper1 = "";
                }
                if (nper2.equals("* "+"\n" + "\n")){
                    nper2 = "";
                }
                if (nper3.equals("* "+"\n" + "\n")){
                    nper3 = "";
                }
                if (nper4.equals("* "+"\n" + "\n")){
                    nper4 = "";
                }
                if (nper5.equals("* "+"\n" + "\n")){
                    nper5 = "";
                }
                if (nper6.equals("* ")){
                    nper6 = "";
                }

                tPersonaMinistro.setText(nper1+nper2+nper3+nper4+nper5+nper6);

                /*tPersonaMinistro.setText(per1 + "\n" + "\n"
                        + per2 + "\n" + "\n"
                        + per3 + "\n" + "\n"
                        + per4 + "\n" + "\n"
                        + per5 + "\n" + "\n"
                        + per6 + "\n" + "\n");*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDetailsContactoCartas() {
        mRef.child("encuentrodeamistad").child("usuarios").child(strKeyChild).child(strDateChild).child("CONTACTO ENTREGA DE CARTAS").addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String cuanrecibio = snapshot.child("Cuántas cartas recibió").getValue(String.class);
                String espatesti = snapshot.child("Espacio para testimonios").getValue(String.class);
                String harecicartassemana = snapshot.child("Ha recibido cartas para entregar esta semana").getValue(String.class);
                String horacontact = snapshot.child("Horario en el que se le puede contactar").getValue(String.class);
                String lasentresemana = snapshot.child("Las entregó esta semana").getValue(String.class);
                String motivonopuentre = snapshot.child("Motivo por el cual no pudo entregarla").getValue(String.class);
                String nechablarpas = snapshot.child("Necesita hablar con sus pastores").getValue(String.class);

                tContactoEntregaCartas.setText("¿Ha recibido cartas para entregar esta semana? - "+'"'+harecicartassemana+'"'+"\n" + "\n"
                        +"¿Cuántas cartas recibio? - "+'"'+cuanrecibio+'"'+"\n" + "\n"
                        +"¿Las entregó esta semana? - "+'"'+lasentresemana+'"'+"\n" + "\n"
                        +"Motivo por el cual no pudo entregarla"+"\n"+"* "+motivonopuentre+"\n" + "\n"
                        +"¿Necesita hablar con sus pastores? - "+'"'+nechablarpas+'"'+"\n" + "\n"
                        +"Horario en el que se le puede contactar"+"\n"+"* "+horacontact);
                tEspacioTestimonios.setText(espatesti);


                layoutAntes.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
