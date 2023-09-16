package com.obidia.myapplication.util

import android.content.res.Resources
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load
import coil.request.ImageRequest
import com.obidia.myapplication.R
fun asPixels(
  resources: Resources,
  size: Int,
): Int {
  val scale = resources.displayMetrics.density
  return (size * scale + 0.5f).toInt()
}
@BindingAdapter("imagesUrl")
fun getImageUser(iv: ImageView, imgUrl: String?) {
  imgUrl.let {
    val imageUrl = ImageRequest.Builder(iv.context)
      .data("${it?.toUri()}")
      .allowHardware(false)
      .build()
    iv.load("${imageUrl.data}") {
      placeholder(R.drawable.loading_animation)
      this.error(R.drawable.ic_broken_image)
    }
  }
}