package com.squirrelWakeUp

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class AddMachine : AppCompatActivity() {

    companion object {
        const val STORAGE_NAME = "storage";
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_machine)
        findViewById<TextView>(R.id.savedMachinesView).movementMethod = ScrollingMovementMethod()
        readStorage()
        /*val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)*/
    }

    fun saveMachine(view: View) {
        val wifiName = findViewById<EditText>(R.id.wifiName).text.toString()
        val macAddress = findViewById<EditText>(R.id.macAddress).text.toString()
        if (macAddress.matches(Regex("[0-9A-F]{2}(-[0-9A-F]{2}){5}"))) {
            val file = File(this.filesDir, STORAGE_NAME)
            file.writeText(wifiName + "\n")
            file.appendText(macAddress + "\n")
            val view = findViewById<TextView>(R.id.savedMachinesView)
            view.text = ""
            readStorage()
        } else {
            findViewById<EditText>(R.id.macAddress).setText(getString(R.string.edit_pattern_warning))
        }
    }

    fun readStorage() {
        val view = findViewById<TextView>(R.id.savedMachinesView)
        val file = File(this.filesDir, STORAGE_NAME)
        if (file.exists()) {
            val machinesList = file.readLines()
            for (i in machinesList.indices) {
                if (i % 2 == 0) {
                    view.append(machinesList[i] + " | " + machinesList[i + 1] + "\n")
                }
            }
        }
    }

}