package co.feip.fefu2025

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class InternetModeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected) {
            Log.d("InternetModeReceiver", "Интернет включен")
        } else {
            Log.d("InternetModeReceiver", "Интернет выключен")
        }
    }
}

class MainActivity : AppCompatActivity() {

    private var counter: Int = 0
    private val COUNTER_KEY = "counter_key"

    private lateinit var internetReceiver: InternetModeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        savedInstanceState?.let {
            counter = it.getInt(COUNTER_KEY, 0)

        }

        val counterTextView: TextView = findViewById(R.id.counterTextView)
        counterTextView.text = counter.toString()

        counterTextView.setOnClickListener {
            counter++
            counterTextView.text = counter.toString()
        }

        internetReceiver = InternetModeReceiver()
        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(internetReceiver, intentFilter)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(COUNTER_KEY, counter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(internetReceiver)

    }
}