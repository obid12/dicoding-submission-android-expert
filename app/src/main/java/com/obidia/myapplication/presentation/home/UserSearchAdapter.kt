package com.obidia.myapplication.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.obidia.core.domain.model.ItemUserSearchEntity
import com.obidia.myapplication.databinding.ItemGithubUserBinding

class UserSearchAdapter(private val onclickListener: OnclickListener) :
  ListAdapter<ItemUserSearchEntity, ViewHolder>(
    DiffCallback
  ) {
  object DiffCallback : DiffUtil.ItemCallback<ItemUserSearchEntity>() {
    override fun areItemsTheSame(
      oldItem: ItemUserSearchEntity,
      newItem: ItemUserSearchEntity
    ) =
      oldItem.hashCode() == newItem.hashCode()

    override fun areContentsTheSame(
      oldItem: ItemUserSearchEntity,
      newItem: ItemUserSearchEntity
    ) =
      oldItem.hashCode() == newItem.hashCode()
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val newsItem = getItem(position)
    val itemHolder = holder as UserSearchViewHolder
    newsItem?.let { itemHolder.bind(it) }
    holder.itemView.setOnClickListener {
      newsItem?.let { data -> onclickListener.onClick(data) }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = ItemGithubUserBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    )
    return UserSearchViewHolder(binding)
  }

  class OnclickListener(
    val clickListener: (entity: ItemUserSearchEntity) ->
    Unit
  ) {
    fun onClick(entity: ItemUserSearchEntity) = clickListener(entity)
  }
}