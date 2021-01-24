package com.example.myfirstapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.wifi.WifiManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MAX
import java.lang.RuntimeException

class WifiCheckingService : Service() {

    companion object {
        const val ACTION_NAME = "WifiCheckingService.ACTION_NAME"
        const val ACTION_START_FOREGROUND_SERVICE = "WifiCheckingService.ACTION_START_FOREGROUND_SERVICE"
        const val ACTION_STOP_FOREGROUND_SERVICE = "WifiCheckingService.ACTION_STOP_FOREGROUND_SERVICE"
    }

    lateinit var broadcastReceiver : WIFIBroadcastReceiver

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        when (intent?.getStringExtra(ACTION_NAME)) {
            ACTION_START_FOREGROUND_SERVICE -> {
                startForeground()
                broadcastReceiver = WIFIBroadcastReceiver()
                val filter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
                registerReceiver(broadcastReceiver, filter)
            }
            ACTION_STOP_FOREGROUND_SERVICE -> {
                stopForeground(true)
                stopSelf()
            }
            else -> {throw RuntimeException("Wrong intent")}
        }

        return START_STICKY;
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
        unregisterReceiver(broadcastReceiver)
        Log.d("DESTROY!", "onDestroy()")
    }

    private fun startForeground() {
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel("my_service", "My Background Service")
            } else {
                // If earlier version channel ID is not used
                // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
                ""
            }

        val notificationBuilder = NotificationCompat.Builder(this, channelId )
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(PRIORITY_MAX)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(101, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String{
        val chan = NotificationChannel(channelId,
            channelName, NotificationManager.IMPORTANCE_HIGH)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }

}