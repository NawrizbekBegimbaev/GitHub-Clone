package com.example.github.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.github.R
import com.example.github.data.models.data.LocalStorage
import com.example.github.databinding.FragmentSearchBinding
import com.example.github.presentation.SearchByUserNameViewModel
import com.example.github.presentation.SearchByUserRepoViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(R.layout.fragment_search) {
    lateinit var binding: FragmentSearchBinding
    private val nameViewModel by viewModel<SearchByUserNameViewModel>()
    private val repoViewModel by viewModel<SearchByUserRepoViewModel>()
    var totalname = 0
    var totalrepo = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        initObServers()

        binding.etSearch.setOnClickListener{
            val input = binding.etSearch.text.toString()
            if (input.isNotEmpty()) {
                lifecycleScope.launchWhenResumed {
                    nameViewModel.searchByUserName(input)
                    repoViewModel.searchByRepo(input)
                    binding.repoTotal.text = totalrepo.toString()
                    binding.accTotal.text = totalname.toString()
                }
            }else{
                Snackbar.make(requireView(),"Toltir",Snackbar.LENGTH_LONG)
            }
        }
        binding.back.setOnClickListener {
            val navController = Navigation.findNavController(requireActivity(),R.id.main_fragment)
            navController.navigate(SearchFragmentDirections.actionSearchFragmentToMainFragment())
        }
    }

    private fun initObServers() {
        nameViewModel.activeSearchFlow.onEach {
            totalname = it.total_count
        }.launchIn(lifecycleScope)
        repoViewModel.activeSearchFlow.onEach {
            totalrepo = it.total_count
        }.launchIn(lifecycleScope)
    }
}