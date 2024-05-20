package com.obidia.myapplication.presentation.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import com.obidia.core.domain.model.UserSearchEntity
import com.obidia.core.utils.Resource
import com.obidia.core.utils.error
import com.obidia.core.utils.success
import com.obidia.myapplication.MainActivity
import com.obidia.myapplication.R
import com.obidia.myapplication.R.layout
import com.obidia.myapplication.databinding.FragmentSearchUserBinding
import com.obidia.myapplication.presentation.home.HomeViewModel
import com.obidia.myapplication.presentation.home.UserSearchAdapter
import com.obidia.myapplication.util.ShimmerAdapter
import com.obidia.myapplication.util.asPixels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchUserFragment : Fragment() {
  private lateinit var binding: FragmentSearchUserBinding
  private var adapter: UserSearchAdapter? = null
  private val viewModel: HomeViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    // Inflate the layout for this fragment
    binding = FragmentSearchUserBinding.inflate(layoutInflater, container, false)
    setupAdapter()
    setupShimmerAdapter()
    observe()
    setupToolBar()
    setupSearch()
    hideBottomNavigation()

    return binding.root
  }

  private fun hideBottomNavigation() {
    (activity as MainActivity).setBottomNavigationVisibility(false)
  }

  private fun setupSearch() {
    binding.run {
      tilSearch.editText?.textChangedListener(iconDelete)
      iconDelete.setOnClickListener {
        onIconDeleteClickListener(tilSearch)
      }
      etSearch.setOnEditorActionListener { v, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
          v.run {
            clearFocus()
            hideKeyboard()
          }
          viewModel.getAllUserSearch(tilSearch.editText?.text.toString())
          binding.swipeRefresh.isVisible = true
        }
        false
      }
    }
  }

  private fun setupShimmerAdapter() {
    val adapter = ShimmerAdapter(layout.shimmer_item_search_user, 10)
    binding.rvUserShimmer.let {
      it.layoutManager = LinearLayoutManager(requireContext())
      it.adapter = adapter
    }
  }

  private fun setupToolBar() {
    binding.iconBack.setOnClickListener {
      findNavController().navigateUp()
    }
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
          }
        }
      }
    }
  }

  private fun swipeRefresh() {
    binding.swipeRefresh.let {
      val blue = ContextCompat.getColor(requireContext(), R.color.blue3)
      val yellow = ContextCompat.getColor(requireContext(), R.color.yellow)

      it.isRefreshing = binding.swipeRefresh.isVisible
      it.setOnRefreshListener(this::onRefresh)
      it.setColorSchemeColors(blue, yellow)
      it.setProgressViewOffset(false, 0, asPixels(resources, 76))
    }
  }


  private fun onRefresh() {
    viewModel.getAllUserSearch(binding.tilSearch.editText?.text.toString())
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
        SearchUserFragmentDirections.actionSearchUserFragmentToDetailFragment(userName = it.login, isFromBottomSheet = false, avatarUrl = it.avatarUrl)
      )

    })
    binding.rvUser.let {
      it.adapter = adapter
      it.layoutManager = LinearLayoutManager(requireContext())
    }
  }


   private fun showContent(index: Int)  {
    binding.animator.displayedChild = index
  }

  private fun EditText.textChangedListener(
    iconDelete: ImageView,
  ) {
    addTextChangedListener(
      object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
          iconDelete.isVisible = s?.isNotEmpty() == true
        }

      }
    )
  }

  private fun onIconDeleteClickListener(textInputLayout: TextInputLayout) {
    binding.swipeRefresh.isGone = true
    textInputLayout.run {
      editText?.setText("")
      requestFocus()
    }
  }

  private fun View.hideKeyboard() {
    try {
      val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
      imm.hideSoftInputFromWindow(windowToken, 0)
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }


  companion object {
    private const val CONTENT_VIEW_INDEX = 1
    private const val SHIMMER_VIEW_INDEX = 0
  }
}