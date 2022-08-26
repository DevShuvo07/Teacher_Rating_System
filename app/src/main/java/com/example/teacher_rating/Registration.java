package com.example.teacher_rating;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class Registration extends AppCompatActivity implements View.OnClickListener{

    private EditText mEditTextPassword,mEditTextEmail,mName,mMobile_number;
    private Button mButtonRegistration;
    private TextView mTextViewLogin;
    private FirebaseAuth mAuth;
    private ProgressBar ProgressBar;
    private DatabaseReference databaseReference;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("University").child("Student");

        ProgressBar = findViewById(R.id.progressbarID);
        mName = (EditText) findViewById(R.id.edit_text_name);
        mMobile_number = (EditText) findViewById(R.id.edittext_mobile_number);
        mEditTextPassword = (EditText) findViewById(R.id.edittext_Password);
        mEditTextEmail= (EditText) findViewById(R.id.edittext_Email);
        mButtonRegistration = (Button) findViewById(R.id.button_Registration);
        mTextViewLogin = (TextView) findViewById(R.id.edittext_Login);

        userID = mAuth.getCurrentUser().getUid();

        mButtonRegistration.setOnClickListener(this);
        mTextViewLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.edittext_Login:
                Intent Intent = new Intent(getApplicationContext(),Login.class);
                startActivity(Intent);
                break;


            case R.id.button_Registration:

                UserRegister();

                break;
        }

    }

    private void UserRegister() {

        final String Email = mEditTextEmail.getText().toString().trim();
        final String Password = mEditTextPassword.getText().toString().trim();
        final String name = mName.getText().toString().trim();
        final String mobile = mMobile_number.getText().toString().trim();

        if(Email.isEmpty())
        {
            mEditTextEmail.setError("Enter an email address");
            mEditTextEmail.requestFocus();
            return;
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches())
        {
            mEditTextEmail.setError("Enter a valid email address");
            mEditTextEmail.requestFocus();
            return;
        }
        if(Password.isEmpty())
        {
            mEditTextPassword.setError("Enter a password");
            mEditTextPassword.requestFocus();
            return;
        }
        if (Password.length()<6)
        {
            mEditTextPassword.setError("Minimum length of Password should be 6");
            mEditTextPassword.requestFocus();
            return;
        }
        if (name.isEmpty() || mobile.isEmpty()){
            mName.setError("Enter your name");
            mMobile_number.setError("Enter yout Mobile Number");
            return;
        }
        if(mobile.length()<10)
        {
            mMobile_number.setError("Minimum length of Mobile Number should be 10");
            mMobile_number.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    ProgressBar.setVisibility(View.GONE);
                    database upload = new database(name,mobile,Email,Password);
                    databaseReference.child(userID).setValue(upload);
                    Toast.makeText(Registration.this, "Sign up Successful", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent Intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(Intent);
                }
                else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(Registration.this, "User is already registered", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(Registration.this, "Sign up Error", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }
}