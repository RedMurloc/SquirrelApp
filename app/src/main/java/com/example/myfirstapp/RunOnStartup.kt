package com.example.myfirstapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import java.io.File

class RunOnStartup : BroadcastReceiver() {

    override fun onReceive(context : Context, intent : Intent) {
        val file = File(context.filesDir, AddMachine.STORAGE_NAME)
        file.writeText("A" + "\n")
        file.appendText("B" + "\n")
        Toast.makeText(context, "onBoot1", Toast.LENGTH_LONG).show()
        Thread.sleep(3000)
        Toast.makeText(context, "onBoot2", Toast.LENGTH_LONG).show()
        //if (intent.action.equals(Intent.ACTION_BOOT_COMPLETED) || intent.action.equals(Intent.ACTION_LOCKED_BOOT_COMPLETED)) {
            //if (!isWifiServiceRunning()) {
                //val i = Intent(context, /*MainActivity::class.java*/WifiCheckingService::class.java).apply {
                  //  putExtra(WifiCheckingService.ACTION_NAME, WifiCheckingService.ACTION_START_FOREGROUND_SERVICE)
                //}
                //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                //i.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
                //Toast.makeText(context, "onBoot2", Toast.LENGTH_LONG).show()
                //context.startForegroundService(i);
                //context.startActivity(i)
            //}
        //}
    }

}