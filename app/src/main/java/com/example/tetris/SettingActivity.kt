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
            isMusicOn = true
            startService(Intent(applicationContext, MusicService::class.java)) ////서비스에 있는 onStartCommand 호출하여 노래 재생
        }
        binding.btnBgmOFF.setOnClickListener {
            isMusicOn = false
            stopService(Intent(applicationContext, MusicService::class.java)) //서비스에 있는 onDestory 호출하여 음악 중지
        }
    }

}