package com.example.tetris.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.tetris.databinding.ListRankingsBinding

class RankingsAdapter(val rankings: LiveData<ArrayList<Ranking>>)
    : RecyclerView.Adapter<RankingsAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListRankingsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(rankings.value?.get(position), position) // 랭킹 표시하려고 position까지 인자로 넣음
    }

    override fun getItemCount(): Int = rankings.value?.size ?: 0 // 받아온 Ranking의 수

    class Holder(private val binding: ListRankingsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ranking: Ranking?, position: Int) {
            binding.txtRank.text = (5-position).toString() // 밑에서부터 쌓을거라 랭킹 순서는 5부터 0까지
            binding.txtName.text = ranking?.name
            binding.txtTop.text = ranking?.score.toString()
        }
    }
}