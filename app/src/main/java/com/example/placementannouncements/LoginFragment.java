package com.example.placementannouncements;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginFragment extends Fragment {
    FirebaseAuth auth;
    private EditText etEmail,etPassword;
    private Button login;
    ProgressBar progressBar;
    FirebaseDatabase firebaseDatabase;
    ArrayList<String> users = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle)
    {
        View view=inflater.inflate(R.layout.activity_login_fragment,viewGroup,false);
        etEmail = view.findViewById(R.id.etLoginEmail);
        etPassword = view.findViewById(R.id.etLoginPassword);
        login = view.findViewById(R.id.btnLogin);
        progressBar = view.findViewById(R.id.progressBar);
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
                    signIn(email,password);
                }
            }
        });

        return view;

    }

    public void signIn (String email, String password){
        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(getContext(), "Login Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(),UserActivity.class));


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
