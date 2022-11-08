package com.example.tetris

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.tetris.databinding.ActivitySettingBinding
import kotlinx.android.synthetic.main.activity_setting.*


class SettingActivity: AppCompatActivity() {
    lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnBgmON.setOnClickListener {
            startService(Intent(applicationContext, MusicService::class.java))
        }
        binding.btnBgmOFF.setOnClickListener {
            stopService(Intent(applicationContext, MusicService::class.java))
        }
    }

}