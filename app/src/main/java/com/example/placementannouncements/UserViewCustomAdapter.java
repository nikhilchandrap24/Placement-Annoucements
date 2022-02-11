package com.example.placementannouncements;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class UserViewCustomAdapter extends ArrayAdapter<UserView> {
    public UserViewCustomAdapter(@NonNull Context context, ArrayList<UserView> userList){
        super(context,0,userList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentView = convertView;

        if(currentView == null){
            currentView = LayoutInflater.from(getContext()).inflate(R.layout.user_custom_listview,parent,false);
        }
        UserView currentItemPosition = getItem(position);
        assert  currentItemPosition!=null;
        TextView title = currentView.findViewById(R.id.tvUserTitle);
        title.setText(currentItemPosition.getTitle());
        TextView date = currentView.findViewById(R.id.tvDate);
        date.setText("Deadline : "+currentItemPosition.getDate());
        TextView eligibility = currentView.findViewById(R.id.tvEligibility);
        eligibility.setText("Eligibility : "+currentItemPosition.getEligibility());
        TextView description = currentView.findViewById(R.id.tvDescription);
        description.setText(currentItemPosition.getDescription());
        Button btnApply = currentView.findViewById(R.id.btnApply);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(currentItemPosition.getLink())));
            }
        });

        return currentView;
    }

}
