package com.example.github.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.github.R
import com.example.github.data.models.GetUserProfileInfoResponse
import com.example.github.data.models.GetUserRepo
import com.example.github.databinding.FragmentProfileBinding
import com.example.github.presentation.GetUserInfoViewModel
import com.example.github.presentation.GetUserRepositoriesViewModel
import com.example.recyclerview.RepoAdapter
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.reflect.Array.get

class ProfileFragment: Fragment(R.layout.fragment_profile) {
    lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModel<GetUserInfoViewModel>()
    private val repoviewModel by viewModel<GetUserRepositoriesViewModel>()
    private val adapter = RepoAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        initObServers()

        binding.apply {
            recyclerRepository.adapter = adapter
            recyclerRepository.addItemDecoration(
                DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL)
            )
            lifecycleScope.launchWhenResumed {
                repoviewModel.getUserRepo()
                viewModel.getUserInfo()
            }
        }

    }

    private fun initObServers() {
        viewModel.activeInfoFlow.onEach {
            binding.apply {
                totalRepo.text = it.public_repos.toString()
                star.text = it.public_gists.toString()
                Picasso.get().load("${it.avatar_url}").into(profileImage)
                profileName.text = it.login
                numFollow.text = it.followers.toString()
                numFollowing.text = it.following.toString()
            }

        }.launchIn(lifecycleScope)
        repoviewModel.activeInfoFlow.onEach {
            adapter.submitList(it)
        }.launchIn(lifecycleScope)
    }
}