package com.obidia.myapplication.presentation.detail.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.obidia.myapplication.databinding.ItemAtributeBinding
import com.obidia.myapplication.presentation.detail.DetailModel.AttributeModel

class AtributViewHolder(var binding: ItemAtributeBinding) :
  ViewHolder(binding.root) {

  fun bind(data: AttributeModel) {
    binding.let {
      it.tvDataText.text = data.dataText
      it.tvText.text = data.text
    }
  }
}