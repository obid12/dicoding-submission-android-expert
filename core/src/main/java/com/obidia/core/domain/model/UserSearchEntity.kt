package com.obidia.core.domain.model

data class UserSearchEntity(
  val items: MutableList<ItemUserSearchEntity>,
)

data class ItemUserSearchEntity(
  val avatarUrl: String,
  val login: String
)
