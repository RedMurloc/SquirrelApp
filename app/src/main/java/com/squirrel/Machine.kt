package com.squirrel

class Machine {
    private var macAddress: String = ""
    private var wifiName: String = ""
    private var isActive: Boolean = false

    constructor(wifiName: String, macAddress: String, isActive: Boolean = true) {
        this.wifiName = wifiName
        this.macAddress = macAddress
        this.isActive = isActive
    }

    public fun getWifiName() : String {
        return this.wifiName
    }

    public fun getMacAddress() : String {
        return this.macAddress
    }

    public fun getMacAddressBytes() : ByteArray {
        val macAddressBytes = ByteArray(6)
        val stringBytes = macAddress.split("-")
        for (i in stringBytes.indices) {
            macAddressBytes[i] = Integer.parseInt(stringBytes[i], 16).toByte()
        }
        return  macAddressBytes
    }

    public fun isActive() : Boolean {
        return this.isActive
    }

}