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
    //var database: DatabaseReference = Firebase.database.reference
    var database = FirebaseDatabase.getInstance()
    val userRef = database.getReference().child("user").orderByChild("score")

    fun observeRanking(rankings: MutableLiveData<ArrayList<Ranking>>) {
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //rankings.postValue(snapshot.value as ArrayList<Ranking>?)
                //val rList = ArrayList<Ranking>()
                for( datasnapshot in snapshot.children) {
                    //rList.plus(datasnapshot.value)
                    val rankingResult = datasnapshot.getValue(Ranking::class.java)
                    rankingResult?.let { rankingList.add(it) }
                //rList.add(Ranking(datasnapshot.child("name").toString(), datasnapshot.child("score").toString()))
                    //if (datasnapshot?.)let {rList.add(datasnapshot.getValue<Ranking>())}
                    //datasnapshot.getValue<Ranking>()?.let { rList.add(it) }

                }
                rankings.postValue(rankingList)



                /*for(dataSnapshot in snapshot.children) {
                    val ranking = dataSnapshot.getValue(Ranking::class.java)

                    //val name = dataSnapshot.child("name").value
                    //val record = dataSnapshot.child("score").value
                    //val rList = ArrayList<String>()

                    ranking?: null?.let { rankingList.add(0, it) }
                    }
                rankings.postValue() rankingList
                    //ranking?.name = ranking
                    //ranking?.top =
                    //rankings.postValue(ranking)*/
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}