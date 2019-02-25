package com.grappus.android.basemvvm.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Patterns
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.grappus.android.basemvvm.R
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class NotificationDisplayUtils(private val mContext: Context) {

    private var notificationManager: NotificationManager? = null
    private var myBitmap: Bitmap? = null


    companion object {
        private const val CHANNEL_ID = "channel_id"
        private val TAG = NotificationDisplayUtils::class.java.simpleName
    }

    fun createNotification(intent: Intent, data: Map<String, String>, imageUrl: String?, isRingToneEnabled: Boolean) {
        if (isRingToneEnabled)
            playNotificationSound();

        notificationManager = mContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        //Setting up Notification channels for android O and above
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            setupChannels()
        }

        /*Extract Bitmap for image notifications*/
        if (imageUrl != null && imageUrl.length > 4 && Patterns.WEB_URL.matcher(imageUrl).matches())
            myBitmap = getBitmapFromURL(imageUrl)


        val message = data["body"]
        val title = if (data["title"] != null) data["title"] else "Notification Testing"

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val mBuilder = NotificationCompat.Builder(mContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setSound(defaultSoundUri)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        if (myBitmap != null)
            mBuilder.setStyle(
                    NotificationCompat.BigPictureStyle()
                            .setSummaryText(message)
                            .bigPicture(myBitmap)
            )
        else
            mBuilder.setStyle(
                    NotificationCompat.BigTextStyle()
                            .setSummaryText(message)
            )


        // notificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        // notificationId is a unique int for each notification that you must define
        notificationManager!!.notify(NotificationId.getID(), mBuilder.build())
    }

    // Downloading push notification image before displaying it in the notification tray
    private fun getBitmapFromURL(strURL: String): Bitmap? {
        return try {
            val url = URL(strURL)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupChannels() {

        val adminChannelName = "channel_name"
        val adminChannelDescription = "channel_description"

        val notificationChannel: NotificationChannel
        notificationChannel = NotificationChannel(CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_LOW)
        notificationChannel.description = adminChannelDescription
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        if (notificationManager != null) {
            notificationManager!!.createNotificationChannel(notificationChannel)
        }
    }

    // Playing notification sound
    private fun playNotificationSound() {
        try {
            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val r = RingtoneManager.getRingtone(mContext, alarmSound)
            r.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
