package com.puc.telias.countriesapp.views.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.puc.telias.countriesapp.database.AppDatabase
import com.puc.telias.countriesapp.repository.UsersRepository


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var activityContext: Context
    private lateinit var auth: FirebaseAuth

    val TAG = "RegisterFragment"

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

        auth = Firebase.auth
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
            val password1 = binding.password1.text.toString()
            val password2 = binding.password2.text.toString()
            val email = binding.userEmail.text.toString()
            val name = binding.name.text.toString()

            if (
                name.isBlank() ||
                email.isBlank() ||
                password1.isBlank()
            ) {
                showError("Todas as informações devem ser preenchidas")
            } else if (
                !password1.equals(password2)
            ) {
                showError("As senhas não são iguais")
            } else {
                auth.createUserWithEmailAndPassword(email, password1)
                    .addOnSuccessListener { _ ->
                        auth.signOut() //Faz logoff ao cadastrar
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    }
                    .addOnFailureListener { exception ->
                        val mensagem: String = when (exception){
                            is FirebaseAuthWeakPasswordException -> "Senha Fraca"
                            is FirebaseAuthInvalidCredentialsException -> "Formato de email inválido"
                            is FirebaseAuthUserCollisionException -> "Email já cadastrado"
                            else -> "Erro ao cadastrar"
                        }

                        showError(mensagem)
                        Log.e(TAG, "signUp Failure: ", exception)
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