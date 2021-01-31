package com.squirrelWakeUp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class RunOnStartup : BroadcastReceiver() {

    override fun onReceive(context : Context, intent : Intent) {

        val i = Intent(context, WifiCheckingService::class.java).apply {
            putExtra(
                    WifiCheckingService.ACTION_NAME,
                    WifiCheckingService.ACTION_START_FOREGROUND_SERVICE
            )
        }
        i.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            context.startForegroundService(i)
        } else {
            context.startService(i)
        }
    }

}