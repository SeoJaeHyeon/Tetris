package com.example.tetris

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tetris.databinding.ActivityGamemodeBinding
import com.example.tetris.databinding.ActivityStopBinding

class StopActivity : AppCompatActivity() {
    lateinit var binding: ActivityStopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //버튼 눌렸을 때

        // 그만하기 버튼을 누르면 엑티비티 스택에 있는 모든 액티비티 없애고 다시 게임 첫 화면으로 이동
        binding.btnStop.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        // 다시 게임으로 돌아가려면 원래 엑티비티로 이동
        binding.btnBackgame.setOnClickListener {
            finish()
        }

        // 모드선택 버튼을 누르면 엑티비티 스택에 있던 모드 선택 엑티비티로 다시 이동
        binding.btnModechoice.setOnClickListener {
            val intent = Intent(this, GameModeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }


    }
}

