package com.puc.telias.countriesapp.views.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.puc.telias.countriesapp.R
import com.puc.telias.countriesapp.database.AppDatabase
import com.puc.telias.countriesapp.databinding.FragmentLoginBinding
import com.puc.telias.countriesapp.repository.UsersRepository
import com.puc.telias.countriesapp.views.MainActivity
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    val TAG = "LoginFragment"

    private lateinit var activityContext: Context

    private val repository by lazy {
        UsersRepository(
            AppDatabase.getConnection(activityContext).usersDao(),
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context
    }

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureInterface()
    }

    private fun showError(message: String) {
        val contextView = binding.mainView
        Snackbar.make(
            contextView,
            message,
            Snackbar.LENGTH_SHORT
        )
            .setBackgroundTint(resources.getColor(R.color.color_error_background))
            .setTextColor(resources.getColor(R.color.color_error_text))
            .show()
    }

    private fun configureInterface() {
        configureLoginButton()
        configureRegisterButton()
    }

    private fun configureRegisterButton() {
        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun configureLoginButton() {
        val intent = Intent(activityContext, MainActivity::class.java)
        binding.loginButton.setOnClickListener {

            lifecycleScope.launch {
                val user = repository.login(
                    binding.userName.text.toString(),
                    binding.password.text.toString()
                )
                if (user != null) {
                    val sharedPrefs = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPrefs.edit()
                    editor.apply {
                        putString("USER_KEY", user.userName)
                    }.apply()
                    startActivity(intent)
                } else {
                    showError("Usuário ou senha inválido")
                }
            }
        }
    }

}