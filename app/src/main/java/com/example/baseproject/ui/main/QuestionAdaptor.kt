package com.example.baseproject.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.data.QuizModel
import com.example.baseproject.databinding.ItemQuestionBinding
import com.example.baseproject.utils.load

@SuppressLint("NotifyDataSetChanged")
class QuestionAdaptor : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var dataList: ArrayList<QuizModel> = ArrayList()

    class QuestionHolder(var binding: ItemQuestionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]
        if (holder is QuestionHolder) {
            with(holder.binding) {
                imgPhoto.isVisible = item.hasImage
                if (item.hasImage) {
                    imgPhoto.load(item.imageURL)
                }
                txtQuestion.text = item.question
                txtOptions.text = item.options.joinToString("\n")
                txtAnswer.text = item.answer
            }
        }
    }


    fun setDataList(list: ArrayList<QuizModel>) {
        dataList = list
        notifyDataSetChanged()
    }
}