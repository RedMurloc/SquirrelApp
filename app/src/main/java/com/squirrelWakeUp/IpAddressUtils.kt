package com.squirrelWakeUp

import android.content.Context
import android.net.wifi.WifiManager
import java.net.InetAddress

class IpAddressUtils {
    companion object {

        private fun intToByteArray(hostAddress : Int) : ByteArray {
            return byteArrayOf( (0xff and hostAddress).toByte(),
                    (0xff and (hostAddress shr 8)).toByte(),
                    (0xff and (hostAddress shr 16)).toByte(),
                    (0xff and (hostAddress shr 24)).toByte()
            )
        }

        private fun getCurrentIP(context: Context) : Int {
            val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val dhcpInfo = wifiManager.dhcpInfo
            return dhcpInfo.ipAddress
        }

        private fun getCurrentNetmask (context: Context) : Int {
            val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val dhcpInfo = wifiManager.dhcpInfo
            return dhcpInfo.netmask
        }

        fun getCurrentBroadcast(context: Context) : InetAddress {
            return InetAddress.getByAddress(intToByteArray(getCurrentIP(context) or (getCurrentNetmask(context) xor 0xFFFFFFFF.toInt())))
        }

    }
}