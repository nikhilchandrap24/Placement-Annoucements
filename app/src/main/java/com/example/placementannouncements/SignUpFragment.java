package com.example.placementannouncements;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpFragment extends Fragment {
    EditText etSignUpEmail,etSignUpPassword,etFullName,etSignUpConfirmPassword,etSignUpBranch,etSignUpRollNumber;
    Button btnSignUp;
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle)
    {
        View view=inflater.inflate(R.layout.activity_sign_up_fragment,viewGroup,false);
        etFullName = view.findViewById(R.id.etSignUpFullName);
        etSignUpEmail = view.findViewById(R.id.etSignUpEmail);
        etSignUpPassword = view.findViewById(R.id.etSignUpPassword);
        etSignUpConfirmPassword = view.findViewById(R.id.etSignUpConfirmPassword);
        etSignUpBranch = view.findViewById(R.id.etBranch);
        etSignUpRollNumber = view.findViewById(R.id.etRollNumber);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName,email,password,confirmPassword,rollNumber,branch;
                fullName = etFullName.getText().toString().trim();
                email = etSignUpEmail.getText().toString().trim();
                password = etSignUpPassword.getText().toString().trim();
                rollNumber = etSignUpRollNumber.getText().toString().trim();
                branch = etSignUpBranch.getText().toString().trim();
                confirmPassword = etSignUpConfirmPassword.getText().toString().trim();

                if(!password.equals(confirmPassword)){
                    Toast.makeText(getContext(), "Passwords don't match!", Toast.LENGTH_SHORT).show();
                }
                else{
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(getContext(), "Account created successfully!", Toast.LENGTH_SHORT).show();
                            Users user = new Users(email,fullName,rollNumber,branch);
                            FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(), "Successfully saved data!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }


            }
        });
        return view;

    }
}