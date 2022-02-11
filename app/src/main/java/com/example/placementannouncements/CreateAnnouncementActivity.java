package com.example.placementannouncements;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class CreateAnnouncementActivity extends AppCompatActivity {
    EditText etTitle,etEventDate,etEligibility,etDescription,etLink;
    Button btnCreate;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_announcement);
        getIntent();
        etTitle=findViewById(R.id.etCreateTitle);
        etEventDate = findViewById(R.id.etCreateDate);
        etEligibility = findViewById(R.id.etCreateEligibility);
        etDescription = findViewById(R.id.etCreateDescription);
        etLink = findViewById(R.id.etLink);
        progressBar = findViewById(R.id.progressBarCreateAnn);
        progressBar.setVisibility(View.INVISIBLE);
        btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String title,eventDate,eligibility,description,link;
                title = etTitle.getText().toString().trim();
                eventDate =etEventDate.getText().toString().trim();
                eligibility = etEligibility.getText().toString().trim();
                description = etDescription.getText().toString().trim();
                link = etLink.getText().toString().trim();
                HashMap<String, Object> placement_info = new HashMap<>();
                placement_info.put("title",title);
                placement_info.put("date",eventDate);
                placement_info.put("eligibility",eligibility);
                placement_info.put("description",description);
                placement_info.put("link",link);

                FirebaseFirestore.getInstance().collection("placement_info").add(placement_info).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Announcement made successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),AdminActivity.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to add data", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}