package com.example.tetris


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tetris.databinding.ActivityClassicmodeBinding



class ClassicMode : AppCompatActivity() {
    lateinit var binding: ActivityClassicmodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClassicmodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun classicMode() {
        //버튼 눌렸을 때
        binding.imgLeft.setOnClickListener {
            left()
        }
        binding.imgRight.setOnClickListener {
            right()
        }
        binding.imgDown.setOnClickListener {
            down()
        }
        binding.imgChange.setOnClickListener {
            change()
        }
        binding.imgStop.setOnClickListener {
        }
    }
}