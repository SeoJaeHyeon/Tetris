package com.example.tetris

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tetris.ViewModel.ViewModelTetris
import com.example.tetris.databinding.ActivityGameoverBinding

class GameOverActivity : AppCompatActivity() {
    lateinit var binding: ActivityGameoverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameoverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtResult.text = intent.getStringExtra("score")




        binding.btnRetry.setOnClickListener {
            val intent = Intent(this, ClassicModeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        // 다시 게임으로 돌아가려면 원래 엑티비티로 이동
        binding.btnModeChoice.setOnClickListener {
            val intent = Intent(this, GameModeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }

        // 모드선택 버튼을 누르면 엑티비티 스택에 있던 모드 선택 엑티비티로 다시 이동
        binding.btnStop.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

    }

}