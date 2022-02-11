package com.example.placementannouncements;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FirebaseDatabase firebaseDatabase;
    ArrayList<String> admins = new ArrayList<>();
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();
        firebaseMessaging.subscribeToTopic("new_user_forums");

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if(user!=null){

            firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference();
            databaseReference.child("Admin").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        for(DataSnapshot i : snapshot.getChildren()){
                            Admin admin = i.getValue(Admin.class);
                            assert admin !=null;
                            admins.add(admin.getEmail());

                        }
                        if(admins.contains(user.getEmail())){
                            startActivity(new Intent(getApplicationContext(),AdminActivity.class));
                        }
                        else{
                            startActivity(new Intent(getApplicationContext(),UserActivity.class));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            setContentView(R.layout.activity_main);
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            // add default fragment
            fragmentTransaction.add(R.id.frame,new LoginFragment());
            fragmentTransaction.commit();

        }

    }


    public void login(View view) {

        fragmentTransaction=fragmentManager.beginTransaction();
        // add default fragment
        fragmentTransaction.replace(R.id.frame,new LoginFragment());
        fragmentTransaction.commit();
    }

    public void signup(View v){

        fragmentTransaction=fragmentManager.beginTransaction();
        // add default fragment
        fragmentTransaction.replace(R.id.frame,new SignUpFragment());
        fragmentTransaction.commit();



    }
    public void  adminLogin(View v){


        fragmentTransaction=fragmentManager.beginTransaction();
        // add default fragment
        fragmentTransaction.replace(R.id.frame,new AdminLoginFragment());
        fragmentTransaction.commit();

    }


}