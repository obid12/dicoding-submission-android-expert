package com.obidia.core.utils

fun Int?.replaceIfNull(replacementValue: Int = 0): Int {
  if (this == null)
    return replacementValue
  return this
}

fun String?.replaceIfNull(replacementValue: String = ""): String {
  if (this == null)
    return replacementValue
  return this
}

fun <T> ArrayList<T>?.orEmpty(): ArrayList<T> = this ?: arrayListOf()

fun Boolean?.replaceIfNull(replacementValue: Boolean = false): Boolean {
  if (this == null) return replacementValue
  return this
}

//fun Double?.replaceIfNull(replacementValue: Double = 0.0): Double {
//  if (this == null)
//    return replacementValue
//  return this
//}
//
//fun Long?.replaceIfNull(replacementValue: Long = 0L): Long {
//  if (this == null)
//    return replacementValue
//  return this
//}