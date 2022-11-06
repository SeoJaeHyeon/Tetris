package com.example.tetris

import android.widget.Button
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

fun classicMode() {

    lateinit var binding: ActivityClassicmodeBinding

    binding = ActivityClassicmodeBinding.inflate(layoutinflater)


    val btn_left: Button = findViewById(R.id.ibtn_left)
    val btn_right: Button = findViewById(R.id.ibtn_right)
    val btn_down: Button = findViewById(R.id.ibtn_down)
    val btn_change: Button = findViewById(R.id.ibtn_change)
    val btn_stop: Button = findViewById(R.id.ibtn_stop)


    // 버튼 눌렸을때
    binding.ibtnLeft.setOnClickListener {
        left()
    }

    binding.ibtnRight.setOnClickListener {
        right()
    }

    binding.ibtnDown.setOnClickListener {
        down()
    }

    binding.ibtnChange.setOnClickListener {
        change()
    }

    binding.ibtnStop.setOnClickListener {
        stop()
    }


}