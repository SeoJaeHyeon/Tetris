package com.example.tetris.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.tetris.databinding.ListRankingsBinding

class RankingsAdapter(val rankings: LiveData<ArrayList<Ranking>>)
    : RecyclerView.Adapter<RankingsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListRankingsBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(rankings.value?.get(position), position)
    }

    override fun getItemCount(): Int = rankings.value?.size ?: 0

    class ViewHolder(private val binding: ListRankingsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ranking: Ranking?, position: Int) {
            binding.txtRank.text = (5-position).toString()
            binding.txtName.text = ranking?.name
            binding.txtTop.text = ranking?.score.toString()
            /*ranking?.let {
                binding.txtRank.text = "1"
                binding.txtName.text = it.name
                binding.txtTop.text = it.top.toString()
            }*/
        }
    }
}