package com.grappus.android.basemvvm.notifications

import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.garg.aman.notificationexample.NotificationDataPayload
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.grappus.android.basemvvm.utils.Constants.Broadcasts.NOTIFICATION_BROADCAST
import com.grappus.android.basemvvm.utils.Constants.RequestArgs.ARG_EXTRA_1
import com.grappus.android.basemvvm.utils.JsonUtils
import com.grappus.android.basemvvm.views.activities.home.MainActivity

class FCMMessagingService : FirebaseMessagingService() {
    private val TAG = FCMMessagingService::class.java.simpleName

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Log.e("FCM Token: ", token)

        /*TODO sync FCM token with backend or save token in prefrences*/
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage!!.notification != null) {
            //Notification Payload
            Log.d(TAG, "Notification Body: " + remoteMessage.notification!!.body!!)
            showNotificationFromDataPayload(remoteMessage)
            /*Send Local broadcast to receive data when app is in foreground*/
        }

        //Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            //Data Payload
            Log.d(TAG, "Notification Data: " + JsonUtils.jsonify(remoteMessage.data))

            /*Send Local broadcast to receive data when app is in foreground*/
            /*TODO handle the broadcast at desired activity or fragment*/
            val broadcastIntent = Intent(NOTIFICATION_BROADCAST)
            broadcastIntent.putExtra(ARG_EXTRA_1, JsonUtils.jsonify(getNotificationDataPayload(remoteMessage.data)))
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(broadcastIntent)

            showNotificationFromDataPayload(remoteMessage)
        }
    }

    private fun showNotificationFromDataPayload(remoteMessage: RemoteMessage) {
        /*Change the Activity name inside intent constructor according to requirements*/
        val intent = Intent(this, MainActivity::class.java)
        val imageUrl = remoteMessage.data["image_url"] /*image url is used to show Image Notifications*/
        val notificationDisplayUtils = NotificationDisplayUtils(this)
        notificationDisplayUtils.createNotification(intent, remoteMessage.data, imageUrl, true)
    }

    private fun getNotificationDataPayload(map: Map<String, String>?): NotificationDataPayload? {
        var dataPayload: NotificationDataPayload? = null
        if (map != null && map.isNotEmpty()) {
            dataPayload = NotificationDataPayload()
            for ((key, value) in map) {
                if (key == "type")
                    dataPayload.type = value
                else if (key == "id")
                    dataPayload.id = value
                else if (key == "title")
                    dataPayload.title = value
                else if (key == "body")
                    dataPayload.body = value
                else if (key == "image_url")
                    dataPayload.imageUrl = value
            }
        }
        return dataPayload
    }
}
