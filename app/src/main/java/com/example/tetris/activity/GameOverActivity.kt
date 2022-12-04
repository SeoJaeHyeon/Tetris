package com.example.tetris.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tetris.component.Ranking
import com.example.tetris.component.RankingsAdapter
import com.example.tetris.databinding.ActivityGameoverBinding
import com.example.tetris.viewModel.ViewModelTetris

class GameOverActivity : AppCompatActivity() {
    /*val rankings = arrayListOf(
        Ranking(1,"Kim", 1800),
        Ranking(2, "Lee", 1700),
        Ranking(3, "Park", 1600),
        Ranking(4, "Choi", 1500),
        Ranking(5, "Jung", 1400),
    )*/

    lateinit var binding: ActivityGameoverBinding

    val viewModel: ViewModelTetris by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameoverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*val imm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        fun hideKeyBoard(v: View) {
            if( v != null ) {
                imm?.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }*/
        val score = intent.getIntExtra("score", 0)
        binding.txtResult.text = score.toString()

        /*binding.btnRank.setOnClickListener {
            //viewModel.retrieveRankings()
        }*/
        binding.etxtInput.setOnClickListener {
            val tempName = binding.etxtInput.getText()
            binding.etxtInput.text = tempName
            val imm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(binding.etxtInput.windowToken, 0)
            viewModel.renewalRanking(tempName.toString(), score)
        }

        binding.recRank.layoutManager = LinearLayoutManager(this)
        binding.recRank.adapter = RankingsAdapter(viewModel.rankings)
        (binding.recRank.layoutManager as LinearLayoutManager).setReverseLayout(true)
        (binding.recRank.layoutManager as LinearLayoutManager).setStackFromEnd(true)

        viewModel.rankings.observe(this) {
            binding?.recRank?.adapter?.notifyDataSetChanged()
        }

        binding.btnRetry.setOnClickListener {
            val intent = Intent(this, ClassicModeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        // 다시 게임으로 돌아가려면 원래 엑티비티로 이동
        binding.btnModeChoice.setOnClickListener {
            val intent = Intent(this, GameModeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }

        // 모드선택 버튼을 누르면 엑티비티 스택에 있던 모드 선택 엑티비티로 다시 이동
        binding.btnStop.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

    }

}