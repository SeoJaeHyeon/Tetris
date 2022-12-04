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
    lateinit var binding: ActivityGameoverBinding

    val viewModel: ViewModelTetris by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameoverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val score = intent.getIntExtra("score", 0) // 게임끝나고 점수 받아옴(Int형)
        binding.txtResult.text = score.toString() // 문자형으로 변환 후 스코어 출력
        
        binding.etxtInput.setOnClickListener {
            val tempName = binding.etxtInput.getText() // 사용자 이름 입력 받아
            binding.etxtInput.text = tempName           // 입력받은 이름 보여주기
            val imm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(binding.etxtInput.windowToken, 0) // 키보드 내리기
            viewModel.renewalRanking(tempName.toString(), score) // 리사이클러뷰 최신화
        }

        binding.recRank.layoutManager = LinearLayoutManager(this)
        binding.recRank.adapter = RankingsAdapter(viewModel.rankings)
        (binding.recRank.layoutManager as LinearLayoutManager).setReverseLayout(true) // 파이어베이스 자료 orderByChild로 받으면 내림차순이라
        (binding.recRank.layoutManager as LinearLayoutManager).setStackFromEnd(true)  // 리사이클러뷰 거꾸로 쌓아서 고득점이 위로 보이게 설정

        viewModel.rankings.observe(this) {
            binding?.recRank?.adapter?.notifyDataSetChanged() //리사이클러뷰 업데이트
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