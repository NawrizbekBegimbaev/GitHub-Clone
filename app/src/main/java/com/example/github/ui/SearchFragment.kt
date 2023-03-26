package com.example.github.ui

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)


        /**val input = binding.etSearch.text.toString()
        if (input.isNotEmpty()) {
        lifecycleScope.launchWhenResumed {
        nameViewModel.searchByUserName(input)
        repoViewModel.searchByRepo(input)
        binding.repoTotal.text = totalrepo.toString()
        binding.userTotal.text = totalname.toString()
        }
        }else{
        Snackbar.make(requireView(),"Toltir",Snackbar.LENGTH_LONG)
        }**/
        binding.etSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.etSearch.clearFocus()
                binding.repo.text = String.format(getString(R.string.repositories_with),query)
                binding.user.text = String.format(getString(R.string.users_with),query)
                LocalStorage().search = query.toString()

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                binding.repo.text = String.format(getString(R.string.repositories_with),newText)
                binding.user.text = String.format(getString(R.string.users_with),newText)
                LocalStorage().search = newText.toString()

                return false
            }

        })

        binding.repo.setOnClickListener {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToReposFragment()
            )
        }
        binding.user.setOnClickListener {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToUserFragment()
            )
        }

        binding.back.setOnClickListener {
            val navController = Navigation.findNavController(requireActivity(),R.id.main_fragment)
            navController.navigate(SearchFragmentDirections.actionSearchFragmentToMainFragment())
        }
    }
}