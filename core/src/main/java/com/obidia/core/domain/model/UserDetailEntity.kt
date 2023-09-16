package com.obidia.core.domain.model

data class UserDetailEntity(
    val username: String,
    val name: String,
    val location: String,
    val repository: Int,
    val company: String,
    val follower: Int,
    val following: Int,
    val avatar: Int,
    val avatarUrl:String
)