package com.example.servicesandroid

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings

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

    private fun startMyService() {
        mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI)
        mediaPlayer.start()
        mediaPlayer.isLooping = true
    }


    //For consistency across our application
    enum class Actions{
        START, STOP
    }
}