package com.example.tetris.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tetris.Block.Block

class ViewModelArray : ViewModel() {
    val ROW = 20
    val COL = 10

    private val _arr = MutableLiveData<Array<Array<Int>>>()
    val arr: LiveData<Array<Array<Int>>> get() = _arr

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    private val _level = MutableLiveData<Int>()
    val level: LiveData<Int> get() = _level

    private val _high = MutableLiveData<Int>()
    val high: LiveData<Int> get() = _high


    init { // 모든 배열 원소 0으로 초기화
        _arr.value = Array( ROW) {
            Array(COL) { 0 }
        }
        _score.value = 0 // 시작점수 0
        _level.value = 1 // 시작레벨 1
        _high.value = 0 // 시작 최고 점수 0
    }

    fun setHigh(score: Int) {
        _high.value = score
    }

    fun setscore(erase: Int) { // erase를 인자로 받아서 점수 계산
        _score.value = erase * 20
    }
    fun setlevel(score: Int) { // 점수를 인자로 받아서 레벨 업업
        _level.value = _score.value?.div(100)?.plus(1)
    }

    fun changeZeroToBlockNumber(block: Block) {
        _arr.value?.let {
            it[block.point1.x][block.point1.y] = block.number
            it[block.point2.x][block.point2.y] = block.number
            it[block.point3.x][block.point3.y] = block.number
            it[block.point4.x][block.point4.y] = block.number
        }
    }

    fun changeBlockNumberToZero(block: Block) {
        _arr.value?.let {
            it[block.point1.x][block.point1.y] = 0
            it[block.point2.x][block.point2.y] = 0
            it[block.point3.x][block.point3.y] = 0
            it[block.point4.x][block.point4.y] = 0
        }
    }
    // 블럭이 gameFrame의 바닥에 닿았는 지 확인
    fun touchFloor(block: Block): Boolean {
        // 한블럭에서 각각의 블럭들(4개) 중 하나라도 (row + 1) 이 ROW 값과 같으면 바닥에 닿은것임
        return (block.point1.x + 1 == ROW) || (block.point2.x + 1 == ROW) ||
                (block.point3.x + 1 == ROW) || (block.point4.x + 1) == ROW
    }
    // 블럭이 gameFrame의 왼쪽 벽에 닿았는 지 확인
    fun touchLeft(block: Block): Boolean {
        // 한블럭에서 각각의 블럭들(4개) 중 하나라도 (col == 0) 이면 왼쪽 벽에 닿은것임
        return (block.point1.y == 0) || (block.point2.y == 0) ||
                (block.point3.y == 0) || (block.point4.y == 0)
    }
    // 블럭이 gameFrame의 왼쪽 벽에 닿았는 지 확인
    fun touchRight(block: Block): Boolean {
        // 한블럭에서 각각의 블럭들(4개) 중 하나라도 (col + 1 == COL) 이면 오른쪽 벽에 닿은것임
        return (block.point1.y + 1 == COL) || (block.point2.y + 1 == COL) ||
                (block.point3.y + 1 == COL) || (block.point4.y + 1 == COL)
    }

    fun destroy(row: Int) {
        for(i in row downTo 1) {
            for( j in 0 until COL) {
                _arr.value?.let {
                    it[i][j] = it[i - 1][j]
                }
            }

        }
    }

}
