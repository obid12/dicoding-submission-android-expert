package com.obidia.myapplication.presentation.detail.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.obidia.core.domain.model.ItemUserSearchEntity
import com.obidia.core.utils.Resource
import com.obidia.core.utils.error
import com.obidia.core.utils.replaceIfNull
import com.obidia.core.utils.success
import com.obidia.myapplication.databinding.FragmentFollowBinding
import com.obidia.myapplication.presentation.detail.DetailModel.TabDetail
import com.obidia.myapplication.presentation.home.UserSearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FollowFragment : Fragment() {

  private var _binding: FragmentFollowBinding? = null
  private val binding get() = _binding!!
  private val viewModel: FollowViewModel by viewModels()
  private var adapter: UserSearchAdapter? = null

  @Inject
  lateinit var model: FollowModel

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
    adapter = null
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentFollowBinding.inflate(layoutInflater, container, false)
    loadArguments()
    loadData()
    observe()
    setupAdapter()
    return binding.root
  }

  private fun observe() {
    if (model.type == TabDetail.FOLLOWING.type) {
      followingObserve()
      return
    }

    if (model.type == TabDetail.FOLLOWERS.type) {
      followersObserve()
      return
    }
  }

  private fun followersObserve() {
    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.dataFollower.collect { state ->
          when (state) {
            is Resource.Loading -> {
            }

            is Resource.Success -> {
              state.success { data ->
                onSuccessGetUserFollow(data)
              }
            }

            is Resource.Error -> {
              state.error { data ->

              }
            }
          }
        }
      }
    }
  }

  private fun onSuccessGetUserFollow(data: ArrayList<ItemUserSearchEntity>?) {
    adapter?.submitList(data)
  }

  private fun followingObserve() {
    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.dataFollowing.collect { state ->
          when (state) {
            is Resource.Loading -> {
            }

            is Resource.Success -> {
              state.success { data ->
                onSuccessGetUserFollow(data)
              }
            }

            is Resource.Error -> {
              state.error { data ->

              }
            }
          }
        }
      }
    }
  }

  private fun setupAdapter() {
    adapter = UserSearchAdapter(UserSearchAdapter.OnclickListener {
    })

    binding.rvFollow.let {
      it.adapter = adapter
      it.layoutManager = LinearLayoutManager(requireContext())
    }
  }

  private fun loadData() {
    if (model.type == TabDetail.FOLLOWING.type) {
      viewModel.getListFollowing(model.userName)
      return
    }

    if (model.type == TabDetail.FOLLOWERS.type) {
      viewModel.getListFollower(model.userName)
      return
    }
  }

  private fun loadArguments() {
    model.type = arguments?.getInt(ARGS_TYPE).replaceIfNull()
    model.userName = arguments?.getString(ARGS_USER_NAME).replaceIfNull()
  }

  companion object {
    private const val ARGS_TYPE = "TYPE"
    private const val ARGS_USER_NAME = "USERNAME"
    fun newInstance(
      type: Int,
      userName: String
    ): FollowFragment {
      return FollowFragment().apply {
        arguments = Bundle().apply {
          putInt(ARGS_TYPE, type)
          putString(ARGS_USER_NAME, userName)
        }
      }
    }
  }
}