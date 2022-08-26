package com.example.teacher_rating;

import android.arch.lifecycle.AndroidViewModel;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class UpdateProfile extends AppCompatActivity {

    private static final String TAG = "UpdateProfile";

    private FirebaseDatabase mdatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;
    private String userID;
    private ListView mListView;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);


        mListView = (ListView) findViewById(R.id.listview);

        mAuth = FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance();
        databaseReference = mdatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            showData(dataSnapshot);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                // ...
            }
        };


    }
    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            database uInfo = new database();
            uInfo.setName(ds.child(userID).getValue(database.class).getName()); //set the name
            uInfo.setEmail(ds.child(userID).getValue(database.class).getEmail()); //set the email
            uInfo.setMobile(ds.child(userID).getValue(database.class).getMobile()); //set the phone_num
            uInfo.setPassword(ds.child(userID).getValue(database.class).getPassword());//set the password


            Log.d(TAG, "showData: name : " + uInfo.getName());
            Log.d(TAG, "showData: email : " + uInfo.getEmail());
            Log.d(TAG, "showData: Mobile Number : " + uInfo.getMobile());
            Log.d(TAG, "showData: Password : " + uInfo.getPassword());


            ArrayList<String> array = new ArrayList<>();
            array.add(uInfo.getName());
            array.add(uInfo.getEmail());
            array.add(uInfo.getMobile());
            array.add(uInfo.getPassword());
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.list_content,array);
            mListView.setAdapter(arrayAdapter);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manu_layout,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.SignOutMenuId)
        {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent Intent = new Intent(getApplicationContext(),Login.class);
            startActivity(Intent);
        }
        if (item.getItemId()==R.id.profile)
        {
            Intent Intent = new Intent(getApplicationContext(),UpdateProfile.class);
            startActivity(Intent);
        }
        if (item.getItemId()==R.id.DashboardId)
        {
            Intent Intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(Intent);
        }
        if (item.getItemId()==R.id.Teacher_list)
        {
            Intent Intent = new Intent(getApplicationContext(),teacher.class);
            startActivity(Intent);
        }
        if (item.getItemId()==R.id.exit)
        {
            if (item.getItemId()==R.id.exit) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfile.this);
                builder.setTitle(R.string.app_name);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("Do you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent Intent = new Intent(android.content.Intent.ACTION_MAIN);
                                Intent.addCategory(Intent.CATEGORY_HOME);
                                Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(Intent);
                                finish();
                                System.exit(0);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        }

        return super.onOptionsItemSelected(item);
    }
}
