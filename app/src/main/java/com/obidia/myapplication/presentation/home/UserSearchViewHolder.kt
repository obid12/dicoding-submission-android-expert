package com.obidia.myapplication.presentation.home

import androidx.recyclerview.widget.RecyclerView
import com.obidia.core.domain.model.ItemUserSearchEntity
import com.obidia.myapplication.databinding.ItemGithubUserBinding
import com.obidia.myapplication.util.getImageUser

class UserSearchViewHolder(private val binding: ItemGithubUserBinding) :
  RecyclerView.ViewHolder(binding.root) {
  fun bind(data: ItemUserSearchEntity) {
    binding.run {
      getImageUser(binding.ivUser, data.avatarUrl)
      tvUsername.text = data.login
    }
  }
}