package com.example.utsav;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        finishAndRemoveTask();
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    public void Click(View v)
    {
        EditText e1 = findViewById(R.id.editText10);

        EditText e2 = findViewById(R.id.editText12);

        String s1 = e1.getText().toString();
        String s2 = e2.getText().toString();
        signin(s1,s2);
    }
    public void signin(String email, String Pass)
    {
        final String e = email;
        mAuth.signInWithEmailAndPassword(email, Pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent i = new Intent(LoginActivity.this, CashOrOnline.class);
                            i.putExtra("email",e);
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(i);

                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

}

