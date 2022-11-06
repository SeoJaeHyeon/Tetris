package com.example.tetris


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tetris.databinding.ActivityClassicmodeBinding

// 왼쪽 오른쪽 아래 모양바꾸기
fun left() {

}

fun right() {

}

fun down() {

}

fun change() {

}

fun stop() {

}

class ClassicMode : AppCompatActivity() {
    lateinit var binding: ActivityClassicmodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClassicmodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    public fun classicMode() {
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
            stop()
        }
    }
}