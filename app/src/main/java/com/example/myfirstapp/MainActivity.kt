package com.example.myfirstapp

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MAIN_ACTIVITY", "MAIN STARTED")
        setContentView(R.layout.activity_main)
        startWifiService(View(this))
    }

    fun sendUDP(view: View) {

        val rawMachinesList = StorageUtils.readStorage(this)
        val machinesList = StorageUtils.getCurrentTargets(this, rawMachinesList)
        val currentBroadcastAddress = IpAddressUtils.getCurrentBroadcast(this)

        SendWakePacket.sendWakePacket(currentBroadcastAddress, machinesList)
    }

    fun addMachine(view: View) {
        val intent = Intent(this, AddMachine::class.java)
        startActivity(intent)
    }

    fun startWifiService(view: View) {
        if (!isWifiServiceRunning()) {
            val intent = Intent(this, WifiCheckingService::class.java).apply {
                putExtra(WifiCheckingService.ACTION_NAME, WifiCheckingService.ACTION_START_FOREGROUND_SERVICE)
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
                startForegroundService(intent)
            } else {
                startService(intent)
            }
        }
    }

    fun stopWifiService(view: View) {
        if (isWifiServiceRunning()) {
            val intent = Intent(this, WifiCheckingService::class.java).apply {
                putExtra(WifiCheckingService.ACTION_NAME, WifiCheckingService.ACTION_STOP_FOREGROUND_SERVICE)
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
                startForegroundService(intent)
            } else {
                startService(intent)
            }
        }
    }

    private fun isWifiServiceRunning(): Boolean {
        val manager = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (WifiCheckingService::class.java.name == service.service.className) {
                return true
            }
        }
        return false
    }

}