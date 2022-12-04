package com.example.tetris.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tetris.component.Ranking
import com.example.tetris.component.Tetris
import com.example.tetris.component.TetrisRepository

class ViewModelTetris : ViewModel() {

    private val repository = TetrisRepository()

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    private val _level = MutableLiveData<Int>()
    val level: LiveData<Int> get() = _level

    private val _high = MutableLiveData<Int>()
    val high: LiveData<Int> get() = _high

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() =_userName

    private val _rankings = MutableLiveData<ArrayList<Ranking>>()
    val rankings : LiveData<ArrayList<Ranking>> = _rankings

    init {
        _userName.value = ""
        _score.value = 0 // 시작점수 0
        _level.value = 1 // 시작레벨 1
        _high.value = 0 // 시작 최고 점수 0
        repository.observeRanking(_rankings) // 파이어베이스에서 읽어온 랭킹 리스트 받아옴
    }
    fun renewalRanking(userName: String, gamemode: String, score: Int) { // 새로운 사용자 정보가 들어오면
        repository.modifyScore(userName, gamemode, score)         // 데이터 업데이트 해주는 함수 호출
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
