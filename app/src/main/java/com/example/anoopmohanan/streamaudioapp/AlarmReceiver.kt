package com.example.anoopmohanan.streamaudioapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.anoopmohanan.streamaudioapp.player.StreamAudioPlayer

import android.support.v4.app.NotificationCompat
import android.app.NotificationManager
import android.app.NotificationChannel
import android.app.PendingIntent
import android.R.id
import android.nfc.NfcAdapter.EXTRA_ID
import java.util.*


class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, receivedIntent: Intent?) {
        Toast.makeText(context,"Start",Toast.LENGTH_LONG).show()
//        val audioPlayer = StreamAudioPlayer.getInstance(context!!)
//        var sourceURL = "http://radioluz.pwr.edu.pl:8000/luzhifi.mp3"
//        audioPlayer.playItem(sourceURL)
        val intent = Intent(context, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val id: Int = (Math.random() * 50 + 1).toInt()
        intent.putExtra("notificationId", id)

        val pendingIntent = PendingIntent.getActivity(context!!, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT)
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, "default")
                .setContentTitle("Title")
                .setContentText("text")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
        notificationManager.notify(id,notification.build())

    }
}
