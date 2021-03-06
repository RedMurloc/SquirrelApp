package com.squirrelWakeUp

import android.content.Context
import android.net.wifi.WifiManager
import java.io.File

class StorageUtils {
    companion object {
         fun readStorage(context: Context) : List<Machine> {
            val file = File(context.filesDir, AddMachine.STORAGE_NAME)
            lateinit var rawMachinesList : List<String>
             var machinesList = ArrayList<Machine>()

            if (file.exists()) {
                rawMachinesList = file.readLines()
            } else {
                return machinesList
            }

            for(i in rawMachinesList.indices) {
                if (i % 2 == 0) {
                    machinesList.add(Machine(rawMachinesList[i], rawMachinesList[i + 1]))
                }
            }

            return machinesList
        }

        fun getCurrentTargets(context: Context, machines: List<Machine>) : List<Machine> {
            val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiManager.connectionInfo
            val rawWifiName = wifiInfo.ssid
            val currentWifiName = if (rawWifiName.first() == '\"' && rawWifiName.last() == '\"') rawWifiName.substring(1, rawWifiName.lastIndex) else rawWifiName
            val currentTargets = ArrayList<Machine>()

            for (machine in machines) {
                if (/*machine.getWifiName() == currentWifiName &&*/ machine.isActive()) {
                    currentTargets.add(machine)
                }
            }

            return currentTargets
        }
    }
}