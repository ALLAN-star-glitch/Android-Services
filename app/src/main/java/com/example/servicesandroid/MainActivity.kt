package com.example.servicesandroid

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.servicesandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

        //note that post notification is only required when our version is greater or equal to android 13
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),200)
        }



        binding.btnStartService.setOnClickListener {
            val intent = Intent(this@MainActivity,MyService::class.java)
            intent.action = MyService.Actions.START.toString()
            if (prefs.getStringSet("componentName",null)==null){
                val serviceComponentname = startService(intent)
                val editor = prefs.edit()
                editor.putString("componentName", serviceComponentname.toString())
                editor.apply()
            }
        }

        binding.btnStopService.setOnClickListener {
            val intent = Intent(this@MainActivity,MyService::class.java)
            intent.action = MyService.Actions.STOP.toString()
            if(prefs.getString("componentName", null)!=null){
                startService(intent)
            }
        }
    }
}