package com.example.tetris.Component

import com.example.tetris.Block.Block


// (20 x 10) Int 배열로서 게임 화면에서 블럭이 있는 곳은 1, 블럭이 없는 곳은 0으로 표시 하기 위한 클래스
// 0으로 표시 되어 있으면 블럭 이동 가능, blockNumber로 표시되어 있으면 블럭 이동 불가능
class CompareArray {
    val arr: Array<Array<Int>>
    val ROW = 20
    val COL = 10
    init { // 모든 배열 원소 0으로 초기화
        arr = Array( ROW) {
            Array(COL) { 0 }
        }
    }
    // 파라미터의 block이 있는 배열의 위치는 모두 1로 값 변경
    // 게임모드 들의 Activity에서 printBlock 함수 내에서 실행
    fun changeZeroToBlockNumber(block: Block) {
        arr[block.point1.x][block.point1.y] = block.number
        arr[block.point2.x][block.point2.y] = block.number
        arr[block.point3.x][block.point3.y] = block.number
        arr[block.point4.x][block.point4.y] = block.number
    }
    // 파라미터의 block이 있는 배열의 위치는 모두 0으로 변경
    // 블럭 이동시 이동 전 게임모드 들의 Activity에서 removeBlock 함수 내에서 실행
    fun changeBlockNumberToZero(block: Block) {
        arr[block.point1.x][block.point1.y] = 0
        arr[block.point2.x][block.point2.y] = 0
        arr[block.point3.x][block.point3.y] = 0
        arr[block.point4.x][block.point4.y] = 0
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
                arr[i][j] = arr[i - 1][j]

            }

        }
    }
}
