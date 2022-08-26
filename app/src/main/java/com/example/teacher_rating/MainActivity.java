package com.example.teacher_rating;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
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

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
