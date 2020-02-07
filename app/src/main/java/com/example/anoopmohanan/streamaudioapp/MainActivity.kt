package com.example.anoopmohanan.streamaudioapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import com.example.anoopmohanan.streamaudioapp.player.StreamAudioPlayer
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var audioPlayer: StreamAudioPlayer? = null

    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent

    private val streamAudioPlayerCallback = object : StreamAudioPlayer.StreamAudioPlayerCallback {
        override fun playerPrepared() {

            show("BUFFERING COMPLETE......")
            showProgress(false)
        }

        override fun playerProgress(offsetInMilliseconds: Long, percent: Float) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun itemComplete() {
            show("FINISHED PLAYING......")
        }

        override fun playerError() {
            show("Error while playing......")
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val notificationId = intent?.extras?.getInt("notificationId",-1) ?: -1
        show(notificationId.toString())
        if (notificationId < 0) {
            alarmMgr = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmIntent = Intent(applicationContext, AlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(applicationContext, 0, intent, 0)
            }

            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis() + 6000
//                set(Calendar.HOUR_OF_DAY, 16)
//                set(Calendar.MINUTE, 34)
            }

            alarmMgr?.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    alarmIntent
            )
        }


//        audioPlayer = StreamAudioPlayer.getInstance(this)
//        audioPlayer?.addCallback(streamAudioPlayerCallback)
//        show("BUFFERING......")
//        showProgress(false)
    }

    fun startStream(view: View) {
        var sourceURL = "http://radioluz.pwr.edu.pl:8000/luzhifi.mp3"
        audioPlayer?.playItem(sourceURL)
        showProgress(true)


    }

    fun stopStream(view: View) {
        audioPlayer?.stop()
        showProgress(false)
    }

    fun showProgress(status: Boolean) {

        if (status) {

            this.progressBar.visibility = VISIBLE
        } else {
            this.progressBar.visibility = INVISIBLE
        }
    }

    fun show(message: String) {

        var toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        toast.show()
    }
}
