package com.example.tetris

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tetris.databinding.ActivityTimeattackmodeBinding

class TimeAttackModeActivity: AppCompatActivity() {
    lateinit var binding: ActivityTimeattackmodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTimeattackmodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imgLeftt.setOnClickListener {
            left()
        }
        binding.imgRightt.setOnClickListener {
            right()
        }
        binding.imgDownt.setOnClickListener {
            down()
        }
        binding.imgChanget.setOnClickListener {
            change()
        }
        binding.imgStopt.setOnClickListener {
            startActivity(Intent(this, StopActivity::class.java)) // 그만하기 버튼 누르면 StopActivity로 이동 -재현이가 해놓음
        }
    }

}