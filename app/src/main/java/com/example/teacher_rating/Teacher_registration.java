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

public class Teacher_registration extends AppCompatActivity implements View.OnClickListener{

    private EditText mfacultycode,mEditTextEmail,mName,mroom,mdesignation;
    private Button mButtonRegistration;
    private FirebaseAuth mAuth;
    private ProgressBar ProgressBar;
    private DatabaseReference databaseReference;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_registration);
        this.setTitle("Sign In Activity");


        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("University").child("teacher");

        ProgressBar = findViewById(R.id.progressbarID);
        mName = (EditText) findViewById(R.id.edit_text_name);
        mdesignation = (EditText) findViewById(R.id.edit_text_designation);
        mfacultycode = (EditText) findViewById(R.id.edittext_faculty_code);
        mroom = (EditText) findViewById(R.id.edittext_room);
        mEditTextEmail= (EditText) findViewById(R.id.edittext_Email);
        mButtonRegistration = (Button) findViewById(R.id.button_addteacher);

        userID = mAuth.getCurrentUser().getUid();

        mButtonRegistration.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.button_Registration:

                UserRegister();

                break;
        }

    }

    private void UserRegister() {

        final String Email = mEditTextEmail.getText().toString().trim();
        final String Room = mroom.getText().toString().trim();
        final String Name = mName.getText().toString().trim();
        final String Designation = mdesignation.getText().toString().trim();
        final String Faculty_Code = mfacultycode.getText().toString().trim();

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

        if (Name.isEmpty() || Faculty_Code.isEmpty()|| Room.isEmpty()){
            mName.setError("Enter teacher name");
            mfacultycode.setError("Enter teacher faculty code");
            mroom.setError("Enter teacher room number");
            return;
        }
        Map userInfo = new HashMap();
        userInfo.put("name", mName);
        userInfo.put("email", mEditTextEmail);
        userInfo.put("room", mroom);
        userInfo.put("designation", mdesignation);
        userInfo.put("faculty_code", mfacultycode);
        databaseReference.updateChildren(userInfo);




    }
}