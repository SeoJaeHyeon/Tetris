package com.example.tetris


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.tetris.databinding.ActivityClassicmodeBinding



class ClassicModeActivity : AppCompatActivity() {
    lateinit var binding: ActivityClassicmodeBinding

    val ROW = 29
    val COL = 16
    var boardView = Array(ROW) {
        arrayOfNulls<ImageView>(COL)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClassicmodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.gridmain.rowCount = ROW
        binding.gridmain.columnCount = COL

        initRender(boardView, binding.gridmain, ROW, COL)
        //버튼 눌렸을 때
        binding.imgLeft.setOnClickListener {
            left()
        }
        binding.imgRight.setOnClickListener {
            right()
        }
        binding.imgDown.setOnClickListener {
            down()
        }
        binding.imgChange.setOnClickListener {
            change()
        }
        binding.imgStop.setOnClickListener {
            startActivity(Intent(this, StopActivity::class.java)) // 그만하기 버튼 누르면 StopActivity로 이동 -재현이가 해놓음
        }
    }

    fun initRender(arr: Array<Array<ImageView?>>, grid: androidx.gridlayout.widget.GridLayout, row: Int, col: Int) {
        val inflater = LayoutInflater.from(this)
        for (i in 0 until row) {
            for (j in 0 until col) {
                arr!![i][j] =
                    inflater.inflate(R.layout.inflate_image, grid, false) as ImageView
                grid.addView(arr[i][j])
            }
        }
    }

}

