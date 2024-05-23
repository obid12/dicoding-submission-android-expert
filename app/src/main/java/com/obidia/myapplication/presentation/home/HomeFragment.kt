package com.obidia.myapplication.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.obidia.core.domain.model.UserSearchEntity
import com.obidia.core.utils.Resource
import com.obidia.core.utils.error
import com.obidia.core.utils.success
import com.obidia.myapplication.MainActivity
import com.obidia.myapplication.R
import com.obidia.myapplication.R.layout
import com.obidia.myapplication.R.string
import com.obidia.myapplication.databinding.FragmentHomeBinding
import com.obidia.myapplication.util.ShimmerAdapter
import com.obidia.myapplication.util.asPixels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

  private var _binding: FragmentHomeBinding? = null
  private val binding get() = _binding!!

  private var adapter: UserSearchAdapter? = null

  private var adapterShimmer: ShimmerAdapter? = null

  private val viewModel: HomeViewModel by viewModels()

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
    adapter = null
    adapterShimmer = null
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
    setupAdapter()
    setupShimmerAdapter()
    observe()
    loadData()
    setupToolBar()
    showButtomNavigation()

    return binding.root
  }

  private fun showButtomNavigation(){
    (activity as MainActivity).setBottomNavigationVisibility(true)
  }


  private fun setupShimmerAdapter() {
    adapterShimmer = ShimmerAdapter(layout.shimmer_item_search_user, 10)
    binding.rvUserShimmer.let {
      it.layoutManager = LinearLayoutManager(requireContext())
      it.adapter =   adapterShimmer
    }
  }

  private fun setupToolBar() {
    binding.toolbarView.let {
      it.imgToolbar.setImageResource(R.drawable.ic_github_mark_white)
      it.toolbarMenu.run {
        setImageResource(R.drawable.ic_search)
        setOnClickListener {
          findNavController().navigate(R.id.action_homeFragment_to_searchUserFragment)
        }
      }
      it.titleToolbar.text = getString(string.toolbar_title_home)
    }
  }

  private fun loadData() {
    viewModel.getAllUserSearch()
  }

  private fun observe() {
    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.dataUserSearch.collect { state ->
          when (state) {
            is Resource.Loading -> {
              swipeRefresh()
              onLoadingUserSearch()
            }

            is Resource.Success -> {
              state.success { data ->
                onSuccessGetUserSearch(data)
              }
            }

            is Resource.Error -> {
              state.error {
              }
            }

            else -> {}
          }
        }
      }
    }
  }

  private fun swipeRefresh() {
    binding.swipeRefresh.let {
      val blue = ContextCompat.getColor(requireContext(), R.color.blue3)
      val yellow = ContextCompat.getColor(requireContext(), R.color.yellow)

      it.isRefreshing = true
      it.setOnRefreshListener(this::onRefresh)
      it.setColorSchemeColors(blue, yellow)
      it.setProgressViewOffset(false, 0, asPixels(resources, 76))
    }
  }


  private fun onRefresh() {
    viewModel.getAllUserSearch()
    setupAdapter()
  }

  private fun onLoadingUserSearch() {
    showContent(SHIMMER_VIEW_INDEX)
  }

  private fun onSuccessGetUserSearch(data: UserSearchEntity?) {
    showContent(CONTENT_VIEW_INDEX)
    adapter?.submitList(data?.items)
  }

  private fun setupAdapter() {
    adapter = UserSearchAdapter(UserSearchAdapter.OnclickListener {
      findNavController().navigate(
        HomeFragmentDirections.actionHomeFragmentToDetailFragment(
          userName = it.login,
          isFromBottomSheet = false,
          avatarUrl = it.avatarUrl
        )
      )

    })
    binding.rvUser.let {
      it.adapter = adapter
      it.layoutManager = LinearLayoutManager(requireContext())
    }
  }

  private fun showContent(index: Int) {
    binding.animator.displayedChild = index
  }

  companion object {
    private const val CONTENT_VIEW_INDEX = 1
    private const val SHIMMER_VIEW_INDEX = 0
  }


}