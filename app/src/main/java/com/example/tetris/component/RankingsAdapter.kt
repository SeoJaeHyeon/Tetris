package com.example.tetris.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tetris.databinding.ListRankingsBinding

class RankingsAdapter(val rankings: ArrayList<Ranking>)
    : RecyclerView.Adapter<RankingsAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListRankingsBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(rankings[position])
    }

    override fun getItemCount() = rankings.size

    class Holder(private val binding: ListRankingsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ranking: Ranking) {
            binding.txtRank.text = ranking.rank.toString()
            binding.txtName.text = ranking.name
            binding.txtTop.text = ranking.top.toString()
        }
    }
}