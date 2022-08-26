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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Adminlogin extends AppCompatActivity {

    ProgressBar progressBar;
    final String TAG = this.getClass().getName();

    private EditText mTextEmail;
    private EditText mTextPassword;
    private Button mButtonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("Sign In Activity");


        mTextEmail = (EditText) findViewById(R.id.edittext_Email);
        mTextPassword = (EditText) findViewById(R.id.edittext_Password);
        mButtonLogin = (Button) findViewById(R.id.button_Login);

        progressBar = findViewById(R.id.progressbarID);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTextEmail.getText().toString().equals("shuvo169693@gmail.com") &&
                        mTextPassword.getText().toString().equals("shuvo0705")) {
                    Intent Intent = new Intent(getApplicationContext(),AdminActivity.class);
                    startActivity(Intent);
                }else{
                    Toast.makeText(Adminlogin.this, "Please enter your correct admin email & password", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(Adminlogin.this, "Please press BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"twice : "+ twice);
                twice = false;
            }
        }, 3000);

    }
}
