package com.example.baseproject.ui.score

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.data.ScoreModel
import com.example.baseproject.databinding.ItemScoreBinding

@SuppressLint("NotifyDataSetChanged")
class ScoreAdaptor : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var dataList: ArrayList<ScoreModel> = ArrayList()

    class ScoreViewHolder(var binding: ItemScoreBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScoreViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]
        if (holder is ScoreViewHolder) {
            with(holder.binding) {
                txtIndex.text = position.plus(1).toString()
                txtDate.text = item.date
                txtPoint.text = item.points
                txtRightWrong.text = item.rightAnswer?.plus(" - ")?.plus(item.wrongAnswer)
            }
        }
    }


    fun setDataList(list: ArrayList<ScoreModel>) {
        dataList = list
        notifyDataSetChanged()
    }
}