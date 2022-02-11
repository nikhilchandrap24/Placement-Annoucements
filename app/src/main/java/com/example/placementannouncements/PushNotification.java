package com.example.placementannouncements;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class PushNotification extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("token",s);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("DeviceTokens").document().set(tokenData);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("PLACEMENT_NOTIFICATION", "My Notification", NotificationManager.IMPORTANCE_HIGH);
            getSystemService(NotificationManager.class).createNotificationChannel(notificationChannel);
            Notification.Builder builder = new Notification.Builder(getApplicationContext(),"PLACEMENT_NOTIFICATION")
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_normal)
                    .setAutoCancel(true);
            NotificationManagerCompat.from(getApplicationContext()).notify(1,builder.build());
        }
    }
}
