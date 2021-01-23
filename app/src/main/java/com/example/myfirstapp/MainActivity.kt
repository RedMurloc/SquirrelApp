package com.example.myfirstapp

import android.Manifest
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


const val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"

class MainActivity : AppCompatActivity() {

    lateinit var wifiManager : WifiManager
    //lateinit var broadcastReceiver : WIFIBroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MAIN_ACTIVITY", "MAIN STARTED")
        setContentView(R.layout.activity_main)
        wifiManager = this.getSystemService(Context.WIFI_SERVICE) as WifiManager
        //broadcastReceiver = WIFIBroadcastReceiver(/*wifiManager*/)
        //val filter = IntentFilter(WIFI_STATE_CHANGED_ACTION)
        //registerReceiver(broadcastReceiver, filter)
        startWifiService(View(this))
    }

    fun sendMessage(view: View) {
        ActivityCompat.requestPermissions(this, Array<String>(1){Manifest.permission.ACCESS_NETWORK_STATE}, 1)
        val editText = findViewById<EditText>(R.id.editText)
        //val message = editText.text.toString()
        val wifiInfo = wifiManager.connectionInfo
        //wifiInfo.getSupplicantState()
        val message = wifiInfo.ssid
        //var message =  ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE)
        //message =  ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val intent = Intent(this, DisplayMessageActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

    fun sendUDP(view: View) {

        /*val socket: DatagramSocket? = try { DatagramSocket(8888)
        } catch (e: SocketException) {
            val sheet = findViewById<EditText>(R.id.textSheet)
            sheet.setText(e.toString())
            null
        }
        val bytes = byteArrayOf(1,2,3,4,5,6,7,8,9)
        val packet = DatagramPacket(bytes, bytes.size, InetAddress.getByAddress(byteArrayOf(192.toByte(), 168.toByte(),1.toByte(),69.toByte())),7777)
        try {
            socket?.send(packet)
        } catch (e: Exception) {
            val sheet = findViewById<EditText>(R.id.textSheet)
            sheet.setText(e.toString())
        }*/
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
            startForegroundService(intent)
        }
    }

    fun stopWifiService(view: View) {
        if (isWifiServiceRunning()) {
            val intent = Intent(this, WifiCheckingService::class.java).apply {
                putExtra(WifiCheckingService.ACTION_NAME, WifiCheckingService.ACTION_STOP_FOREGROUND_SERVICE)
            }
            startForegroundService(intent)
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