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
import com.example.tetris.ViewModel.ViewModelArray
import com.example.tetris.databinding.ActivityClassicmodeBinding
import kotlin.concurrent.thread

import java.util.*


class ClassicModeActivity : AppCompatActivity() {
    lateinit var binding: ActivityClassicmodeBinding

    val ROW = 20 // gameFrame의 row
    val COL = 10 // gameFrame의 col
    val gameFrame = Array(ROW) { // gameFrame의 이차원 배열
        // 각 행들의 배열의 타입은 ImageView ! 나중에 초기화하기 위해 arrayOfNulls 배열 사용
        arrayOfNulls<ImageView>(COL)
    }
    val nextBlockFrame = Array(4) {
        arrayOfNulls<ImageView>(3)
    }
    private val random = Random() //seed를 랜덤하게 나오기위해 seed 랜덤 저장
    // 블럭들을 랜덤하게 나오기 위해 블럭의 번호를 난수로 저장
    var randomNum: Int = random.nextInt(7) + 1 //boundary라 + 1 해줌
    // 랜덤하게 얻은 블럭을 Block에 저장(게임화면에서 움직일 블럭)
    var block: Block = randomBlockChoice(randomNum, 1, COL / 2)
    var run = true
    var erase: Int = 0

    val viewModelFrame: ViewModelArray by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClassicmodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.gridmain.rowCount = ROW // gridLayout의 row
        binding.gridmain.columnCount = COL // gridLayout의 col

        binding.nextblock.rowCount = 4
        binding.nextblock.columnCount = 3


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
        gameFrameSetting(gameFrame, binding.gridmain, ROW, COL)
        gameFrameSetting(nextBlockFrame, binding.nextblock, 4, 3)

        gameRun()

        //버튼 눌렸을 때
        binding.imgLeft.setOnClickListener {
            removeBlock() // 블럭을 원래 gridLayout의 배경으로 다시 변경
            block.blockLeft(viewModelFrame)
            printBlock() // 움직인 블럭을 다시 그림
        }
        binding.imgRight.setOnClickListener {
            removeBlock() // 블럭을 원래 gridLayout의 배경으로 다시 변경
            block.blockRight(viewModelFrame)
            printBlock() // 움직인 블럭을 다시 그림
        }
        binding.imgDown.setOnClickListener {
            removeBlock() // 블럭을 원래 gridLayout의 배경으로 다시 변경
            block.blockDown(viewModelFrame)
            printBlock() // 움직인 블럭을 다시 그림
            newBlockDown()
        }
        binding.imgChange.setOnClickListener {
            removeBlock() // 블럭을 원래 gridLayout의 배경으로 다시 변경
            block.rotation(viewModelFrame)
            printBlock() // 움직인 블럭을 다시 그림
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
                arr!![i][j] = inflater.inflate(R.layout.gameframe_image, grid, false) as ImageView
                // 위의 배열을 gridLayout에 맞추어 이미지 삽입
                grid.addView(arr[i][j])
            }
        }
    }

    // 다음에 나올 블럭 랜덤하게 지정(블럭의 number를 통해 지정)
    fun randomBlockChoice(number: Int, row: Int, col: Int): Block {
        val randomBlock: Block
        when(number) {
            1 -> randomBlock = BlockI(row, col)
            2 -> randomBlock = BlockO(row, col)
            3 -> randomBlock = BlockZ(row, col)
            4 -> randomBlock = BlockS(row, col)
            5 -> randomBlock = BlockJ(row, col)
            6 -> randomBlock = BlockL(row, col)
            else -> randomBlock = BlockT(row, col)
        }
        return randomBlock
    }

    // 블럭의 색 결정하는 함수
    fun blockColor(number: Int): Int {
        val color: Int
        when(number) {
            // 블럭의 number에 따라 각각 그려질 블럭 색깔 지정
            1 -> color = R.drawable.skyblueblockl
            2 -> color = R.drawable.yellowblocko
            3 -> color = R.drawable.redblockz
            4 -> color = R.drawable.greenblocks
            5 -> color = R.drawable.deepblueblockj
            6 -> color = R.drawable.orangeblockl
            7 -> color = R.drawable.purpleblockt
            else -> color = R.drawable.gameframe
        }
        return color
    }

    // 블럭을 gameFrame에 보여주는 함수
    fun printBlock() {
        viewModelFrame.changeZeroToBlockNumber(block)
        // 블럭이 지정하는 인덱스에 맞추어 블럭 출력 -> gameFrame의 배열 블럭은 항상 이미지가 있어 null이 될 수 없음
        gameFrame[block.point1.x][block.point1.y]!!.setImageResource(blockColor(block.number))
        gameFrame[block.point2.x][block.point2.y]!!.setImageResource(blockColor(block.number))
        gameFrame[block.point3.x][block.point3.y]!!.setImageResource(blockColor(block.number))
        gameFrame[block.point4.x][block.point4.y]!!.setImageResource(blockColor(block.number))
    }

    // 다음에 올 블럭을 nextBlockFrame에 보여주는 함수
    fun printNextBlock() {
        resetFrame(nextBlockFrame,4,3) //화면초기화 후
        val blockPreView = randomBlockChoice(randomNum, 1, 1) //새 블럭 객체
        nextBlockFrame[blockPreView.point1.x][blockPreView.point1.y]!!.setImageResource((blockColor(blockPreView.number)))
        nextBlockFrame[blockPreView.point2.x][blockPreView.point2.y]!!.setImageResource((blockColor(blockPreView.number)))
        nextBlockFrame[blockPreView.point3.x][blockPreView.point3.y]!!.setImageResource((blockColor(blockPreView.number)))
        nextBlockFrame[blockPreView.point4.x][blockPreView.point4.y]!!.setImageResource((blockColor(blockPreView.number)))
    }

    // gameFrame에서 블럭을 다시 원래 배경으로 바꾸는 함수(이동할 때 사용)
    fun removeBlock() {
        viewModelFrame.changeBlockNumberToZero(block)
        gameFrame[block.point1.x][block.point1.y]!!.setImageResource(R.drawable.gameframe)
        gameFrame[block.point2.x][block.point2.y]!!.setImageResource(R.drawable.gameframe)
        gameFrame[block.point3.x][block.point3.y]!!.setImageResource(R.drawable.gameframe)
        gameFrame[block.point4.x][block.point4.y]!!.setImageResource(R.drawable.gameframe)
    }

    // Frame 초기화를 위해 만듦
    fun resetFrame(arr: Array<Array<ImageView?>>, row: Int, col: Int) {
        for(i in 0 until row)
            for(j in 0 until col)
                arr[i][j]!!.setImageResource(blockColor(R.drawable.gameframe))
    }

    fun newBlockDown() {
        if(block.point1.x == 1 && block.touchBottomBlock(viewModelFrame)) {  //블럭이 생성되는 곳과 블럭이 닿았을떄 게임오버
            run = false
        } else {
            if(block.point1.x == 2){ //세 블럭생성 후 다음블럭 보여줌
            randomNum = random.nextInt(7) + 1
            printNextBlock()
        }
            if(viewModelFrame.touchFloor(block) || block.touchBottomBlock(viewModelFrame)) { // 블럭이 바닥에 닿으면 새로운 블럭 생성
                while(isDelete()) {
                    DeleteBlocksChecking()
                }
                printBlock()  //printBlock이랑 block객체 순서 중요
                block = randomBlockChoice(randomNum, 1, COL / 2)
            }
        }
    }

    fun DeleteBlocks(row: Int) {
        viewModelFrame.destroy(row)
        erase += 1 // 한 줄 지워질 때마다 erase 1씩 증가
        viewModelFrame.setscore(erase) // erase수에 따라 스코어 계산
        viewModelFrame.setlevel(viewModelFrame.score.value?:1) // level 갱신
        var h: Int = 0
        var s: Int = 0
        viewModelFrame.high.value?.let {
            h = it
        }
        viewModelFrame.score.value?.let {
            s = it
        }?:0
        if(h < s) viewModelFrame.setHigh(s)
        printAllGameFrame()
    }

    fun DeleteBlocksChecking() {
        for(row in ROW-1 downTo 0) {
            for(col in 0 until COL) {
                var isBlock = false
                viewModelFrame.arr.value?. let{
                    isBlock = it[row][col] == 0
                }
                if( isBlock ) break
                else{
                    if(col == COL - 1) {
                        DeleteBlocks(row)
                    }
                }
            }
        }
    }

    fun isDelete(): Boolean {
        for(i in ROW-1 downTo 0) {
            for(j in 0 until COL) {
                var isBlock = true
                viewModelFrame.arr.value?. let{
                    isBlock = it[i][j] == 0
                }
                if( isBlock ) break
                else{
                    if(j == COL - 1) {
                        return true
                    }
                }
            }
        }
        return false
    }

    fun printAllGameFrame() {
        for(i in 0 until ROW) {
            for (j in 0 until  COL){
                var blockNum = 0
                viewModelFrame.arr.value?. let{
                    blockNum = it[i][j]
                }
                printEachBlock(blockNum, gameFrame, i, j)
            }
        }
    }

    fun printEachBlock(number: Int, arr: Array<Array<ImageView?>>, row: Int, col: Int) {
        when(number) {
            1 -> arr[row][col]!!.setImageResource(R.drawable.skyblueblockl)
            2 -> arr[row][col]!!.setImageResource(R.drawable.yellowblocko)
            3 -> arr[row][col]!!.setImageResource(R.drawable.redblockz)
            4 -> arr[row][col]!!.setImageResource(R.drawable.greenblocks)
            5 -> arr[row][col]!!.setImageResource(R.drawable.deepblueblockj)
            6 -> arr[row][col]!!.setImageResource(R.drawable.orangeblockl)
            7 -> arr[row][col]!!.setImageResource(R.drawable.purpleblockt)
            else -> arr[row][col]!!.setImageResource(R.drawable.gameframe)
        }
    }


    // 나중에 게임 종료 되면 여기서 현재 자기 점수 값 게임오버 액티비티에 넘겨야 해서 함수로 만듦
    fun changeGameOverActivity() {

        val intent = Intent(this, GameOverActivity::class.java)
        intent.putExtra("score", viewModelFrame.score.value.toString())

        startActivity(intent)
    }


    // 자동으로 내려가는 함수
    fun moveDownBlock() {
        removeBlock()
        block.blockDown(viewModelFrame)
        printBlock()
    }

    fun gameRun() {
        resetFrame(nextBlockFrame,4,3)
        printNextBlock()
        thread(start = true) {
            while(run) {
                var millis = 1000L - (viewModelFrame.level.value?.times(25) ?:0)
                Thread.sleep(millis)
                runOnUiThread {
                    moveDownBlock()
                    newBlockDown()
                }
            }
            changeGameOverActivity()
        }
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



/*
    fun printArray() {
        viewModelFrame.arr.value?.let {
            Log.e(
                "#######",
                "${it[0][0]} ${it[0][1]} ${it[0][2]} ${it[0][3]} ${it[0][4]} ${it[0][5]} ${it[0][6]} ${it[0][7]} ${it[0][8]} ${it[0][9]}"
            )
            Log.e(
                "#######",
                "${it[1][0]} ${it[1][1]} ${it[1][2]} ${it[1][3]} ${it[1][4]} ${it[1][5]} ${it[1][6]} ${it[1][7]} ${it[1][8]} ${it[1][9]}"
            )
            Log.e(
                "#######",
                "${it[2][0]} ${it[2][1]} ${it[2][2]} ${it[2][3]} ${it[2][4]} ${it[2][5]} ${it[2][6]} ${it[2][7]} ${it[2][8]} ${it[2][9]}"
            )
            Log.e(
                "#######",
                "${it[3][0]} ${it[3][1]} ${it[3][2]} ${it[3][3]} ${it[3][4]} ${it[3][5]} ${it[3][6]} ${it[3][7]} ${it[3][8]} ${it[3][9]}"
            )
            Log.e(
                "#######",
                "${it[4][0]} ${it[4][1]} ${it[4][2]} ${it[4][3]} ${it[4][4]} ${it[4][5]} ${it[4][6]} ${it[4][7]} ${it[4][8]} ${it[4][9]}"
            )
            Log.e(
                "#######",
                "${it[5][0]} ${it[5][1]} ${it[5][2]} ${it[5][3]} ${it[5][4]} ${it[5][5]} ${it[5][6]} ${it[5][7]} ${it[5][8]} ${it[5][9]}"
            )
            Log.e(
                "#######",
                "${it[6][0]} ${it[6][1]} ${it[6][2]} ${it[6][3]} ${it[6][4]} ${it[6][5]} ${it[6][6]} ${it[6][7]} ${it[6][8]} ${it[6][9]}"
            )
            Log.e(
                "#######",
                "${it[7][0]} ${it[7][1]} ${it[7][2]} ${it[7][3]} ${it[7][4]} ${it[7][5]} ${it[7][6]} ${it[7][7]} ${it[7][8]} ${it[7][9]}"
            )
            Log.e(
                "#######",
                "${it[8][0]} ${it[8][1]} ${it[8][2]} ${it[8][3]} ${it[8][4]} ${it[8][5]} ${it[8][6]} ${it[8][7]} ${it[8][8]} ${it[8][9]}"
            )
            Log.e(
                "#######",
                "${it[9][0]} ${it[9][1]} ${it[9][2]} ${it[9][3]} ${it[9][4]} ${it[9][5]} ${it[9][6]} ${it[9][7]} ${it[9][8]} ${it[9][9]}"
            )
            Log.e(
                "#######",
                "${it[10][0]} ${it[10][1]} ${it[10][2]} ${it[10][3]} ${it[10][4]} ${it[10][5]} ${it[10][6]} ${it[10][7]} ${it[10][8]} ${it[10][9]}"
            )
            Log.e(
                "#######",
                "${it[11][0]} ${it[11][1]} ${it[11][2]} ${it[11][3]} ${it[11][4]} ${it[11][5]} ${it[11][6]} ${it[11][7]} ${it[11][8]} ${it[11][9]}"
            )
            Log.e(
                "#######",
                "${it[12][0]} ${it[12][1]} ${it[12][2]} ${it[12][3]} ${it[12][4]} ${it[12][5]} ${it[12][6]} ${it[12][7]} ${it[12][8]} ${it[12][9]}"
            )
            Log.e(
                "#######",
                "${it[13][0]} ${it[13][1]} ${it[13][2]} ${it[13][3]} ${it[13][4]} ${it[13][5]} ${it[13][6]} ${it[13][7]} ${it[13][8]} ${it[13][9]}"
            )
            Log.e(
                "#######",
                "${it[14][0]} ${it[14][1]} ${it[14][2]} ${it[14][3]} ${it[14][4]} ${it[14][5]} ${it[14][6]} ${it[14][7]} ${it[14][8]} ${it[14][9]}"
            )
            Log.e(
                "#######",
                "${it[15][0]} ${it[15][1]} ${it[15][2]} ${it[15][3]} ${it[15][4]} ${it[15][5]} ${it[15][6]} ${it[15][7]} ${it[15][8]} ${it[15][9]}"
            )
            Log.e(
                    "#######",
            "${it[16][0]} ${it[16][1]} ${it[16][2]} ${it[16][3]} ${it[16][4]} ${it[16][5]} ${it[16][6]} ${it[16][7]} ${it[16][8]} ${it[16][9]}"
            )
            Log.e(
                "#######",
                "${it[17][0]} ${it[17][1]} ${it[17][2]} ${it[17][3]} ${it[17][4]} ${it[17][5]} ${it[17][6]} ${it[17][7]} ${it[17][8]} ${it[17][9]}"
            )
            Log.e(
                "#######",
                "${it[18][0]} ${it[18][1]} ${it[18][2]} ${it[18][3]} ${it[18][4]} ${it[18][5]} ${it[18][6]} ${it[18][7]} ${it[18][8]} ${it[18][9]}"
            )
            Log.e(
                "#######",
                "${it[19][0]} ${it[19][1]} ${it[19][2]} ${it[19][3]} ${it[19][4]} ${it[19][5]} ${it[19][6]} ${it[19][7]} ${it[19][8]} ${it[19][9]}"
            )



        }
    }

 */
}




