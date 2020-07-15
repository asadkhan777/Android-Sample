package com.moengage.sample.kotlin

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.core.app.NotificationCompat.Builder
import com.moengage.core.ConfigurationProvider
import com.moengage.pushbase.push.PushMessageListener

/**
 *
 * @author Umang Chamaria
 * Date: 2019-05-09
 */
class CustomPushMessageListener : PushMessageListener() {

  // decide whether notification should be shown or not. If super() returns false this method
  // should return false. In case super() isn't called notification will not be displayed.
  override fun isNotificationRequired(context: Context, payload: Bundle): Boolean {
    return false
  }

  //
  override fun onNotificationReceived(context: Context, payload: Bundle) {
    super.onNotificationReceived(context, payload)
    // pass payload to your notification builder. This method is called on a worker thread.
    val payloadMap = HashMap<String, String>()
    for (key in payload.keySet()) {
      payloadMap[key] = payload.getString(key)
    }
    SampleNotificationBuilder(context, payloadMap).buildAndShowNotification()
  }

  override fun onHandleRedirection(activity: Activity?, payload: Bundle?) {
    // add code for redirection on notification click, will only be triggered if notification is
    // delivered via push amp+
  }
}
