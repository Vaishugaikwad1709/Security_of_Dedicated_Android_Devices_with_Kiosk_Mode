package com.osamaalek.kiosklauncher.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.osamaalek.kiosklauncher.R
import com.osamaalek.kiosklauncher.util.KioskUtil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check if the user is authenticated before proceeding
        if (FirebaseAuth.getInstance().currentUser == null) {
            val authIntent = Intent(this, AuthActivity::class.java)
            startActivity(authIntent)
            finish() // Prevent returning to MainActivity without authentication
        } else {
            startKioskModeIfAuthenticated()
        }
    }

    override fun onResume() {
        super.onResume()
        startKioskModeIfAuthenticated()
    }

    private fun startKioskModeIfAuthenticated() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            KioskUtil.startKioskMode(this)
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) is AppsListFragment) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, HomeFragment()).commit()
        } else {
            super.onBackPressed()
        }
    }
}
