package com.bloodapp.blood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


    public class login extends AppCompatActivity {

        private EditText inputEmail, inputPassword;
        private FirebaseAuth auth;
        private ProgressBar progressBar;
        public Button btnLogin;
        private String nemad = "admin@gmail.com";


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            auth = FirebaseAuth.getInstance();

            if (auth.getCurrentUser() != null) {
                startActivity(new Intent(login.this, RecieverHome.class));
                finish();
            }

            setContentView(R.layout.activity_login);


            inputEmail =  findViewById(R.id.mail);
            inputPassword =  findViewById(R.id.pass);
            progressBar = findViewById(R.id.progressBar);
            btnLogin =  findViewById(R.id.button3);

            auth = FirebaseAuth.getInstance();


            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String email = inputEmail.getText().toString();
                    final String password = inputPassword.getText().toString();

                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);

                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    progressBar.setVisibility(View.GONE);
                                    if (!task.isSuccessful()) {
                                        if (password.length() < 6) {
                                            inputPassword.setError(getString(R.string.minimum_password));
                                        } else {
                                            Toast.makeText(login.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        if (email.equals(nemad))
                                        {
                                            Intent intent= new Intent(login.this,AdminPage.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else {
                                            Intent intent = new Intent(login.this, RecieverHome.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }
                            });
                }
            });
        }
    public void clicked(View v)
    {
        Intent intent = new Intent(login.this, register.class);
        startActivity(intent);
    }

    public void resetpwds(View view)
    {
        Intent intent = new Intent(login.this,ResetPassword.class);
        startActivity(intent);
    }
}
