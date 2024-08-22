package com.osamaalek.kiosklauncher.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.osamaalek.kiosklauncher.ui.AuthActivity

object KioskUtil {

    fun startKioskMode(activity: AppCompatActivity) {
        if (!isKioskModeEnabled(activity)) {
            lockDevice(activity)
            val intent = Intent(activity, AuthActivity::class.java)
            intent.putExtra("EXIT_KIOSK_MODE", false)
            activity.startActivity(intent)
        }
    }

    fun stopKioskMode(activity: AppCompatActivity) {
        if (isKioskModeEnabled(activity)) {
            unlockDevice(activity)
            val intent = Intent(activity, AuthActivity::class.java)
            intent.putExtra("EXIT_KIOSK_MODE", true)
            activity.startActivity(intent)
        }
    }

    private fun isKioskModeEnabled(activity: Activity): Boolean {
        val dpm = activity.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        return dpm.isLockTaskPermitted(activity.packageName)
    }

    @SuppressLint("NewApi")
    private fun lockDevice(activity: Activity) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.startLockTask()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("NewApi")
    private fun unlockDevice(activity: Activity) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.stopLockTask()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
