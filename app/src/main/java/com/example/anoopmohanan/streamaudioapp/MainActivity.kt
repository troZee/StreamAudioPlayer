package com.example.anoopmohanan.streamaudioapp

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
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
import android.widget.DatePicker
import android.support.v4.app.SupportActivity
import android.support.v4.app.SupportActivity.ExtraData
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.text.SimpleDateFormat


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myCalendar = Calendar.getInstance().apply { timeInMillis = System.currentTimeMillis()+ 86_400_000}
        val timeListener = TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
            myCalendar.set(Calendar.HOUR_OF_DAY,selectedHour)
            myCalendar.set(Calendar.MINUTE,selectedMinute)
            Toast.makeText(this, "$selectedHour $selectedMinute",Toast.LENGTH_SHORT).show()
            tv_date.text = SimpleDateFormat("HH:mm:SS", Locale.UK).format(myCalendar.time)
            val alarmMgr = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmIntent = Intent(applicationContext, AlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(applicationContext, 0, intent, 0)
            }
            alarmMgr.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    myCalendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    alarmIntent
            )
        }
        btn_open_calendar.setOnClickListener {
            TimePickerDialog(this,timeListener,myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),true).show()
        }

    }
}
