package com.example.tetris


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.tetris.Block.*
import com.example.tetris.databinding.ActivityClassicmodeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random


class ClassicModeActivity : AppCompatActivity() {
    lateinit var binding: ActivityClassicmodeBinding

    val ROW = 20
    val COL = 10
    var gameFrame = Array(ROW) {
        arrayOfNulls<ImageView>(COL)
    }
    var randomNum: Int = Random.nextInt(0, 7)
    var block: Block = randomBlockChoice(randomNum, 1, COL / 2)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClassicmodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.gridmain.rowCount = ROW
        binding.gridmain.columnCount = COL

        gameFrameSetting(gameFrame, binding.gridmain, ROW, COL)
        printBlock()


        //버튼 눌렸을 때
        binding.imgLeft.setOnClickListener {
            removeBlock()
            block.blockLeft()
            printBlock()
        }
        binding.imgRight.setOnClickListener {
            removeBlock()
            block.blockRight()
            printBlock()
        }
        binding.imgDown.setOnClickListener {
            removeBlock()
            block.blockDown()
            printBlock()
        }
        binding.imgChange.setOnClickListener {
            removeBlock()
            block.rotation()
            printBlock()
        }
        binding.imgStop.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    StopActivity::class.java
                )
            ) // 그만하기 버튼 누르면 StopActivity로 이동
        }
    }

    fun randomBlockChoice(number: Int, row: Int, col: Int): Block {
        val randomBlock: Block
        when(number) {
            0 -> randomBlock = BlockI(row, col)
            1 -> randomBlock = BlockO(row, col)
            2 -> randomBlock = BlockZ(row, col)
            3 -> randomBlock = BlockS(row, col)
            4 -> randomBlock = BlockJ(row, col)
            5 -> randomBlock = BlockL(row, col)
            else -> randomBlock = BlockT(row, col)
        }
        return randomBlock
    }


    fun gameFrameSetting(arr: Array<Array<ImageView?>>, grid: androidx.gridlayout.widget.GridLayout, row: Int, col: Int) {
        val inflater = LayoutInflater.from(this)
        for (i in 0 until row) {
            for (j in 0 until col) {
                arr!![i][j] = inflater.inflate(R.layout.inflate_image, grid, false) as ImageView
                grid.addView(arr[i][j])
            }
        }
    }
    fun blockColor(number: Int): Int {
        val color: Int
        when(number) {
            0 -> color = R.drawable.skyblueblockl
            1 -> color = R.drawable.yellowblocko
            2 -> color = R.drawable.redblockz
            3 -> color = R.drawable.greenblocks
            4 -> color = R.drawable.deepblueblockj
            5 -> color = R.drawable.orangeblockl
            else -> color = R.drawable.purpleblockt
        }
        return color
    }


    fun printBlock() {
        gameFrame[block.point1.x][block.point1.y]!!.setImageResource(blockColor(block.number))
        gameFrame[block.point2.x][block.point2.y]!!.setImageResource(blockColor(block.number))
        gameFrame[block.point3.x][block.point3.y]!!.setImageResource(blockColor(block.number))
        gameFrame[block.point4.x][block.point4.y]!!.setImageResource(blockColor(block.number))
    }

    fun removeBlock() {
        gameFrame[block.point1.x][block.point1.y]!!.setImageResource(R.drawable.gameframe)
        gameFrame[block.point2.x][block.point2.y]!!.setImageResource(R.drawable.gameframe)
        gameFrame[block.point3.x][block.point3.y]!!.setImageResource(R.drawable.gameframe)
        gameFrame[block.point4.x][block.point4.y]!!.setImageResource(R.drawable.gameframe)
    }


    fun runDelay(second: Long) {
        runBlocking {
            launch {
                delay(second)
            }
        }
    }

    // 자동으로 내려가는 함수
    fun moveDownBlock() {

    }

}




