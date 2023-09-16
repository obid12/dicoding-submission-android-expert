package com.obidia.core.data.remote.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.obidia.core.domain.model.UserDetailEntity
import com.obidia.core.utils.replaceIfNull

data class UserDetailResponse(
  @SerializedName("login")
  @Expose
  val login: String? = "",
  @SerializedName("id")
  @Expose
  val id: Int? = 0,
  @SerializedName("node_id")
  @Expose
  val nodeId: String? = "",
  @SerializedName("avatar_url")
  @Expose
  val avatarUrl: String? = "",
  @SerializedName("gravatar_id")
  @Expose
  val gravatarId: String? = "",
  @SerializedName("type")
  @Expose
  val type: String? = "",
  @SerializedName("site_admin")
  @Expose
  val siteAdmin: Boolean? = true,
  @SerializedName("name")
  @Expose
  val name: String? = "",
  @SerializedName("company")
  @Expose
  val company: String? = "",
  @SerializedName("blog")
  @Expose
  val blog: String? = "",
  @SerializedName("location")
  @Expose
  val location: String? = "",
  @SerializedName("email")
  @Expose
  val email: String? = "",
  @SerializedName("hireable")
  @Expose
  val hireable: String? = "",
  @SerializedName("bio")
  @Expose
  val bio: String? = "",
  @SerializedName("twitter_username")
  @Expose
  val twitterUsername: String? = "",
  @SerializedName("public_repos")
  @Expose
  val publicRepos: Int? = 0,
  @SerializedName("public_gists")
  @Expose
  val publicGists: Int? = 0,
  @SerializedName("followers")
  @Expose
  val followers: Int? = 0,
  @SerializedName("following")
  @Expose
  val following: Int? = 0,
  @SerializedName("created_at")
  @Expose
  val createdAt: String? = "",
  @SerializedName("updated_at")
  @Expose
  val updatedAt: String? = ""
) {
  companion object {
    fun transform(data: UserDetailResponse) = UserDetailEntity(
      data.login.replaceIfNull(),
      data.name.replaceIfNull(),
      data.location.replaceIfNull(),
      data.publicRepos.replaceIfNull(),
      data.company.replaceIfNull(),
      data.followers.replaceIfNull(),
      data.following.replaceIfNull(),
      avatar = 0,
      data.avatarUrl.replaceIfNull()
    )
  }
}