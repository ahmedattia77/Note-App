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

public class createAccount_activity extends AppCompatActivity {

    TextInputLayout email , password , confirm_password;
    TextView login;
    EditText email2;
    ProgressBar progressBar;
    MaterialButton create_account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        email = findViewById(R.id.createAccount_layout_email);
        password = findViewById(R.id.createAccount_layout_password);
        confirm_password = findViewById(R.id.createAccount_layout_confirm_password);
        create_account = findViewById(R.id.createAccount_create_account_bu);
        login = findViewById(R.id.createAccount_login_tv);
        progressBar = findViewById(R.id.createAccount_progressbar);


        create_account.setOnClickListener(v-> createAccount());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(createAccount_activity.this, com.example.nonote.login.class));
            }
        });

    }

    void createAccount(){

        //String email_ = email2.getText().toString();
        String email_ = email.getEditText().getText().toString();
        String pass_ = password.getEditText().getText().toString();
        String con_pass_ = confirm_password.getEditText().getText().toString();

        boolean is_valid = isValid(email_ , pass_ , con_pass_);
        if(!is_valid)
            return;
        create_account_firebase(email_ , pass_);


    }

     void create_account_firebase(String email, String pass) {
        changeInProgress(true);
         FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
         firebaseAuth.createUserWithEmailAndPassword(email , pass).addOnCompleteListener(createAccount_activity.this,
                 new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()){
                            firebaseAuth.getCurrentUser().sendEmailVerification();
                            firebaseAuth.signOut();
                            finish();

                         }else{
                             Toast.makeText(createAccount_activity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                             changeInProgress(false);
                         }
                     }
                 }
         );

    }

    void changeInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            create_account.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            create_account.setVisibility(View.VISIBLE);
        }

    }

    boolean isValid(String email , String password , String confirm_password) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            this.email.setError("email is valid");
            return false;
        }

        if (password.length() <= 6) {
            this.password.setError("password valid");
            return false;
        }

        if (!confirm_password.equals(password)) {
            this.confirm_password.setError("password not matched");
            return false;
        }

       return true;
    }
}