package com.example.placementannouncements;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class UpdateAnnouncement extends AppCompatActivity {
    EditText etUpdateTitle,etUpdateDate,etUpdateEligibility,etUpdateDescription,etUpdateLink;
    Button btnUpdate;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_announcement);
        etUpdateTitle = findViewById(R.id.etUpdateTitle);
        etUpdateDate = findViewById(R.id.etUpdateDate);
        etUpdateEligibility = findViewById(R.id.etUpdateEligibility);
        etUpdateDescription = findViewById(R.id.etUpdateDescription);
        etUpdateLink = findViewById(R.id.etUpdateLink);
        btnUpdate = findViewById(R.id.btnUpdate);
        progressBar = findViewById(R.id.progressBarUpdate);
        progressBar.setVisibility(View.INVISIBLE);
        AdminView announcement = (AdminView) getIntent().getSerializableExtra("announcement");
        String id = getIntent().getStringExtra("id");

        etUpdateTitle.setText(announcement.getTitle());
        etUpdateDate.setText(announcement.getEventDate());
        etUpdateEligibility.setText(announcement.getBranch());
        etUpdateDescription.setText(announcement.getDescription());
        etUpdateLink.setText(announcement.getLink());
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String title,date,eligibility,description,link;
                title = etUpdateTitle.getText().toString().trim();
                date = etUpdateDate.getText().toString().trim();
                eligibility = etUpdateEligibility.getText().toString().trim();
                description = etUpdateDescription.getText().toString().trim();
                link = etUpdateLink.getText().toString().trim();
                AdminView adminView = new AdminView(title,date,eligibility,description,link);
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("title",title);
                hashMap.put("date",date);
                hashMap.put("eligibility",eligibility);
                hashMap.put("description",description);
                hashMap.put("link",link);
                hashMap.put("id",id);
                FirebaseFirestore.getInstance().collection("placement_info").document(id).set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),AdminActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to update", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }
}