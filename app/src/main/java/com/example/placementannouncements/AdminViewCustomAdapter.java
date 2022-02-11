package com.example.placementannouncements;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdminViewCustomAdapter extends ArrayAdapter<AdminView> {
    private static LayoutInflater inflater=null;
    public AdminViewCustomAdapter(@NonNull Context context, ArrayList<AdminView> arrayList) {
        super(context,0,arrayList);
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentView = convertView;

        if(currentView == null){
            currentView = inflater.inflate(R.layout.admin_custom_listview,parent,false);
        }

        AdminView currentItemPosition = getItem(position);

        assert currentItemPosition!=null;
        TextView title = currentView.findViewById(R.id.etTitle);
        title.setText(currentItemPosition.getTitle());
        TextView date = currentView.findViewById(R.id.etDate);
        date.setText("Date : "+currentItemPosition.getEventDate());
        TextView branches = currentView.findViewById(R.id.etBranches);
        branches.setText("Eligibility : "+currentItemPosition.getBranch());
        TextView description = currentView.findViewById(R.id.etDescription);
        description.setText(currentItemPosition.getDescription());
        Button update = currentView.findViewById(R.id.btnEdit);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(parent.getContext(),UpdateAnnouncement.class);
                AdminView announcement = new AdminView(currentItemPosition.getTitle(),currentItemPosition.getEventDate(),currentItemPosition.getBranch(),currentItemPosition.getDescription(),currentItemPosition.getLink());
                i.putExtra("announcement",announcement);
                i.putExtra("id",currentItemPosition.getId());
                parent.getContext().startActivity(i);
            }
        });
        Button delete = currentView.findViewById(R.id.btnDelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                builder.setTitle("Delete ?");
                builder.setMessage("Are you sure ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseFirestore.getInstance().collection("placement_info").document(currentItemPosition.getId()).delete();
                        dialogInterface.dismiss();
                        Toast.makeText(getContext(), "Deleted Successfully!", Toast.LENGTH_SHORT).show();


                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        return currentView;


    }


}
