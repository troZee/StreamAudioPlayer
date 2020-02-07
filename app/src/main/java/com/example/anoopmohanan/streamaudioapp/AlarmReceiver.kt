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
import android.os.PowerManager
import java.util.*
import android.support.v4.content.ContextCompat.startActivity




class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, receivedIntent: Intent?) {
        Toast.makeText(context,"Start",Toast.LENGTH_LONG).show()
        val i = Intent()
        i.setClassName("com.example.anoopmohanan.streamaudioapp", "com.example.anoopmohanan.streamaudioapp.AlarmActivity")
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context!!.startActivity(i)

    }
}
