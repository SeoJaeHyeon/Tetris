package com.example.tetris.component

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class TetrisRepository {
    private val rankingList = ArrayList<Ranking>() // 파이어베이스에서 읽어온 데이터를 저장할 어레이리스트
    var database = FirebaseDatabase.getInstance().getReference() // 데이터베이스에서 자료를 받아오는데
    //val userRef = database.child("user").orderByChild("score").limitToLast(5)
    // user 아래 children 데이터들을 가져올건데         점수를 기준으로 오름차순       맨 마지막에서 5개

    fun observeRanking(rankings: MutableLiveData<ArrayList<Ranking>>, gamemode: String) {
        val userRef = database.child(gamemode).orderByChild("score").limitToLast(5)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                rankingList.clear()                                          // 기존 랭킹리스트 초기화
                for( datasnapshot in snapshot.children) {                    // user 아래 child마다
                    val ranking = datasnapshot.getValue(Ranking::class.java) // 데이터를 Ranking 객체로 받아옴 
                    ranking?.let { rankingList.add(it) }                     // 널인지 확인 후 랭킹리스트에 추가
                }
                rankings.postValue(rankingList)                              // 인자로 받아온 rankings에 랭킹리스트 넣어주기
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    fun modifyScore(userName: String, gamemode: String, newValue: Int) {    // 사용자, 데이터 들어오면
        val ranking = Ranking(userName, newValue)                           // Ranking 객체를 만들어서
        database.child(gamemode).child(userName).setValue(ranking)          // user 하위에 사용자 이름의 클래스에 점수 업데이트
    }                                                                       // 받아온 이름으로 된 사용자 정보 없으면 새로 추가해줌
}