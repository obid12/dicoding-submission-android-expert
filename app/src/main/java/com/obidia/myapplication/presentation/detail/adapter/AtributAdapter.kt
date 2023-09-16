package com.obidia.myapplication.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.obidia.myapplication.databinding.ItemAtributeBinding
import com.obidia.myapplication.presentation.detail.viewholder.AtributViewHolder
import com.obidia.myapplication.presentation.detail.DetailModel.AtributModel

class AtributAdapter : ListAdapter<AtributModel, ViewHolder>(DiffCallBack) {
  object DiffCallBack : DiffUtil.ItemCallback<AtributModel>() {

    override fun areItemsTheSame(oldItem: AtributModel, newItem: AtributModel): Boolean {
      return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: AtributModel, newItem: AtributModel): Boolean {
      return oldItem.hashCode() == newItem.hashCode()
    }
  }


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = ItemAtributeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return AtributViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val data = getItem(position) as AtributModel
    (holder as AtributViewHolder).bind(data)
  }
}