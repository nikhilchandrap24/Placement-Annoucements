package com.example.placementannouncements;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    Button btnCreate,btnAdminLogout;
    ListView lv;
    ArrayList<AdminView> arrayList = new ArrayList<>();
    private long pressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        btnCreate = findViewById(R.id.btnCreateAnnouncement);
        btnAdminLogout = findViewById(R.id.btnAdminLogout);
        btnAdminLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
        lv = findViewById(R.id.listview);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this,CreateAnnouncementActivity.class));
   }
        });

        FirebaseFirestore.getInstance().collection("placement_info").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                arrayList.clear();
                assert value!=null;
                for(DocumentSnapshot s : value){
                    AdminView adminView = new AdminView(s.getString("title"),s.getString("date"),s.getString("eligibility"),s.getString("description"),s.getString("link"));
                    adminView.setId(s.getId());
                    arrayList.add(adminView);
                }
                AdminViewCustomAdapter adapter = new AdminViewCustomAdapter(getApplicationContext(),arrayList);
                lv.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }
        });

    }
    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            this.finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}