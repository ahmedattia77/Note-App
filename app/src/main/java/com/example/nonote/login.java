package com.example.nonote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    TextView createAccount;
    TextInputLayout email , password ;
    ProgressBar progressBar;
    MaterialButton login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.log_layout_email);
        password = findViewById(R.id.log_layout_password);
        login = findViewById(R.id.lod_create_account_bu);
        progressBar = findViewById(R.id.log_progressbar);
        createAccount = findViewById(R.id.log_createAccount_tv);


        login.setOnClickListener((v)-> loginUser());

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this,createAccount_activity.class));
            }
        });
    }

    private void loginUser(){
        String email_ = email.getEditText().getText().toString();
        String password_ = password.getEditText().getText().toString();
        if (!isValid(email_ , password_))
            return;
        loginFireBaseUser(email_ , password_);
    }

    private void loginFireBaseUser(String email , String password ){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        changeInProgress(true);
        firebaseAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);
                if (task.isSuccessful() ){
                    // move to main activity
                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                        startActivity(new Intent(login.this , MainActivity.class));
                        Toast.makeText(login.this, "login successfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(login.this, "email is not verified", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(login.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void changeInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            login.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
        }

    }

    private boolean isValid(String email , String password) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            this.email.setError("email is valid");
            return false;
        }

        if (password.length() <= 5) {
            this.password.setError("password is valid");
            return false;
        }
        return true;
    }
}