package com.puc.telias.countriesapp.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.puc.telias.countriesapp.R
import com.puc.telias.countriesapp.models.User

class LoginFragment : Fragment(R.layout.fragment_login) {

    val TAG = "LoginFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = requireArguments().getSerializable("USERD") as User

        Log.i(TAG, "onCreateFrag: $userId")
    }

}