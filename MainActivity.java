package com.firebaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements OnCompleteListener<AuthResult>, View.OnClickListener {


    private EditText edtEmail = null, edtPassword = null;
    private TextView txtEmail = null, txtPassword = null;
    private FirebaseAuth auth;
    private Button btnlogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
        auth = FirebaseAuth.getInstance();


        //    auth.signInWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString()).
        //          addOnCompleteListener(this);



    }

    public void initView() {

        txtEmail = (TextView) findViewById(R.id.activity_main_txtemail);
        txtPassword = (TextView) findViewById(R.id.activity_main_txtpassword);
        edtEmail = (EditText) findViewById(R.id.activity_main_edtemail);
        edtPassword = (EditText) findViewById(R.id.activity_main_edtpassword);
        btnlogin = (Button) findViewById(R.id.activity_main_btnlogin);

    }


    public void initEvent() {

        btnlogin.setOnClickListener(this);
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            Toast.makeText(MainActivity.this, "kayıt başarılı", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onClick(View v) {
        auth.createUserWithEmailAndPassword(edtEmail.getText().toString().trim(), edtPassword.getText().toString().trim())
                .addOnCompleteListener(this);
        Toast.makeText(MainActivity.this, auth.getCurrentUser().getEmail().toString(), Toast.LENGTH_SHORT).show();
        Intent myintent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(myintent);
    }
}