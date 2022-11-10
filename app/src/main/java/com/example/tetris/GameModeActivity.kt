package com.example.tetris


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
/*
            val intent = Intent(this, MainActivity::class.java) // 게임 모드에서 뒤로 가기 버튼을 누르면
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)                  // 스택에 있는 나머지 액티비티 다 없애고
            startActivity(intent)                                            // 다시 MainActivity인 첫 화면으로 이동
*/

            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }
    }
}

