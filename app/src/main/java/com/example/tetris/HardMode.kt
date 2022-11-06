package com.example.tetris

import android.widget.Button
import com.example.tetris.databinding.ActivityClassicmodeBinding
import com.example.tetris.databinding.ActivityHardmodeBinding

fun hardMode() {

    lateinit var binding: ActivityHardmodeBinding

    binding = ActivityHardmodeBinding.inflate(layoutinflater)


    val btn_lefth: Button = findViewById(R.id.ibtn_lefth)
    val btn_righth: Button = findViewById(R.id.ibtn_righth)
    val btn_downh: Button = findViewById(R.id.ibtn_downh)
    val btn_changeh: Button = findViewById(R.id.ibtn_changeh)
    val btn_stoph: Button = findViewById(R.id.ibtn_stoph)


// 버튼 눌렸을때
    binding.ibtnLefth.setOnClickListener {
        left()
    }
    binding.ibtnRighth.setOnClickListener {
        right()
    }
    binding.ibtnDownh.setOnClickListener {
        down()
    }
    binding.ibtnChangeh.setOnClickListener {
        change()
    }
    binding.ibtnStoph.setOnClickListener {
        stop()
    }

}