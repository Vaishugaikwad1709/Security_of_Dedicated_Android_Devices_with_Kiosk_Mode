package com.osamaalek.kiosklauncher.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.osamaalek.kiosklauncher.R
import com.osamaalek.kiosklauncher.util.KioskUtil

class AuthActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        auth = FirebaseAuth.getInstance()

        val user = auth.currentUser
        if (user != null) {
            onUserAuthenticated(user)
        } else {
            performAuthentication()
        }
    }

    private fun performAuthentication() {
        auth.signInWithEmailAndPassword("user@example.com", "password")
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    onUserAuthenticated(user)
                } else {
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun onUserAuthenticated(user: FirebaseUser?) {
        val exitKioskMode = intent.getBooleanExtra("EXIT_KIOSK_MODE", false)
        if (exitKioskMode) {
            KioskUtil.stopKioskMode(this)
        } else {
            val packageName = intent.getStringExtra("packageName")
            if (packageName != null) {
                val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
                startActivity(launchIntent)
            } else {
                KioskUtil.startKioskMode(this)
            }
        }
        finish()
    }
}
