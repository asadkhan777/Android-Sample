package com.moengage.sample.java;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import com.moe.pushlibrary.utils.MoEHelperConstants;
import com.moengage.core.MoEUtils;
import com.moengage.pushbase.MoEPushHelper;
import java.util.Map;

public class SampleNotificationBuilder {

  private Context context;
  private Map<String, String> pushPayload;

  public SampleNotificationBuilder(Context context, Map<String, String> pushPayload){
    this.context = context;
    this.pushPayload = pushPayload;
  }

  public void buildAndShowNotification(){
    if (pushPayload.isEmpty()) return;
    // if a silent push is received from MoEngage Platform do not take any action, simply pass
    // the payload to the SDK
    if (MoEPushHelper.getInstance().isFromMoEngagePlatform(pushPayload)
        && MoEPushHelper.getInstance().isSilentPush(pushPayload)) {
      MoEPushHelper.getInstance().handlePushPayload(context, pushPayload);
      return;
    }
    NotificationCompat.Builder builder = new NotificationCompat.Builder(context,
        "moe_default_channel")
        .setContentTitle(pushPayload.get(MoEHelperConstants.GCM_EXTRA_TITLE))
        .setContentTitle(pushPayload.get(MoEHelperConstants.GCM_EXTRA_CONTENT))
        .setSmallIcon(R.drawable.icon);
    Intent intent = new Intent(context, MainActivity.class);
    // add the push payload as extras to the pending intent this is important for click tracking.
    intent.putExtras(MoEUtils.convertMapToBundle(pushPayload));
    PendingIntent pendingIntent = PendingIntent.getActivity(context, 123, intent,
        PendingIntent.FLAG_UPDATE_CURRENT);
    builder.setContentIntent(pendingIntent);
    NotificationManager notificationManager =
        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify(9808,builder.build());
    if (MoEPushHelper.getInstance().isFromMoEngagePlatform(pushPayload)) {
      MoEPushHelper.getInstance().logNotificationReceived(context, pushPayload);
    }
  }
}