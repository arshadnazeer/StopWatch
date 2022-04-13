package com.arsh.stopwatch

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arsh.stopwatch.StopWatchService.Companion.CURRENT_TIME
import com.arsh.stopwatch.StopWatchService.Companion.UPDATED_TIME
import com.arsh.stopwatch.databinding.ActivityMainBinding
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isStarted = false
    private lateinit var serviceIntent : Intent
    private var time = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btStartStop.setOnClickListener {
            startOrStop()
        }

        binding.btReset.setOnClickListener {
            reset()
        }

        serviceIntent = Intent(applicationContext,StopWatchService::class.java)

        registerReceiver(updatedTime, IntentFilter(UPDATED_TIME))

    }

    private fun startOrStop(){
        if (isStarted){
            stop()
        } else{
            start()
        }
    }

    private fun start(){
        serviceIntent.putExtra(CURRENT_TIME,time)
        startService(serviceIntent)
        binding.btStartStop.text = "Stop"
        isStarted = true

    }

    private fun stop(){
        stopService(serviceIntent)
        binding.btStartStop.text = "Start"
        isStarted = false

    }


    private fun reset() {
        stop()
        time = 0.0
        binding.txStpwatch.text = getFormattedTime(time )
    }

    private val updatedTime : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(CURRENT_TIME, 0.0)
            binding.txStpwatch.text = getFormattedTime(time)
        }
    }

    private fun getFormattedTime(
        time : Double
    ) : String{
        val timeInt  = time.roundToInt()
        val hours = timeInt % 86400 / 3600
        val mins = timeInt % 86400 % 3600 / 60
        val seconds = timeInt % 86400 % 3600 % 60
        return String.format("%02d:%02d:%02d", hours,mins,seconds)
    }

}