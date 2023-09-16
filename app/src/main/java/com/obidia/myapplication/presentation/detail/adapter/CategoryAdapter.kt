package com.obidia.myapplication.presentation.detail.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class CategoryAdapter(manager: FragmentManager, lifecycle: Lifecycle) :
  FragmentStateAdapter(manager, lifecycle) {
  private val mFragmentList = ArrayList<Fragment>()
  private val mFragmentTitleList = ArrayList<String>()


  fun clearFragment() {
    mFragmentList.clear()
    mFragmentTitleList.clear()
  }

  fun addFragment(fragment: Fragment, title: String) {
    mFragmentList.add(fragment)
    mFragmentTitleList.add(title)
  }

  fun getTabName(position: Int): String {
    return mFragmentTitleList[position]
  }

  fun getFragmentAt(position: Int): Fragment {
    return mFragmentList[position]
  }

  override fun getItemCount(): Int {
    return mFragmentList.size
  }

  override fun createFragment(position: Int): Fragment {
    return mFragmentList[position]
  }

}