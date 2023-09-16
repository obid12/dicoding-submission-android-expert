package com.obidia.myapplication.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.obidia.myapplication.databinding.ItemAtributeBinding
import com.obidia.myapplication.presentation.detail.DetailModel.AttributeModel
import com.obidia.myapplication.presentation.detail.viewholder.AtributViewHolder

class AttributeAdapter : ListAdapter<AttributeModel, ViewHolder>(DiffCallBack) {
  object DiffCallBack : DiffUtil.ItemCallback<AttributeModel>() {

    override fun areItemsTheSame(oldItem: AttributeModel, newItem: AttributeModel): Boolean {
      return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: AttributeModel, newItem: AttributeModel): Boolean {
      return oldItem.hashCode() == newItem.hashCode()
    }
  }


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = ItemAtributeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return AtributViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val data = getItem(position) as AttributeModel
    (holder as AtributViewHolder).bind(data)
  }
}