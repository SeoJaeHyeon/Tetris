package com.example.tetris

import android.content.ContentValues.TAG
import android.content.Intent
import android.media.MediaPlayer
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.tetris.databinding.ActivityClassicmodeBinding
import com.example.tetris.databinding.ActivityGamemodeBinding
import com.example.tetris.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_setting.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    fun stopGame() {
        val bindingStopGame = ActivityClassicmodeBinding.inflate(layoutInflater)
        setContentView(bindingStopGame.root)

        bindingStopGame.imgStop.setOnClickListener {
            setContentView(R.layout.activity_stop)
        }

    }


    private var mediaPlayer: MediaPlayer? = null
    fun settingMode() { //게임 설정 화면
        val btn_back : TextView = findViewById(R.id.btn_back)
        val btn_bgmON : Button = findViewById((R.id.btn_bgmON))
        val btn_bgmOFF : Button = findViewById((R.id.btn_bgmOFF))

        btn_bgmON.setOnClickListener {
            if (mediaPlayer == null) { //노래 겹쳐서 재생방지
                mediaPlayer = MediaPlayer.create(this, R.raw.tetris_nintendo)
            }

            mediaPlayer?.start()
        }
        btn_bgmOFF.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer = null
        }
        btn_back.setOnClickListener {
            setContentView(binding.root)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 첫 시작 화면(mainActivity)에서 게임 시작 버튼을 누르면 게임 모드 선택 엑티비티로 이동
        binding.btnGamestart.setOnClickListener {
            startActivity(Intent(this, GameModeActivity::class.java))
        }

        // 여기는 인범이가 짠 함수 잘 몰라서 안건드림 but Intent 써서 위와 같이 수정해야함 !
        binding.btnSetting.setOnClickListener {
            setContentView(R.layout.activity_setting)
            settingMode()
        }

        mediaPlayer = MediaPlayer.create(this, R.raw.tetris_nintendo) //앱시작시 테트리스브금
        mediaPlayer?.start()

    }

    override fun onStop() { //배경음악 백그라운드에서 재생금지
        super.onStop()
        mediaPlayer?.release()

    }


}