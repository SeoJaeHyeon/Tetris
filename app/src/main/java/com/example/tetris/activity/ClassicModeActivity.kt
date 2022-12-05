package com.example.tetris.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.gridlayout.widget.GridLayout
import com.example.tetris.component.Tetris
import com.example.tetris.R
import com.example.tetris.viewModel.ViewModelTetris
import com.example.tetris.databinding.ActivityClassicmodeBinding
import kotlin.concurrent.thread


class ClassicModeActivity : AppCompatActivity() {
    lateinit var binding: ActivityClassicmodeBinding

    val viewModelFrame: ViewModelTetris by viewModels()
    val tetris = Tetris()
    val gamemode: String = "classic"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClassicmodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.gridmain.rowCount = tetris.gameState.ROW // gridLayout의 row
        binding.gridmain.columnCount = tetris.gameState.COL // gridLayout의 col

        binding.nextblock.rowCount = tetris.gameState.NEXTROW
        binding.nextblock.columnCount = tetris.gameState.NEXTCOL


        // 뷰모델로 score 갱신
        viewModelFrame.score.observe(this) {
            binding?.txtScore?.text = viewModelFrame.score.value.toString()
        }
        // 뷰모델로 level 갱신
        viewModelFrame.level.observe(this) {
            binding?.txtLevel?.text = viewModelFrame.level.value.toString()
        }
        // 뷰모델로 high 갱신
        viewModelFrame.high.observe(this) {
            binding?.txtHigh?.text = viewModelFrame.high.value.toString()
        }



        // 게임 시작 후 gridLayout에 게임화면 생성
        gameFrameSetting(tetris.gameState.gameFrame, binding.gridmain, tetris.gameState.ROW, tetris.gameState.COL)
        gameFrameSetting(tetris.gameState.nextBlockFrame, binding.nextblock, tetris.gameState.NEXTROW, tetris.gameState.NEXTCOL)

        gameRun()

        //버튼 눌렸을 때
        binding.imgLeft.setOnClickListener {
            tetris.imgLeft()
        }
        binding.imgRight.setOnClickListener {
            tetris.imgRight()
        }
        binding.imgDown.setOnClickListener {
            tetris.imgDown()
        }
        binding.imgChange.setOnClickListener {
            tetris.imgChange()
        }
        binding.imgStop.setOnClickListener {
            // 그만하기 버튼 누르면 StopActivity로 이동
            startActivity(Intent(this, StopActivity::class.java))
        }

    }
    // 게임 화면 설정 -> 게임화면에 사용될 이차원배열, 이차원배열을 그려줄 gridLayout, row, col을 매개변수로 받음
    fun gameFrameSetting(arr: Array<Array<ImageView?>>, grid: GridLayout, row: Int, col: Int) {
        // 해당 context(환경에 대한 전역정보) LayoutInflater를 가져옴(xml을 View객체로 변환)
        val inflater = LayoutInflater.from(this)
        for (i in 0 until row) {
            for (j in 0 until col) {
                // 게임화면에서 사용되는 이차원 배열의 각 원소에 gameframe_image를 넣음
                arr.let {
                    it[i][j] = inflater.inflate(R.layout.gameframe_image, grid, false) as ImageView
                }
                // 위의 배열을 gridLayout에 맞추어 이미지 삽입
                grid.addView(arr[i][j])
            }
        }
    }

    fun gameRun() {

        tetris.resetFrame(tetris.gameState.nextBlockFrame,tetris.gameState.NEXTROW,tetris.gameState.NEXTCOL)
        tetris.printNextBlock()
        thread(start = true) {
            while(tetris.gameState.run) {
                var millis = 1000L - (viewModelFrame.level.value?.times(25) ?:0)
                Thread.sleep(millis)
                runOnUiThread {
                    tetris.moveDownBlock()
                    tetris.newBlockDown()
                }
                viewModelFrame.setscore(tetris.score, viewModelFrame.high.value?:0)
                viewModelFrame.setlevel()
            }
            changeGameOverActivity()
        }
    }

    // 나중에 게임 종료 되면 여기서 현재 자기 점수 값 게임오버 액티비티에 넘겨야 해서 함수로 만듦
    fun changeGameOverActivity() {

        val intent = Intent(this, GameOverActivity::class.java)
        intent.putExtra("score", viewModelFrame.score.value)
        intent.putExtra("gamemode", gamemode)

        startActivity(intent)
    }


    override fun onPause() {
        super.onPause()

        saveState()
    }

    fun saveState() {
        val pref = getSharedPreferences("pref", Activity.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putInt("high", (viewModelFrame.high.value!!))
        editor.commit()
    }

    override fun onResume() {
        super.onResume()

        restoreState()
    }

    fun restoreState() {
        val pref = getSharedPreferences("pref", Activity.MODE_PRIVATE)
        if( pref != null && pref.contains("high") ) {
            viewModelFrame.setHigh(pref.getInt("high", 0))
        }
    }



}



