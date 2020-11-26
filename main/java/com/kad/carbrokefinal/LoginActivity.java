package com.kad.carbrokefinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private EditText mUsername,  mPassword;
    private Button mButtonLogin, mButtonServicePro;
    private TextView mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);

    mUsername = findViewById(R.id.etxtUsername);
    mPassword = findViewById(R.id.etxtPassword);
    mButtonLogin  = findViewById(R.id.buttonLogin);
    mRegister = findViewById(R.id.txtViewRegister);
    mButtonServicePro = findViewById(R.id.buttonServicePro);

    mAuth= FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null) {

                    Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        mRegister.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(registerIntent);
            finish();
            return;
        }

    });
    mButtonLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = mUsername.getText().toString();
            String password = mPassword.getText().toString();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful())
                    { Toast.makeText(LoginActivity.this,R.string.error_signIn,Toast.LENGTH_LONG).show();}
                    else {
                        Intent loginIntent = new Intent(LoginActivity.this,MapsActivity.class);
                        startActivity(loginIntent);
                        finish();
                       // return;
                    }
                }
            });

        }
    });
    mButtonServicePro.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent serviceProIntent = new Intent(LoginActivity.this,ServiceProviderActivity.class);
            startActivity(serviceProIntent);
            finish();
           // return;
        }
    });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}
