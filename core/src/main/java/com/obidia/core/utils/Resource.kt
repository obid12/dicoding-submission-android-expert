package com.obidia.core.utils

import com.obidia.core.utils.Resource.Error
import com.obidia.core.utils.Resource.Loading
import com.obidia.core.utils.Resource.Success

sealed class Resource<out R> {
  data class Success<out T>(val data: T) : Resource<T>()
  data class Error(val throwable: Throwable) : Resource<Nothing>()
  object Loading : Resource<Nothing>()
}

inline infix fun <T> Resource<T>.loading(predicate: () -> Unit): Resource<T> {
  if (this is Loading) {
    predicate.invoke()
  }
  return this
}

inline infix fun <T> Resource<T>.success(predicate: (data: T) -> Unit): Resource<T> {
  if (this is Success && this.data != null) {
    predicate.invoke(data)
  }
  return this
}

inline infix fun <T> Resource<T>.error(predicate: (data: Throwable) -> Unit) {
  if (this is Error) {
    predicate.invoke(this.throwable)
  }
}
