package com.example.tetris.component

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
    private val rankingList = ArrayList<Ranking>()
    var database = FirebaseDatabase.getInstance()
    val userRef = database.getReference().child("user").orderByChild("score").limitToLast(5)

    fun observeRanking(rankings: MutableLiveData<ArrayList<Ranking>>) {
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for( datasnapshot in snapshot.children) {
                    val rankingResult = datasnapshot.getValue(Ranking::class.java)
                    rankingResult?.let { rankingList.add(it) }
                }
                rankings.postValue(rankingList)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    fun modifyScore(newValue: Int) {

    }
}