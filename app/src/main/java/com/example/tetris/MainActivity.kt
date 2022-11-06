package com.example.tetris

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.tetris.databinding.ActivityGamemodeBinding
import com.example.tetris.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_setting.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    fun choiceGamemode() {
        val bindingChoiceGame = ActivityGamemodeBinding.inflate(layoutInflater)
        setContentView(bindingChoiceGame.root)



/*
        val btn_classic: TextView = findViewById(R.id.btn_classicmode)
        val btn_hard: TextView = findViewById(R.id.btn_hardmode)
        val btn_timeAttack: TextView = findViewById(R.id.btn_timeattackmode)
        val btn_backMain: TextView = findViewById(R.id.btn_backmain)
*/

        bindingChoiceGame.btnClassicmode.setOnClickListener {
            setContentView(R.layout.activity_classicmode)
            classicMode()
        }

        bindingChoiceGame.btnHardmode.setOnClickListener {
            setContentView(R.layout.activity_hardmode)
            hardMode()
        }

        bindingChoiceGame.btnTimeattackmode.setOnClickListener {
            setContentView(R.layout.activity_timeattackmode)
            timeAttackMode()
        }

        bindingChoiceGame.btnBackmain.setOnClickListener {
            setContentView(binding.root)
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

        binding.btnGamestart.setOnClickListener {
            setContentView(R.layout.activity_gamemode)
            choiceGamemode()
        }

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