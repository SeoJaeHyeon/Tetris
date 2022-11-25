package com.example.tetris.activity


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tetris.databinding.ActivityGamemodeBinding

// GameModeActivity class 생성
class GameModeActivity : AppCompatActivity() {
    lateinit var binding: ActivityGamemodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGamemodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //버튼 눌렸을 때
        // Intent를 이용하여 원하는 엑티비티로 이동
        binding.btnClassicmode.setOnClickListener {
            val intent = Intent(this, ClassicModeActivity::class.java) // classic 모드로 엑티비티 이동
            startActivity(intent)
        }
        binding.btnHardmode.setOnClickListener {
            startActivity(Intent(this, HardModeActivity::class.java)) // hard 모드로 엑티비티 이동
        }
        binding.btnTimeattackmode.setOnClickListener {
            startActivity(Intent(this, TimeAttackModeActivity::class.java)) // time attack 모드로 엑티비티 이동
        }
        binding.btnBackmain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }
    }
}

