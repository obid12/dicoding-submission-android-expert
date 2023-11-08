package com.obidia.favorite

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDeepLinkRequest.Builder
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.obidia.core.utils.Resource
import com.obidia.core.utils.loading
import com.obidia.core.utils.success
import com.obidia.favorite.UserFavoriteAdapter.OnclickListener
import com.obidia.favorite.databinding.FragmentFavoriteBinding
import com.obidia.favorite.di.DaggerFavoriteComponent
import com.obidia.myapplication.MainActivity
import com.obidia.myapplication.R
import com.obidia.myapplication.R.layout
import com.obidia.myapplication.R.string
import com.obidia.myapplication.presentation.di.DaggerDependencies
import com.obidia.myapplication.util.ShimmerAdapter
import com.obidia.myapplication.util.asPixels
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteFragment : Fragment() {


  private var _binding: FragmentFavoriteBinding? = null
  private val binding get() = _binding!!
  private var adapter: UserFavoriteAdapter? = null
  private var shimmerAdapter: ShimmerAdapter? = null

  @Inject
  lateinit var factory: ViewModelFactory

  private val viewModel: FavoriteViewModel by viewModels { factory }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
    adapter = null
    shimmerAdapter = null
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    DaggerFavoriteComponent.builder()
      .context(requireContext())
      .appDependencies(
        EntryPointAccessors.fromApplication(
          requireContext(),
          DaggerDependencies::class.java
        )
      )
      .build()
      .inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
    setupAdapter()
    observe()
    setupToolBar()
    setBottomNavigation()
    setupShimmerAdapter()
    return binding.root
  }

  private fun setupToolBar() {
    binding.toolbarView.let {
      it.titleToolbar.text = getString(string.toolbar_title_favorite)
      it.imgToolbar.run {
        setImageResource(R.drawable.ic_back)
        setOnClickListener {
          findNavController().navigateUp()
        }
      }
      it.toolbarMenu.isGone = true
    }
  }

  private fun setBottomNavigation() {
    (activity as MainActivity).setBottomNavigationVisibility(true)
  }

  private fun observe() {
    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.getFavoriteUser().catch {}.collect { state ->
          when (state) {
            is Resource.Loading -> {
              state.loading {
                swipeRefresh()
                showContent(SHIMMER_VIEW_INDEX)
                delay(700)
              }
            }

            is Resource.Success -> {
              state.success {
                showContent(CONTENT_VIEW_INDEX)
                adapter?.submitList(it)
              }
            }

            is Resource.Error -> {
            }

          }

        }
      }
    }
  }

  private fun onRefresh() {
    observe()
    setupAdapter()
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

  private fun setupAdapter() {
    gotoDetailUser()
    binding.rvUser.let {
      it.adapter = adapter
      it.layoutManager = LinearLayoutManager(requireContext())
    }
  }

  private fun gotoDetailUser() {
    adapter = UserFavoriteAdapter(OnclickListener {
      val request = Builder
        .fromUri(Uri.parse("android-app://com.obidia.app/detail_fragment/" + false + "?userName=" + it.login + "&avatarUrl=" + it.avatarUrl))
        .build()
      findNavController().navigate(request)
    })
  }

  private fun setupShimmerAdapter() {
    shimmerAdapter = ShimmerAdapter(layout.shimmer_item_search_user, 10)
    binding.rvUserShimmer.let {
      it.layoutManager = LinearLayoutManager(requireContext())
      it.adapter = shimmerAdapter
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