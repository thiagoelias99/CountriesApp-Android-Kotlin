package com.puc.telias.countriesapp.views

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.puc.telias.countriesapp.R
import com.puc.telias.countriesapp.database.AppDatabase
import com.puc.telias.countriesapp.databinding.ActivityLoginBinding
import com.puc.telias.countriesapp.models.User
import com.puc.telias.countriesapp.repository.UsersRepository
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val repository by lazy {
        UsersRepository(
            AppDatabase.getConnection(this).usersDao(),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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

    private fun configureRegisterButton(){
        binding.registerButton.setOnClickListener {
            Intent(this, RegisterActivity::class.java).run {
                startActivity(this)
            }
        }
    }

    private fun configureLoginButton() {
        val intent = Intent(this, MainActivity::class.java)
        binding.loginButton.setOnClickListener {

            lifecycleScope.launch {
                val user = repository.login(
                    binding.userName.text.toString(),
                    binding.password.text.toString()
                )
                if (user != null) {
                    val sharedPrefs = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPrefs.edit()
                    editor.apply {
                        putString("USER_KEY", user.userName)
                    }.apply()
                    startActivity(intent)
                } else{
                    showError("Usuário ou senha inválido")
                }
            }
        }
    }
}