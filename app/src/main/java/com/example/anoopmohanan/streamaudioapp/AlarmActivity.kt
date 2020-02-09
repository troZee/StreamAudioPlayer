package com.example.anoopmohanan.streamaudioapp

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.anoopmohanan.streamaudioapp.player.StreamAudioPlayer

import android.os.PowerManager

import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_alarm.*

class AlarmActivity : AppCompatActivity() {

    private lateinit var audioPlayer: StreamAudioPlayer
    private val sourceURL = "http://radioluz.pwr.edu.pl:8000/luzhifi.mp3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
        turnOnScreen()
        audioPlayer = StreamAudioPlayer.getInstance(this)
        audioPlayer.playItem(sourceURL)
        btn_stop.setOnClickListener {
            audioPlayer.stop()
        }
    }

    private fun turnOnScreen() {
        var screenLock: PowerManager.WakeLock? = null
        if (getSystemService(Context.POWER_SERVICE) != null) {
            screenLock = (getSystemService(Context.POWER_SERVICE) as PowerManager).newWakeLock(
                    PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG:asd")
            screenLock!!.acquire(10 * 60 * 1000L /*10 minutes*/)
            screenLock.release()
        }
    }
    override fun onDestroy() {
        audioPlayer.stop()
        audioPlayer.release()
        super.onDestroy()
    }
}
