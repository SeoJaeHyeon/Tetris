package com.example.tetris

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tetris.databinding.ActivityHardmodeBinding


class HardModeActivity: AppCompatActivity() {
    lateinit var binding: ActivityHardmodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHardmodeBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
            startActivity(Intent(this, StopActivity::class.java)) // 그만하기 버튼 누르면 StopActivity로 이동 -재현이가 해놓음
        }

    }
}