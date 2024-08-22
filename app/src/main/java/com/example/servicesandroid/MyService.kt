package com.example.servicesandroid

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import androidx.core.app.NotificationCompat

class MyService : Service() {
    private lateinit var mediaPlayer: MediaPlayer //mediaPlayer object
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }


    //in this function, we will be checking what the actions the intent has
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when(intent?.action){
            Actions.START.toString() -> {
                startMyService()

            }

            Actions.STOP.toString() ->{
                mediaPlayer.stop()
                stopSelf()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("ForegroundServiceType")
    private fun startMyService() {
        mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI)
        mediaPlayer.start()
        mediaPlayer.isLooping = true
        val notification = NotificationCompat.Builder(
            this, "my_channel"
        ).setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Foreground Service")
            .setContentText("Foreground Service is running")
            .setOngoing(true)
            .build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(1001, notification,ServiceInfo.FOREGROUND_SERVICE_TYPE_MANIFEST)
        }
    }


    //For consistency across our application
    enum class Actions{
        START, STOP
    }
}