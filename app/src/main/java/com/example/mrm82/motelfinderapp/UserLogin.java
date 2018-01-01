package com.example.mrm82.motelfinderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by mrm82 on 30/12/2017.
 */

public class UserLogin extends AppCompatActivity {

    EditText edtUser, edtPass;
    Button btnLogin,btnRegister;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        Controls();
        mAuth=FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserLogin.this,Register.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtUser.getText().toString();
                String pass = edtPass.getText().toString();
                UserSignIn(email,pass);
            }
        });
    }
    private void Controls(){

        edtUser=findViewById(R.id.edtUserEmail);
        edtPass=findViewById(R.id.edtPassword);
        btnLogin=findViewById(R.id.btnLogin);
        btnRegister=findViewById(R.id.btnRegister);

    }

    private void UserSignIn(String email, String pass){

        mAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(UserLogin.this, "Log in success", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(UserLogin.this,MainActivity.class);
                            startActivity(i);
                            FirebaseUser user =mAuth.getCurrentUser();

                        }else {
                            Log.w("error", "signInWithEmail:failure", task.getException());
                            Toast.makeText(UserLogin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
