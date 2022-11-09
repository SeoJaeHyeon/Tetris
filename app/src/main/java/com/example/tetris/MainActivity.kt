package com.example.tetris

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NO_USER_ACTION
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tetris.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onRestart() {
        super.onRestart()
        startService(Intent(this, MusicService::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 첫 시작 화면(mainActivity)에서 게임 시작 버튼을 누르면 게임 모드 선택 엑티비티로 이동
        binding.btnGamestart.setOnClickListener {
            val intent = Intent(this, GameModeActivity::class.java)
            intent.addFlags(FLAG_ACTIVITY_NO_USER_ACTION) //onUserLeaveHint 로 인해 intent시 음악이 꺼지는 현상 발생 방지
            startActivity(intent)
        }

        // setting 화면 관련 Intent 수정완료
        binding.btnSetting.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            intent.addFlags(FLAG_ACTIVITY_NO_USER_ACTION)
            startActivity(intent)
        }

        startService(Intent(applicationContext, MusicService::class.java)) //서비스에 있는 onStartCommand 호출하여 노래 재생
    }

    override fun onDestroy() { //앱 종료시 음악종료
        stopService(Intent(applicationContext, MusicService::class.java)) //서비스에 있는 onDestory 호출하여 음악 중지
        super.onDestroy()
    }

    override fun onUserLeaveHint() { //홈버튼 눌러 앱종료시 음악이 꺼짐
        super.onUserLeaveHint()
        stopService(Intent(applicationContext, MusicService::class.java))
    }
}