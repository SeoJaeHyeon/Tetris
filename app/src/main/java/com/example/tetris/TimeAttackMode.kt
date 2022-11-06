package com.example.tetris

import android.widget.Button
import com.example.tetris.databinding.ActivityTimeattackmodeBinding

fun timeAttackMode() {

    lateinit var binding: ActivityTimeattackmodeBinding

    binding = ActivityTimeattackmodeBinding.inflate(layoutinflater)

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