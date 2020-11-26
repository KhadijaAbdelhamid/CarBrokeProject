package com.kad.carbrokefinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class ServiceProviderActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private EditText mUsername,  mPassword;
    private Button mButtonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider);

        mUsername = findViewById(R.id.etxtUsername);
        mPassword = findViewById(R.id.etxtPassword);
        mButtonLogin  = findViewById(R.id.buttonLogin);

        mAuth= FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null) {

                    Intent intent = new Intent(ServiceProviderActivity.this, ServiceProMapsActivity.class);
                    startActivity(intent);
                    finish();
                    // return;
                }
            }
        };

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mUsername.getText().toString();
                String password = mPassword.getText().toString();

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(ServiceProviderActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful())
                        { Toast.makeText(ServiceProviderActivity.this,R.string.error_signIn,Toast.LENGTH_LONG).show();}
                        else {
                            Intent loginIntent = new Intent(ServiceProviderActivity.this,ServiceProMapsActivity.class);
                            startActivity(loginIntent);
                            finish();
                            // return;
                        }
                    }
                });

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
