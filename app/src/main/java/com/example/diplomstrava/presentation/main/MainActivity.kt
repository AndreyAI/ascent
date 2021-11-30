package com.example.diplomstrava.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.diplomstrava.R
import com.example.diplomstrava.databinding.ActivityMainBinding
import com.example.diplomstrava.presentation.addactivity.AddActivityFragment
import com.example.diplomstrava.presentation.person.LogoutDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ExitDialog.Exit {

    private val binding by viewBinding(ActivityMainBinding::bind, R.id.activityMain)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragment)
        if (navHost?.childFragmentManager?.fragments?.get(0) is AddActivityFragment)
            super.onBackPressed()
        else{
            navHost?.childFragmentManager?.let {
                ExitDialog()
                    .show(it, "Duration")
            }
        }
    }

    override fun exit() {
        finish()
    }
}