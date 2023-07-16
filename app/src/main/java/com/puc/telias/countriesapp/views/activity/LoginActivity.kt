package com.puc.telias.countriesapp.views.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.puc.telias.countriesapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}