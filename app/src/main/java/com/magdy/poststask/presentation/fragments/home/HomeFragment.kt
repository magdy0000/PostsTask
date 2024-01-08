package com.magdy.poststask.presentation.fragments.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.magdy.poststask.base.BaseFragment
import com.magdy.poststask.databinding.FragmentHomeBinding
import com.magdy.poststask.presentation.adapters.AdapterRecycler
import com.magdy.poststask.presentation.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var adapterPosts: AdapterRecycler

    override fun FragmentHomeBinding.initializeUI() {
        adapterPosts = AdapterRecycler {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it)
            )
        }
        recyclerPosts.adapter = adapterPosts
    }

    override fun setupObservers() {
        lifecycleScope.launch {
            launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    homeViewModel.errorChannel.collect {
                        it?.let { showToast(it) }
                    }
                }
            }
            launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    homeViewModel.postsStateFlow.collect {
                        adapterPosts.setData(it)
                    }
                }
            }
            launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    homeViewModel.loadingStateFlow.collect {
                        if (it) showLoading()
                        else hideLoading()
                    }
                }
            }
        }
    }
}