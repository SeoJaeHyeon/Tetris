package com.example.tetris

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tetris.databinding.ActivityTimeattackmodeBinding

class TimeAttackMode: AppCompatActivity() {
    lateinit var binding: ActivityTimeattackmodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTimeattackmodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun hardMode() {
// 버튼 눌렸을때
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
            stop()
        }
    }
}