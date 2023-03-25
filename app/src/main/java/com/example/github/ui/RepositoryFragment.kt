package com.example.github.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.github.R
import com.example.github.data.models.GetUserRepo
import com.example.github.databinding.FragmentRepoBinding
import com.example.github.presentation.GetUserRepositoriesViewModel
import com.example.github.ui.adapter.RepoAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoryFragment: Fragment(R.layout.fragment_repo) {
    lateinit var binding: FragmentRepoBinding
    private val viewModel by viewModel<GetUserRepositoriesViewModel>()
    private val adapter = RepoAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepoBinding.bind(view)


        initObServers()

        binding.apply {
            recyclerRepo.adapter = adapter
            recyclerRepo.addItemDecoration(
                DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL)
            )
            lifecycleScope.launchWhenResumed {
                viewModel.getUserRepo()
            }
        }
    }

    private fun initObServers() {
        viewModel.activeInfoFlow.onEach {
            adapter.submitList(it)
        }.launchIn(lifecycleScope)
    }


}