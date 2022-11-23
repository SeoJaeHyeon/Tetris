package com.example.tetris.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelTetris : ViewModel() {

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    private val _level = MutableLiveData<Int>()
    val level: LiveData<Int> get() = _level

    private val _high = MutableLiveData<Int>()
    val high: LiveData<Int> get() = _high


    init {

        _score.value = 0 // 시작점수 0
        _level.value = 1 // 시작레벨 1
        _high.value = 0 // 시작 최고 점수 0
    }

    fun setHigh(score: Int) {
        //_high.value = score
        _high.postValue(score)
    }

    fun setscore(score: Int, high: Int) { // erase를 인자로 받아서 점수 계산
        //_score.value = score
        _score.postValue(score)
        if( score > high) setHigh(score)
    }
    fun setlevel() { // 점수를 인자로 받아서 레벨 업업
        //_level.value = _score.value?.div(100)?.plus(1)
        _level.postValue(_score.value?.div(100)?.plus(1))
    }


}
