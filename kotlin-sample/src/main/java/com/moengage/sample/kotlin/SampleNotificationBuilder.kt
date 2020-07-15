package com.moengage.sample.kotlin

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.moe.pushlibrary.utils.MoEHelperConstants
import com.moengage.core.MoEUtils
import com.moengage.pushbase.MoEPushHelper

class SampleNotificationBuilder(private val context: Context, private val pushPayload: Map<String,
    String>) {
  fun buildAndShowNotification() {
    if (pushPayload.isEmpty()) return
    // if a silent push is received from MoEngage Platform do not take any action, simply pass
    // the payload to the SDK
    if (MoEPushHelper.getInstance().isFromMoEngagePlatform(pushPayload) && MoEPushHelper.getInstance().isSilentPush(pushPayload)){
      MoEPushHelper.getInstance().handlePushPayload(context, pushPayload)
      return
    }
    // create the notification and display to the user and pass the impression to MoEngage SDK.
    val notificationBuilder = NotificationCompat.Builder(context, "moe_default_channel")
    notificationBuilder.setContentTitle(pushPayload[MoEHelperConstants.GCM_EXTRA_TITLE])
        .setContentTitle(pushPayload[MoEHelperConstants.GCM_EXTRA_CONTENT])
        .setSmallIcon(R.drawable.icon)
    val intent = Intent(context, MainActivity::class.java)
    // add the push payload as extras to the pending intent this is important for click tracking.
    intent.putExtras(MoEUtils.convertMapToBundle(pushPayload))
    val pendingIntent = PendingIntent.getActivity(context, 123, intent,
        PendingIntent.FLAG_UPDATE_CURRENT)
    notificationBuilder.setContentIntent(pendingIntent)
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as
            NotificationManager
    notificationManager.notify(9808, notificationBuilder.build())
    if (MoEPushHelper.getInstance().isFromMoEngagePlatform(pushPayload)) {
      MoEPushHelper.getInstance().logNotificationReceived(context, pushPayload)
    }
  }
}