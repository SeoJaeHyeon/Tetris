package com.example.tetris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.tetris.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    fun choiceGamemode() {

        val btn_classic: TextView = findViewById(R.id.btn_classicmode)
        val btn_hard: TextView = findViewById(R.id.btn_hardmode)
        val btn_timeAttack: TextView = findViewById(R.id.btn_timeattackmode)
        val btn_backMain: TextView = findViewById(R.id.btn_backmain)


        btn_classic.setOnClickListener {
            setContentView(R.layout.activity_classicmode)
            classicMode()
        }

        btn_hard.setOnClickListener {
            setContentView(R.layout.activity_hardmode)
            hardMode()
        }

        btn_timeAttack.setOnClickListener {
            setContentView(R.layout.activity_timeattackmode)
            timeAttackMode()
        }

        btn_backMain.setOnClickListener {
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
        }




    }
}