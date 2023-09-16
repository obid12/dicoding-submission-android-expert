package com.obidia.myapplication.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.obidia.core.domain.model.UserDetailEntity
import com.obidia.core.domain.model.UserModel
import com.obidia.core.utils.Resource
import com.obidia.core.utils.error
import com.obidia.core.utils.replaceIfNull
import com.obidia.core.utils.success
import com.obidia.myapplication.MainActivity
import com.obidia.myapplication.R
import com.obidia.myapplication.R.layout
import com.obidia.myapplication.R.string
import com.obidia.myapplication.databinding.FragmentDetailBinding
import com.obidia.myapplication.presentation.detail.DetailModel.TabDetailModel
import com.obidia.myapplication.presentation.detail.adapter.AtributAdapter
import com.obidia.myapplication.presentation.detail.adapter.CategoryAdapter
import com.obidia.myapplication.presentation.detail.follow.FollowFragment
import com.obidia.myapplication.util.ShimmerAdapter
import com.obidia.myapplication.util.asPixels
import com.obidia.myapplication.util.getImageUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

  private lateinit var binding: FragmentDetailBinding
  lateinit var atributAdapter: AtributAdapter
  private val viewModel: DetailViewModel by viewModels()
  lateinit var categoryAdapter: CategoryAdapter
  private val args by navArgs<DetailFragmentArgs>()

  lateinit var model: DetailModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View {
    binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
    model = DetailModel()

    loadData()
    if (!args.isFromBottomSheet) loadDataFavorite()
    setupObserver()
    setupTabView()
    setupToolBar()
    setupShimmerAdapter()
    setBottomNavigation(args.isFromBottomSheet)

    return binding.root
  }

  private fun loadDataFavorite() {
    viewModel.cekSameData(args.userName.replaceIfNull())
  }


  private fun setupToolBar() {
    binding.toolbarView.let {
      it.titleToolbar.text =
        if (args.isFromBottomSheet) getString(string.toolbar_title_profile) else getString(string.toolbar_title_detail)
      it.imgToolbar.run {
        setImageResource(R.drawable.ic_back_white)
        setOnClickListener {
          findNavController().navigateUp()
        }
      }
    }
  }

  private val appBarOffsetChangedListener = AppBarLayout.OnOffsetChangedListener { _, offset ->
    binding.swipeRefresh.isEnabled = offset >= 0
  }

  private fun setBottomNavigation(visibility: Boolean) {
    (activity as MainActivity).setBottomNavigationVisibility(visibility)
  }

  private fun setupTabView() {
    val list: ArrayList<TabDetailModel> = arrayListOf()
    model.getList(list)
    categoryAdapter = CategoryAdapter(childFragmentManager, requireActivity().lifecycle).apply {
      clearFragment()
      list.forEachIndexed { index, model ->
        val fragment = FollowFragment.newInstance(index, args.userName.replaceIfNull())
        addFragment(fragment, model.nameTab)
      }
    }

    binding.viewPager.run {
      adapter = categoryAdapter
    }

    TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
      tab.text = categoryAdapter.getTabName(position)
    }.attach()

    binding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
      override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        binding.tabs.selectTab(binding.tabs.getTabAt(position))
      }
    })
  }

  private fun setupAdapterInfo(rv: RecyclerView, data: UserDetailEntity?) {
    rv.let {
      atributAdapter = AtributAdapter()
      atributAdapter.submitList(
        model.transform(
          data?.repository.toString(), data?.follower.toString(), data?.following.toString()
        )
      )
      it.adapter = atributAdapter
      it.layoutManager = FlexboxLayoutManager(requireContext()).apply {
        justifyContent = JustifyContent.SPACE_EVENLY
        alignItems = AlignItems.CENTER
      }
    }
  }

  private fun setupObserver() {
    getDataObserve()
    if (!args.isFromBottomSheet) favoriteObserve()
  }

  private fun favoriteObserve() {
    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.cekData.collect { isFavorite ->
          binding.ivFavorite.let { fav ->
            fav.setImageResource(if (isFavorite.replaceIfNull()) R.drawable.ic_favorite else R.drawable.ic_favorite_outline)
            fav.setOnClickListener {
              fav.onClick(isFavorite.replaceIfNull())
            }
          }
        }
      }
    }
  }

  private fun ImageView.onClick(data: Boolean) {
    if (data.replaceIfNull()) {
      setImageResource(R.drawable.ic_favorite_outline)
      viewModel.deleteUser(user = args.userName.replaceIfNull())
      viewModel.cekSameData(args.userName.replaceIfNull())
    } else {
      setImageResource(R.drawable.ic_favorite)
      viewModel.insertDataUser(
        UserModel(
          0,
          args.avatarUrl.replaceIfNull(),
          args.userName.replaceIfNull()
        )
      )
      viewModel.cekSameData(args.userName.replaceIfNull())
    }
  }

  private fun getDataObserve() {
    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.dataDetailUser.collect { state ->
          when (state) {
            is Resource.Loading -> {
              swipeRefresh()
              showContent(SHIMMER_VIEW_INDEX)
              delay(800)
            }

            is Resource.Success -> {
              state.success { data ->
                onSuccessGetDetailUser(data)
              }
            }

            is Resource.Error -> {
              state.error { data ->
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

  private fun setupShimmerAdapter() {
    val adapter = ShimmerAdapter(layout.shimmer_item_search_user, 10)
    binding.shimmerDetail.rvUserShimmer.let {
      it.layoutManager = LinearLayoutManager(requireContext())
      it.adapter = adapter
    }
  }

  private fun onRefresh() {
    viewModel.getDetailUser(args.userName.replaceIfNull())
  }

  private fun onSuccessGetDetailUser(data: UserDetailEntity?) {
    showContent(CONTENT_VIEW_INDEX)
    binding.let {
      it.appBar.addOnOffsetChangedListener(appBarOffsetChangedListener)
      it.ivFavorite.isInvisible = args.isFromBottomSheet
      getImageUser(it.cardImageProfile, data?.avatarUrl)
      it.tvUsername.text = data?.username
      setupAdapterInfo(it.rvAtribut, data)
    }
  }

  private fun loadData() {
    viewModel.getDetailUser(args.userName.replaceIfNull())
  }

  private fun showContent(index: Int) {
    binding.animator.displayedChild = index
  }

  companion object {
    private const val CONTENT_VIEW_INDEX = 1
    private const val SHIMMER_VIEW_INDEX = 0
  }

}