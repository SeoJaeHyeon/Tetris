package com.example.tetris

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NO_USER_ACTION
import android.media.MediaPlayer
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.tetris.databinding.ActivityClassicmodeBinding
import com.example.tetris.databinding.ActivityGamemodeBinding
import com.example.tetris.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_setting.*

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
            intent.addFlags(FLAG_ACTIVITY_NO_USER_ACTION)
            startActivity(intent)
        }

        // setting 화면 관련 Intent 수정완료
        binding.btnSetting.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            intent.addFlags(FLAG_ACTIVITY_NO_USER_ACTION)
            startActivity(intent)
        }

        startService(Intent(applicationContext, MusicService::class.java))
    }

    override fun onDestroy() { //앱종료시 음악종료
        stopService(Intent(applicationContext, MusicService::class.java))
        super.onDestroy()
    }

    override fun onUserLeaveHint() { //홈버튼 누르면 음악꺼짐
        super.onUserLeaveHint()
        stopService(Intent(applicationContext, MusicService::class.java))
    }
}