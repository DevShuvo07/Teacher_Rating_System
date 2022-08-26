package com.example.teacher_rating;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    final String TAG = this.getClass().getName();

    private EditText mTextEmail;
    private EditText mTextPassword;
    private Button mButtonLogin;
    private TextView mTextViewregister,mPresshere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("Sign In Activity");

        mAuth = FirebaseAuth.getInstance();

        mTextEmail = (EditText) findViewById(R.id.edittext_Email);
        mTextPassword = (EditText) findViewById(R.id.edittext_Password);
        mButtonLogin = (Button) findViewById(R.id.button_Login);
        mTextViewregister = (TextView) findViewById(R.id.edittext_register);
        mPresshere = (TextView) findViewById(R.id.edittext_here);
        progressBar = findViewById(R.id.progressbarID);

        mButtonLogin.setOnClickListener(this);
        mTextViewregister.setOnClickListener(this);
        mPresshere.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edittext_register:

                Intent Intent = new Intent(getApplicationContext(), Registration.class);
                startActivity(Intent);

                return;

            case R.id.button_Login:

                UserLogin();

                return;
        }
    }

    private void UserLogin() {

        final String Email = mTextEmail.getText().toString().trim();
        final String Password = mTextPassword.getText().toString().trim();

        if(Email.isEmpty())
        {
            mTextEmail.setError("Enter an email address");
            mTextEmail.requestFocus();
            return;
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches())
        {
            mTextEmail.setError("Enter a valid email address");
            mTextEmail.requestFocus();
            return;
        }
        if(Password.isEmpty())
        {
            mTextPassword.setError("Enter a password");
            mTextPassword.requestFocus();
            return;
        }
        if (Password.length()<6)
        {
            mTextPassword.setError("Minimum length of Password should be 6");
            mTextPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful())
                {
                    Intent Intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(Intent);
                }
                else {
                    Toast.makeText(Login.this, "Login Error", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
    boolean twice = false;
    @Override
    public void onBackPressed(){
        Log.d(TAG,"Click");
        if (twice == true)
        {
            Intent Intent = new Intent(android.content.Intent.ACTION_MAIN);
            Intent.addCategory(Intent.CATEGORY_HOME);
            Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(Intent);
            finish();
            System.exit(0);

        }
        twice = true;
        Log.d(TAG,"twice : "+ twice);
        Toast.makeText(Login.this, "Please press BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"twice : "+ twice);
                twice = false;
            }
        }, 3000);

    }
}


