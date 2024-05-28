package com.example.baseproject.ui.quiz

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.R
import com.example.baseproject.data.OptionsModel
import com.example.baseproject.databinding.ItemOptionsBinding

@SuppressLint("NotifyDataSetChanged")
class OptionsAdaptor : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var dataList: ArrayList<OptionsModel> = ArrayList()
    private var answer: String? = null

    class QuestionHolder(var binding: ItemOptionsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemOptionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]
        val submittedAnswer = dataList.find { it.isselected == true }
        if (holder is QuestionHolder) {
            with(holder.binding) {
                txtAbc.text = item.index
                txtOptions.text = item.options
                if (submittedAnswer != null) {
                    if (item.isselected == true && answer == item.options) {
                        llRoot.setBackgroundResource(R.drawable.option_bg_true)
                        imgTrueFalse.setImageResource(R.drawable.true_check)
                        txtAbc.isSelected = true
                        txtOptions.isSelected = true
                        imgTrueFalse.isVisible = item.isselected == true
                    } else if (item.isselected == true) {
                        llRoot.setBackgroundResource(R.drawable.option_bg_wrong)
                        imgTrueFalse.setImageResource(R.drawable.wrong_check)
                        txtAbc.isSelected = true
                        txtOptions.isSelected = true
                        imgTrueFalse.isVisible = true
                    } else {
                        txtAbc.isSelected = false
                        txtOptions.isSelected = false
                        imgTrueFalse.isVisible = false
                        llRoot.setBackgroundResource(R.drawable.option_bg_normal)
                    }
                    if (answer == item.options) {
                        llRoot.setBackgroundResource(R.drawable.option_bg_true)
                        imgTrueFalse.setImageResource(R.drawable.true_check)
                        txtAbc.isSelected = true
                        txtOptions.isSelected = true
                        imgTrueFalse.isVisible = item.isselected == true
                    }
                } else {
                    llRoot.setBackgroundResource(R.drawable.option_bg_normal)
                }
                llRoot.setOnClickListener {
                    if (submittedAnswer == null) {
                        item.isselected = true
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }


    fun setDataList(list: ArrayList<String>) {
        dataList.clear()
        for (item in list.withIndex()) {
            dataList.add(OptionsModel(index = getAbc()[item.index], options = item.value))
        }
        notifyDataSetChanged()
    }

    fun setAnswer(str: String?) {
        answer = str
    }
}

fun getAbc(): ArrayList<String> {
    val list: ArrayList<String> = ArrayList()
    list.add("A")
    list.add("B")
    list.add("C")
    list.add("D")
    return list
}