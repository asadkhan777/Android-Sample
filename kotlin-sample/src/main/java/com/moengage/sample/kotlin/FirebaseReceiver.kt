package com.moengage.sample.kotlin

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.moengage.push.PushManager

/**
 * @author Umang Chamaria
 * Date: 15/07/20
 */
class FirebaseReceiver : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        SampleNotificationBuilder(
            applicationContext,
            remoteMessage.data
        ).buildAndShowNotification();
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        PushManager.getInstance().refreshToken(applicationContext, token)
    }
}