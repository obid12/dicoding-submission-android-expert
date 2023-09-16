package com.obidia.favorite

import androidx.recyclerview.widget.RecyclerView
import com.obidia.core.domain.model.UserModel
import com.obidia.myapplication.databinding.ItemGithubUserBinding
import com.obidia.myapplication.util.getImageUser

class UserFavoriteViewHolder(private val binding: ItemGithubUserBinding) :
  RecyclerView.ViewHolder(binding.root) {
  fun bind(data: UserModel) {
    binding.run {
      getImageUser(binding.ivUser, data.avatarUrl)
      tvUsername.text = data.login
    }
  }
}