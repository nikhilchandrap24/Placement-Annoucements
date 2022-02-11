package com.example.placementannouncements;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AdminLoginFragment extends Fragment {
    FirebaseAuth auth;
    private EditText etEmail,etPassword;
    private Button login;
    ArrayList<String> admins = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle)
    {
        View view=inflater.inflate(R.layout.activity_admin_login_fragment,viewGroup,false);
        etEmail = view.findViewById(R.id.etAdminLoginEmail);
        etPassword = view.findViewById(R.id.etAdminLoginPassword);
        login = view.findViewById(R.id.btnAdminLogin);
        progressBar = view.findViewById(R.id.progressBarAdmin);
        progressBar.setVisibility(View.INVISIBLE);
        auth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
                    Toast.makeText(getContext(), "Please enter all fields!", Toast.LENGTH_SHORT).show();
                }
                else{
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
                                if(admins.contains(email)){
                                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(getContext(), "Logged in successfully", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getContext(),AdminActivity.class));
                                            }
                                            else{
                                                Toast.makeText(getContext(), "Login failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });

        return view;

    }

}