package com.vivalavida.informeencuentrodeamistad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    DatabaseReference mRef;
    BottomNavigationView bottomNavigationView;
    Fragment fragment = null;
    ConstraintLayout antes,laExiste;
    Calendar calendar;
    int year,month,numOfTheWeek,dayOfTheMont,dayOfTheWeek;
    String nombreMes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRef = FirebaseDatabase.getInstance().getReference();

        setContentView(R.layout.host_layout);
        antes = findViewById(R.id.antes);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

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

        mRef.child("encuentrodeamistad").child("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String whatIs = snapshot.child("rol").getValue(String.class);
                if (whatIs.equals("admin")){
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
                antes.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,
                    new HomeFragment()).commit();
        }
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.more:
                        fragment = new AdminFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                fragment)
                        .commit();
                return true;
            }
        });
    }

}