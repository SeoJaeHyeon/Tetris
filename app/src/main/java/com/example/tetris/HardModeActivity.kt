package com.example.tetris

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
import com.example.tetris.ViewModel.ViewModelArray
import com.example.tetris.databinding.ActivityHardmodeBinding
import com.example.tetris.databinding.ActivityTimeattackmodeBinding
import java.util.*
import kotlin.concurrent.thread

class HardModeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHardmodeBinding

    val viewModelFrameH: ViewModelArray by viewModels()
    val tetris = Tetris()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHardmodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.boardh.rowCount = tetris.ROW // gridLayout의 row
        binding.boardh.columnCount = tetris.COL // gridLayout의 col

        binding.nextblockh.rowCount = tetris.NEXTROW
        binding.nextblockh.columnCount = tetris.NEXTCOL

        // 뷰모델로 score 갱신
        viewModelFrameH.score.observe(this) {
            binding?.txtScoreh?.text = viewModelFrameH.score.value.toString()
        }
        // 뷰모델로 level 갱신
        viewModelFrameH.level.observe(this) {
            binding?.txtLevelh?.text = viewModelFrameH.level.value.toString()
        }
        // 뷰모델로 high 갱신
        viewModelFrameH.high.observe(this) {
            binding?.txtHighh?.text = viewModelFrameH.high.value.toString()
        }


        // 게임 시작 후 gridLayout에 게임화면 생성
        gameFrameSetting(tetris.gameFrame, binding.boardh, tetris.ROW, tetris.COL)
        gameFrameSetting(tetris.nextBlockFrame, binding.nextblockh, tetris.NEXTROW, tetris.NEXTCOL)

        gameRun()

        binding.imgLefth.setOnClickListener {
            tetris.imgLeft()
        }
        binding.imgRighth.setOnClickListener {
            tetris.imgRight()
        }
        binding.imgDownh.setOnClickListener {
            tetris.imgDown()
        }
        binding.imgChangeh.setOnClickListener {
            tetris.imgChange()
        }
        binding.imgStoph.setOnClickListener {
            startActivity(Intent(this, StopActivity::class.java)) // 그만하기 버튼 누르면 StopActivity로 이동 -재현이가 해놓음
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

    fun gameRun() {

        tetris.resetFrame(tetris.nextBlockFrame,tetris.NEXTROW,tetris.NEXTCOL)
        tetris.printNextBlock()
        thread(start = true) {
            while(tetris.run) {
                var millis = 500L
                Thread.sleep(millis)
                runOnUiThread {
                    tetris.moveDownBlock()
                    tetris.newBlockDown()
                }
                viewModelFrameH.setscore(tetris.score, viewModelFrameH.high.value?:0)
                viewModelFrameH.setlevel()
            }

            changeGameOverActivity()
        }
    }

    // 나중에 게임 종료 되면 여기서 현재 자기 점수 값 게임오버 액티비티에 넘겨야 해서 함수로 만듦
    fun changeGameOverActivity() {

        val intent = Intent(this, GameOverActivity::class.java)
        intent.putExtra("score", viewModelFrameH.score.value.toString())

        startActivity(intent)
    }


    override fun onPause() {
        super.onPause()

        saveState()
    }

    fun saveState() {
        val pref = getSharedPreferences("prefH", Activity.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putInt("highH", (viewModelFrameH.high.value!!))
        editor.commit()
    }

    override fun onResume() {
        super.onResume()

        restoreState()
    }

    fun restoreState() {
        val pref = getSharedPreferences("prefH", Activity.MODE_PRIVATE)
        if( pref != null && pref.contains("highH") ) {
            viewModelFrameH.setHigh(pref.getInt("highH", 0))
        }
    }


}