package com.example.myfirstapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService


open class WIFIBroadcastReceiver : BroadcastReceiver() {
    private /*lateinit*/ var TAG = "WIFIBroadcastReceiver"
    var stupidCounter = 0
    //private lateinit var wifiManager: WifiManager

    /*init {
        TAG = "WIFIBroadcastReceiver"
    }*/

    /*constructor(wifiManager: WifiManager) {
        super()
        this.wifiManager = wifiManager
    }*/

    override fun onReceive(context: Context, intent: Intent) {
        if ((intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 228) == WifiManager.WIFI_STATE_ENABLED) /*&& (stupidCounter++ != 0)*/) {
                        Toast.makeText(context, "RECIEVED", Toast.LENGTH_LONG).show()
            //Thread.sleep(2000)

            do {
                Thread.sleep(1000)
                val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetworkInfo: NetworkInfo? = connectivityManager.getActiveNetworkInfo()
                Log.d("Wait 4 network", activeNetworkInfo?.isConnected.toString())
            } while (activeNetworkInfo == null || !activeNetworkInfo.isConnected)

            val rawMachinesList = StorageUtils.readStorage(context)
            val machinesList = StorageUtils.getCurrentTargets(context, rawMachinesList)
            Log.d("recieved!", "${machinesList.size}")
            //for (i in 0..9) {
                //SendUDPTask().execute(*machinesList.toTypedArray())
                val currentBroadcastAddress = IpAddressUtils.getCurrentBroadcast(context)
                SendWakePacket.sendWakePacket(currentBroadcastAddress, machinesList)
            //}
            /*StringBuilder().apply {
            append("Action: ${intent.action}\n")
            append("URI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}\n")
            toString().also { log ->
                Log.d(TAG, log)
                Toast.makeText(context, log, Toast.LENGTH_LONG).show()
            }
        }*/
        }
    }
}