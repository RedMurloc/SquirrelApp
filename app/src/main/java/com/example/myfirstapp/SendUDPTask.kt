package com.example.myfirstapp

import android.os.AsyncTask
import android.util.Log
import android.widget.EditText
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketException
import androidx.appcompat.app.AppCompatActivity

class SendUDPTask : AsyncTask<Machine, Int?, String?>() {
    override fun doInBackground(vararg arr: Machine?): String? {
        /*var exceptionText: String = ""
        Thread.sleep(2000)
        val socket: DatagramSocket? = try { DatagramSocket(8888)
        } catch (e: SocketException) {
            Log.d("UDP1", e.toString())
            null
        }

        val bytes = ByteArray(102)
        val macAddr = byteArrayOf(0xB4.toByte(), 0x2E.toByte(), 0x99.toByte(), 0x94.toByte(), 0xFE.toByte(), 0xE7.toByte())

        for (i in 0..5) {
            bytes[i] = 0xFF.toByte()
        }

        for (i in 6..101) {
            bytes[i] = macAddr[i % 6]
        }

        val packet = DatagramPacket(bytes, bytes.size, InetAddress.getByAddress(byteArrayOf(192.toByte(), 168.toByte(),1.toByte(),66.toByte())),7777)
        //val packet = DatagramPacket(bytes, bytes.size, InetAddress.getByAddress(byteArrayOf(192.toByte(), 168.toByte(), 1.toByte(), 255.toByte())),9)

        try {
            socket?.send(packet)
        } catch (e: Exception) {
            Log.d("UDP2", e.toString())
        }
        socket?.close()
        return exceptionText*/

        Thread.sleep(2000)

        val socket: DatagramSocket? = try { DatagramSocket(8888)
        } catch (e: SocketException) {
            Log.d("UDP1", e.toString())
            null
        }

        for (machine in arr) {
            val bytes = ByteArray(102)
            val macAddress = machine?.getMacAddressBytes()

            for (i in 0..5) {
                bytes[i] = 0xFF.toByte()
            }

            for (i in 6..101) {
                bytes[i] = macAddress!![i % 6]
            }

            val packet = DatagramPacket(bytes, bytes.size, InetAddress.getByAddress(byteArrayOf(192.toByte(), 168.toByte(),1.toByte(),66.toByte())),7777)

        }

return "kek"



    }
}