package com.example.github.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.github.R
import com.example.github.data.models.data.LocalStorage
import com.example.github.databinding.FragmentLoginBinding
import com.example.github.presentation.LoginViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModel<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

       if (LocalStorage().login == 1){
           findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
       }else{
           initObServers()

           lifecycleScope.launchWhenResumed {
               viewModel.login()
           }

           binding.login.setOnClickListener {
               val intent = Intent(
                   Intent.ACTION_VIEW, Uri.parse(
                       "https://github.com/login/oauth/authorize?client_id=8f3cf5f09bd0c93a0528&scope=repo"
                   )
               )
               startActivity(intent)
           }
       }
    }

    private fun initObServers() {
        viewModel.activeLoginFlow.onEach {
            LocalStorage().login = 1
            LocalStorage().token = it
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToMainFragment()
            )
        }.launchIn(lifecycleScope)
    }


    override fun onResume() {
        super.onResume()
        val uri: Uri? = requireActivity().intent?.data
        if (uri != null) {
            val code = uri.getQueryParameter("code")
            if (code != null) {
                LocalStorage().code = code
            } else if ((uri.getQueryParameter("error")) != null) {
                Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}