package com.vivalavida.informeencuentrodeamistad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    EditText editEmail,editContra;
    Button loginBtn;
    ProgressBar progressBar;
    ConstraintLayout rootLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        rootLayout = findViewById(R.id.root_layout);

        editEmail = findViewById(R.id.edit_email);
        editContra = findViewById(R.id.edit_contra);
        loginBtn = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.pro);

        if (mUser != null){
            //checkDayMonth
            /*Intent i;
            if (dayOfMonth.equalsIgnoreCase("1")){
                i = new Intent(Login_Activity.this, firstDayOfMonthActivity.class);
            }else{
                i = new Intent(Login_Activity.this, MainActivity.class);
            }*/
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
            finish();

        }else{
            rootLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserLogin();
            }
        });

    }

    private void UserLogin () {
        String email = editEmail.getText().toString().trim();
        String password = editContra.getText().toString().trim();

        if (email.isEmpty()) {
            editEmail.setError("Campo Requerido");
            editEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Porfavor ingrese un correo valido");
            editEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editContra.setError("Campo Requerido");
            editContra.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    /*Intent i;
                    if (dayOfMonth.equalsIgnoreCase("1")){
                        i = new Intent(Login_Activity.this, firstDayOfMonthActivity.class);
                    }else{
                        i = new Intent(Login_Activity.this, MainActivity.class);
                    }*/
                    Intent i = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();

                }else{
                    Toast.makeText(LoginActivity.this, "error"+task.getException(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
