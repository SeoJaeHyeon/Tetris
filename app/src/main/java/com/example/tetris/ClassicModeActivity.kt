package com.example.tetris


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tetris.databinding.ActivityClassicmodeBinding



class ClassicModeActivity : AppCompatActivity() {
    lateinit var binding: ActivityClassicmodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClassicmodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            startActivity(Intent(this, StopActivity::class.java)) // 그만하기 버튼 누르면 StopActivity로 이동 -재현이가 해놓음
        }
    }

}

