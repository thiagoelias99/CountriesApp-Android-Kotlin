package com.puc.telias.countriesapp.views.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.puc.telias.countriesapp.R
import com.puc.telias.countriesapp.databinding.FragmentRegisterBinding
import com.puc.telias.countriesapp.models.User
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController
import com.puc.telias.countriesapp.database.AppDatabase
import com.puc.telias.countriesapp.repository.UsersRepository


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var activityContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context
    }

    private val repository by lazy {
        UsersRepository(
            AppDatabase.getConnection(activityContext).usersDao(),
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureInterface()
    }

    private fun configureInterface() {
        configureRegisterButton()
        configureCancelButton()
    }

    private fun configureCancelButton() {
        binding.cancelButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun configureRegisterButton() {
        binding.registerButton.setOnClickListener {
            if (
                binding.name.text.isNullOrBlank() ||
                binding.userName.text.isNullOrBlank() ||
                binding.password1.text.isNullOrBlank()
            ) {
                showError("Todas as informações devem ser preenchidas")
            } else if (
                !binding.password1.text.toString().equals(binding.password2.text.toString())
            ) {
                showError("As senhas não são iguais")
            } else {
                lifecycleScope.launch {
                    repository.save(
                        User(
                            name = binding.name.text.toString(),
                            userName = binding.userName.text.toString(),
                            password = binding.password1.text.toString()
                        )
                    )
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
            }
        }
    }

    private fun showError(message: String) {
        val contextView = binding.notificationView
        Snackbar.make(
            contextView,
            message,
            Snackbar.LENGTH_SHORT
        )
            .setBackgroundTint(resources.getColor(R.color.color_error_background))
            .setTextColor(resources.getColor(R.color.color_error_text))
            .show()
    }


}