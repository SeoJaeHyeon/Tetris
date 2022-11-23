package com.example.tetris

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.gridlayout.widget.GridLayout
import com.example.tetris.Block.*
import com.example.tetris.Component.Tetris
import com.example.tetris.Component.TetrisTimeAttack
import com.example.tetris.ViewModel.ViewModelArray
import com.example.tetris.databinding.ActivityTimeattackmodeBinding
import java.util.*
import kotlin.concurrent.thread
import kotlin.concurrent.timer

class TimeAttackModeActivity: AppCompatActivity() {
    lateinit var binding: ActivityTimeattackmodeBinding

    val viewModelFrameT: ViewModelArray by viewModels()
    val tetris = TetrisTimeAttack()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTimeattackmodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.boardt.rowCount = tetris.gameState.ROW // gridLayout의 row
        binding.boardt.columnCount = tetris.gameState.COL // gridLayout의 col

        binding.nextblockt.rowCount = tetris.gameState.NEXTROW
        binding.nextblockt.columnCount = tetris.gameState.NEXTCOL

        // 뷰모델로 score 갱신
        viewModelFrameT.score.observe(this) {
            binding?.txtScoret?.text = viewModelFrameT.score.value.toString()
        }

        // 뷰모델로 high 갱신
        viewModelFrameT.high.observe(this) {
            binding?.txtHight?.text = viewModelFrameT.high.value.toString()
        }


        // 게임 시작 후 gridLayout에 게임화면 생성
        gameFrameSetting(tetris.gameState.gameFrame, binding.boardt, tetris.gameState.ROW, tetris.gameState.COL)
        gameFrameSetting(tetris.gameState.nextBlockFrame, binding.nextblockt, tetris.gameState.NEXTROW, tetris.gameState.NEXTCOL)

        gameRun()

        timeAttack()

        binding.imgLeftt.setOnClickListener {
            tetris.imgLeft()
        }
        binding.imgRightt.setOnClickListener {
            tetris.imgRight()
        }
        binding.imgDownt.setOnClickListener {
            tetris.imgDown()
        }
        binding.imgChanget.setOnClickListener {
            tetris.imgChange()
        }
        binding.imgStopt.setOnClickListener {
            startActivity(Intent(this, StopActivity::class.java)) // 그만하기 버튼 누르면 StopActivity로 이동
        }
    }


    // 게임 화면 설정 -> 게임화면에 사용될 이차원배열, 이차원배열을 그려줄 gridLayout, row, col을 매개변수로 받음
    fun gameFrameSetting(arr: Array<Array<ImageView?>>, grid: GridLayout, row: Int, col: Int) {
        // 해당 context(환경에 대한 전역정보) LayoutInflater를 가져옴(xml을 View객체로 변환)
        val inflater = LayoutInflater.from(this)
        for (i in 0 until row) {
            for (j in 0 until col) {
                // 게임화면에서 사용되는 이차원 배열의 각 원소에 gameframe_image를 넣음
                arr!![i][j] = inflater.inflate(R.layout.gameframe_image, grid, false) as ImageView
                // 위의 배열을 gridLayout에 맞추어 이미지 삽입
                grid.addView(arr[i][j])
            }
        }
    }

    fun timeAttack() {
        //타이머로 시간 표현, time이 0 되면 게임종료
        tetris.timerTask = timer(period = 10) {
            tetris.time--
            tetris.sec = tetris.time / 100
            tetris.milli = tetris.time % 100
            runOnUiThread {
                binding.txtTime?.text = "${tetris.sec}:${tetris.milli}"
            }
            if (tetris.time == 0) {
                tetris.timerTask?.cancel()
                tetris.gameState.run = false
            }
        }
    }

    fun gameRun() {

        tetris.resetFrame(tetris.gameState.nextBlockFrame,tetris.gameState.NEXTROW,tetris.gameState.NEXTCOL)
        tetris.printNextBlock()
        thread(start = true) {
            while(tetris.gameState.run) {
                var millis = 500L
                Thread.sleep(millis)
                runOnUiThread {
                    tetris.moveDownBlock()
                    tetris.newBlockDown()
                }
                viewModelFrameT.setscore(tetris.score, viewModelFrameT.high.value?:0)
            }

            changeGameOverActivity()
        }
    }

    // 나중에 게임 종료 되면 여기서 현재 자기 점수 값 게임오버 액티비티에 넘겨야 해서 함수로 만듦
    fun changeGameOverActivity() {

        val intent = Intent(this, GameOverActivity::class.java)
        intent.putExtra("score", viewModelFrameT.score.value.toString())

        startActivity(intent)
    }


    override fun onPause() {
        super.onPause()

        saveState()
    }

    fun saveState() {
        val pref = getSharedPreferences("prefT", Activity.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putInt("highT", (viewModelFrameT.high.value!!))
        editor.commit()
    }

    override fun onResume() {
        super.onResume()

        restoreState()
    }

    fun restoreState() {
        val pref = getSharedPreferences("prefT", Activity.MODE_PRIVATE)
        if( pref != null && pref.contains("highT") ) {
            viewModelFrameT.setHigh(pref.getInt("highT", 0))
        }
    }


}