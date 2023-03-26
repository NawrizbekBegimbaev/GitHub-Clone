package com.example.github.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.github.R
import com.example.github.data.models.Itemss
import com.example.github.data.models.SearchByUserName.Items
import com.example.github.data.models.data.LocalStorage
import com.example.github.databinding.FragmentSearchrepositoriesBinding
import com.example.github.databinding.ItemSearchBinding
import com.example.github.presentation.SearchByUserRepoViewModel
import com.example.github.ui.adapter.SearchByRepoAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReposFragment:Fragment(R.layout.fragment_searchrepositories) {
    lateinit var binding: FragmentSearchrepositoriesBinding
    private val viewModel by viewModel<SearchByUserRepoViewModel>()
    private var adapter = SearchByRepoAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchrepositoriesBinding.bind(view)

        initObservers()

        binding.apply {
            lifecycleScope.launchWhenResumed {
                viewModel.searchByRepo(LocalStorage().search)
            }
            recyclerRepo.adapter = adapter
        }
    }

    private fun initObservers() {
        viewModel.activeSearchFlow.onEach {
            adapter.models = it.items
        }.launchIn(lifecycleScope)
    }
}