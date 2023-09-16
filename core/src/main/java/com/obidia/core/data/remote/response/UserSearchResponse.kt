package com.obidia.core.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.obidia.core.domain.model.ItemUserSearchEntity
import com.obidia.core.domain.model.UserSearchEntity
import com.obidia.core.utils.orEmpty
import com.obidia.core.utils.replaceIfNull

data class UserSearchResponse(
  @field:SerializedName("items")
  val items: ArrayList<ItemUserSearchResponse>?,
) {
  companion object {
    fun transform(data: UserSearchResponse) = UserSearchEntity(
      ItemUserSearchResponse.transform(data.items.orEmpty())
    )
  }
}

data class ItemUserSearchResponse(
  @SerializedName("avatar_url")
  @Expose
  val avatarUrl: String? = "",
  @SerializedName("login")
  @Expose
  val login: String? = ""
) {
  companion object {
    fun transform(data: ArrayList<ItemUserSearchResponse>) = ArrayList<ItemUserSearchEntity>(
      data.map {
        ItemUserSearchEntity(
          it.avatarUrl.replaceIfNull(),
          it.login.replaceIfNull()
        )
      }
    )
  }
}
