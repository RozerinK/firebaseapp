package com.firebaseapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements OnCompleteListener<AuthResult> {


    private EditText edtEmail = null, edtPassword = null;
    private FirebaseAuth auth = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        FirebaseAuth auth = FirebaseAuth.getInstance();



        initEvent();

    }

    public void initView() {

    }


    public void initEvent() {

    }
    @Override
    public void onComplete (@NonNull Task< AuthResult > task) {
        if (task.isSuccessful()) {
            Toast.makeText(getApplicationContext(), "kayıt başarılı", Toast.LENGTH_SHORT).show();

            auth.getCurrentUser();// null ise kullanıcıya şifre sorduracaksın
        }
    }
}
