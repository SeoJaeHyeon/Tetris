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

    val ROW = 20 // gameFrame의 row
    val COL = 10 // gameFrame의 col
    val gameFrame = Array(ROW) { // gameFrame의 이차원 배열
        // 각 행들의 배열의 타입은 ImageView ! 나중에 초기화하기 위해 arrayOfNulls 배열 사용
        arrayOfNulls<ImageView>(COL)
    }
    // 블럭들을 랜덤하게 나오기 위해 블럭의 번호를 난수로 저장
    var randomNum: Int = Random.nextInt(0, 7)
    // 랜덤하게 얻은 블럭을 Block에 저장(게임화면에서 움직일 블럭)
    var block: Block = randomBlockChoice(randomNum, 1, COL / 2)

    val compareArr: CompareArray = CompareArray()





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClassicmodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.gridmain.rowCount = ROW // gridLayout의 row
        binding.gridmain.columnCount = COL // gridLayout의 col


        // 게임 시작 후 gridLayout에 게임화면 생성
        gameFrameSetting(gameFrame, binding.gridmain, ROW, COL)
        printBlock() // 움직일 블럭 생성


        //버튼 눌렸을 때
        binding.imgLeft.setOnClickListener {
            removeBlock() // 블럭을 원래 gridLayout의 배경으로 다시 변경
            block.blockLeft(compareArr)// 블럭을 왼쪽으로 움직임
            printBlock() // 움직인 블럭을 다시 그림
        }
        binding.imgRight.setOnClickListener {
            removeBlock() // 블럭을 원래 gridLayout의 배경으로 다시 변경
            block.blockRight(compareArr)// 블럭을 오른쪽으로 움직임
            printBlock() // 움직인 블럭을 다시 그림
        }
        binding.imgDown.setOnClickListener {
            removeBlock() // 블럭을 원래 gridLayout의 배경으로 다시 변경
            block.blockDown(compareArr)// 블럭을 아래로 움직임
            printBlock() // 움직인 블럭을 다시 그림
            newBlockDown()


        }
        binding.imgChange.setOnClickListener {
            removeBlock() // 블럭을 원래 gridLayout의 배경으로 다시 변경
            block.rotation() // 블럭을 회전시킴
            printBlock() // 움직인 블럭을 다시 그림
        }
        binding.imgStop.setOnClickListener {
            // 그만하기 버튼 누르면 StopActivity로 이동
            startActivity(Intent(this, StopActivity::class.java))
        }
    }

    // 다음에 나올 블럭 랜덤하게 지정(블럭의 number를 통해 지정)
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

    // 게임 화면 설정 -> 게임화면에 사용될 이차원배열, 이차원배열을 그려줄 gridLayout, row, col을 매개변수로 받음
    fun gameFrameSetting(arr: Array<Array<ImageView?>>, grid: androidx.gridlayout.widget.GridLayout, row: Int, col: Int) {
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



    // 블럭의 색 결정하는 함수
    fun blockColor(number: Int): Int {
        val color: Int
        when(number) {
            // 블럭의 number에 따라 각각 그려질 블럭 색깔 지정
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

    // 블럭을 gameFrame에 보여주는 함수
    fun printBlock() {
        // 블럭이 지정하는 인덱스에 맞추어 블럭 출력 -> gameFrame의 배열 블럭은 항상 이미지가 있어 null이 될 수 없음
        gameFrame[block.point1.x][block.point1.y]!!.setImageResource(blockColor(block.number))
        gameFrame[block.point2.x][block.point2.y]!!.setImageResource(blockColor(block.number))
        gameFrame[block.point3.x][block.point3.y]!!.setImageResource(blockColor(block.number))
        gameFrame[block.point4.x][block.point4.y]!!.setImageResource(blockColor(block.number))

        compareArr.changeZeroToOne(block)

    }

    // gameFrame에서 블럭을 다시 원래 배경으로 바꾸는 함수(이동할 때 사용)
    fun removeBlock() {
        gameFrame[block.point1.x][block.point1.y]!!.setImageResource(R.drawable.gameframe)
        gameFrame[block.point2.x][block.point2.y]!!.setImageResource(R.drawable.gameframe)
        gameFrame[block.point3.x][block.point3.y]!!.setImageResource(R.drawable.gameframe)
        gameFrame[block.point4.x][block.point4.y]!!.setImageResource(R.drawable.gameframe)
        compareArr.changeOneToZero(block)
    }

    // 블럭이 내려오는 걸 지연하는 함수인데 했는데 적용이 안되서 다시 해야할 듯
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

    fun newBlockDown() {
        if(compareArr.touchFloor(block)) { // 블럭이 바닥에 닿으면 새로운 블럭 생성
            randomNum = Random.nextInt(0, 7)
            block = randomBlockChoice(randomNum, 1, COL / 2)
            printBlock()
        }

    }
}




