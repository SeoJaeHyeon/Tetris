package com.example.tetris

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tetris.databinding.ActivityHardmodeBinding


class HardMode: AppCompatActivity() {
    lateinit var binding: ActivityHardmodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHardmodeBinding.inflate(layoutInflater)
    }

    fun hardMode() {
// 버튼 눌렸을때
        binding.imgLefth.setOnClickListener {
            left()
        }
        binding.imgRighth.setOnClickListener {
            right()
        }
        binding.imgDownh.setOnClickListener {
            down()
        }
        binding.imgChangeh.setOnClickListener {
            change()
        }
        binding.imgStoph.setOnClickListener {
            stop()
        }
    }
}