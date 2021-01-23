package com.example.myfirstapp

import android.util.Log
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketException

class SendWakePacket {
    companion object {
        public fun sendWakePacket(address: InetAddress, machines: List<Machine>) {
            Thread(Runnable {
                //Thread.sleep(2000)

                /*val socket: DatagramSocket? = try { DatagramSocket(8888)
                } catch (e: SocketException) {
                    Log.d("UDP1", e.toString())
                    null
                }*/

                DatagramSocket(8888).use { socket ->
                    for (machine in machines) {
                        val bytes = ByteArray(102)
                        val macAddress = machine.getMacAddressBytes()

                        for (i in 0..5) {
                            bytes[i] = 0xFF.toByte()
                        }

                        for (i in 6..101) {
                            bytes[i] = macAddress[i % 6]
                        }

                        val packet = DatagramPacket(bytes, bytes.size, /*address*/InetAddress.getByAddress(byteArrayOf(192.toByte(), 168.toByte(), 1.toByte(), 66.toByte())), 7777)

                        try {
                            socket.send(packet)
                        } catch (e: Exception) {
                            Log.d("UDP2", e.toString())
                        }
                    }
                }
            }).start()
        }

    }

}