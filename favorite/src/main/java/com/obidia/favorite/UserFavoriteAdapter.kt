package com.obidia.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.obidia.core.domain.model.UserModel
import com.obidia.myapplication.databinding.ItemGithubUserBinding

class UserFavoriteAdapter(private val onclickListener: OnclickListener) :
  ListAdapter<UserModel, ViewHolder>(
    DiffCallback
  ) {
  object DiffCallback : DiffUtil.ItemCallback<UserModel>() {
    override fun areItemsTheSame(
      oldItem: UserModel,
      newItem: UserModel
    ) =
      oldItem.hashCode() == newItem.hashCode()

    override fun areContentsTheSame(
      oldItem: UserModel,
      newItem: UserModel
    ) =
      oldItem.hashCode() == newItem.hashCode()
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val newsItem = getItem(position)
    val itemHolder = holder as UserFavoriteViewHolder
    newsItem?.let { itemHolder.bind(it) }
    holder.itemView.setOnClickListener {
      newsItem?.let { data -> onclickListener.onClick(data) }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = ItemGithubUserBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    )
    return UserFavoriteViewHolder(binding)
  }

  class OnclickListener(
    val clickListener: (entity: UserModel) ->
    Unit
  ) {
    fun onClick(entity: UserModel) = clickListener(entity)
  }
}