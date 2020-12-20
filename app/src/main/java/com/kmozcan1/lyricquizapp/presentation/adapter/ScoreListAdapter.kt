package com.kmozcan1.lyricquizapp.presentation.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kmozcan1.lyricquizapp.BR
import com.kmozcan1.lyricquizapp.R
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.ScoreDomainModel

/**
 * Created by Kadir Mert Özcan on 20-Dec-20.
 */
class ScoreListAdapter(private val scoreList: List<ScoreDomainModel>):
    RecyclerView.Adapter<ScoreListAdapter.RecordingSettingsItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordingSettingsItemViewHolder {
        val binding = bindingInflate(
            parent,
            R.layout.score_list_item)
        return RecordingSettingsItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return scoreList.size
    }

    override fun onBindViewHolder(holder: RecordingSettingsItemViewHolder, position: Int) {
        holder.bind(scoreList[position].userName,
            scoreList[position].score)
    }

    inner class RecordingSettingsItemViewHolder(
        private val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(name: String, score: Int) {
            binding.setVariable(BR.userName, name)
            binding.setVariable(BR.score, score.toString())
            binding.executePendingBindings()
        }
    }
}