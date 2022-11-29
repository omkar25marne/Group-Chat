package com.omkarmarne.groupchat.utility

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.omkarmarne.groupchat.R
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    fun navigateFragment(
        supportFragmentManager: FragmentManager,
        fragment: Fragment
    ) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.fade_out,
                android.R.anim.fade_in,
                android.R.anim.slide_out_right
            )
            .replace(R.id.fragment_container, fragment, null)
            .addToBackStack(null)
            .commit()
    }

    fun getDateTime(timestamp: Long): String? {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yy HH:mm", Locale.ENGLISH)
            val netDate = Date(timestamp)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }
}