package com.moengage.sample.java;

import androidx.annotation.NonNull;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.moengage.push.PushManager;

public class FirebaseReceiver extends FirebaseMessagingService {
  @Override public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);
    new SampleNotificationBuilder(getApplicationContext(),
        remoteMessage.getData()).buildAndShowNotification();
  }

  @Override public void onNewToken(@NonNull String token) {
    super.onNewToken(token);
    PushManager.getInstance().refreshToken(getApplicationContext(), token);
  }
}
