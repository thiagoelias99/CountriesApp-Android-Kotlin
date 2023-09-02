package com.puc.telias.countriesapp.views.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.puc.telias.countriesapp.R
import com.puc.telias.countriesapp.database.AppDatabase
import com.puc.telias.countriesapp.databinding.FragmentLoginBinding
import com.puc.telias.countriesapp.repository.UsersRepository
import com.puc.telias.countriesapp.views.activity.MainActivity
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    val TAG = "LoginFragment"

    private lateinit var activityContext: Context
    private lateinit var auth: FirebaseAuth

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

        auth = Firebase.auth
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
            val password = binding.password.text.toString()
            val email = binding.userEmail.text.toString()

            if (password.isBlank() or email.isBlank()){
                showError("Usuário e senha devem estar preenchidos")
            } else{
                auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        startActivity(intent)
                    }
                    .addOnFailureListener {exception ->
                        val mensagem: String = when (exception){
                            is FirebaseAuthInvalidUserException -> "Usuário não existe"
                            is FirebaseAuthInvalidCredentialsException -> "Usúario ou senha inválidos"
                            else -> "Erro ao registrar"
                        }

                        showError(mensagem) //Faz logoff ao cadastrar
                    }
            }
        }
    }

}