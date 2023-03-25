package com.example.github.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.github.R
import com.example.github.databinding.FragmentHomeBinding
import com.example.github.databinding.FragmentLoginBinding
import com.example.github.presentation.GetUserInfoViewModel
import com.example.github.presentation.GetUserRepositoriesViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment: Fragment(R.layout.fragment_home){

    private lateinit var binding: FragmentHomeBinding
    private val infoViewModel by viewModel<GetUserInfoViewModel>()
    private val repoViewModel by viewModel<GetUserRepositoriesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)


        binding.repo.setOnClickListener {
            val navController = Navigation.findNavController(requireActivity(),R.id.main_fragment)
            navController.navigate(MainFragmentDirections.actionMainFragmentToRepositoryFragment())
        }

        binding.search.setOnClickListener{
            val navController = Navigation.findNavController(requireActivity(),R.id.main_fragment)
            navController.navigate(MainFragmentDirections.actionMainFragmentToSearchFragment())
        }
    }

}