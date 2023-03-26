package com.example.github.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.github.R
import com.example.github.data.models.data.LocalStorage
import com.example.github.databinding.FragmentUsernamesBinding
import com.example.github.presentation.SearchByUserNameViewModel
import com.example.github.ui.adapter.SearchByUserAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment : Fragment(R.layout.fragment_usernames) {
    lateinit var binding: FragmentUsernamesBinding
    private val viewModel by viewModel<SearchByUserNameViewModel>()
    private var adapter = SearchByUserAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUsernamesBinding.bind(view)

        initObServers()

        binding.apply {
            lifecycleScope.launchWhenResumed {
                viewModel.searchByUserName(LocalStorage().search)
            }
            recyclerUser.adapter = adapter
        }

    }

    private fun initObServers() {
        viewModel.activeSearchFlow.onEach {
            adapter.models = it.items
        }.launchIn(lifecycleScope)
    }
}