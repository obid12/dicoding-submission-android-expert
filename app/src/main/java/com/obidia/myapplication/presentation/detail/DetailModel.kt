package com.obidia.myapplication.presentation.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

class DetailModel @Inject constructor() {

  var list: ArrayList<AtributModel> = arrayListOf()

  data class AtributModel(
    val dataText: String,
    val text: String
  )

  fun transform(repository: String, followers: String, following: String): ArrayList<AtributModel> {
    list.run {
      clear()
      add(AtributModel(repository, "Repository"))
      add(AtributModel(followers, "Followers"))
      add(AtributModel(following, "Following"))
    }

    return list
  }

  enum class TabDetail(val type: Int) {
    FOLLOWERS(0),
    FOLLOWING(1)
  }

  @Parcelize
  data class TabDetailModel(
    val type: Int,
    val nameTab: String
  ) : Parcelable

  fun getList(list: ArrayList<TabDetailModel>): ArrayList<TabDetailModel> {
    list.clear()
    list.add(TabDetailModel(TabDetail.FOLLOWERS.type, "Followers"))
    list.add(TabDetailModel(TabDetail.FOLLOWING.type, "Followings"))
    return list
  }
}