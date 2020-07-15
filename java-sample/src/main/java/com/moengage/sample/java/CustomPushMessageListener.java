package com.moengage.sample.java;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.moengage.pushbase.push.PushMessageListener;
import java.util.HashMap;

public class CustomPushMessageListener extends PushMessageListener {

  // decide whether notification should be shown or not. If super() returns false this method
  // should return false. In case super() isn't called notification will not be displayed.
  @Override public boolean isNotificationRequired(Context context, Bundle payload) {
    return false;
  }

  @Override public void onNotificationReceived(Context context, Bundle payload) {
    super.onNotificationReceived(context, payload);
    // pass payload to your notification builder. This method is called on a worker thread.
    HashMap<String, String> payloadMap = new HashMap<>();
    for (String key : payload.keySet()) {
      payloadMap.put(key, payload.getString(key));
    }
    new SampleNotificationBuilder(context, payloadMap).buildAndShowNotification();
  }

  @Override public void onHandleRedirection(Activity activity, Bundle payload) {
    // add code for redirection on notification click, will only be triggered if notification is
    // delivered via push amp+
  }
}