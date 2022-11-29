package com.omkarmarne.groupchat.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.omkarmarne.groupchat.R
import com.omkarmarne.groupchat.utility.LoginSessionHandler
import com.omkarmarne.groupchat.utility.Utils
import com.omkarmarne.groupchat.view.fragment.GroupListFragment
import com.omkarmarne.groupchat.view.fragment.LoginFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginSessionHandler = LoginSessionHandler(baseContext)

        if (loginSessionHandler.isLoggedIn()) {
            Utils().navigateFragment(
                supportFragmentManager,
                GroupListFragment.newInstance()
            )
        } else {
            Utils().navigateFragment(supportFragmentManager, LoginFragment.newInstance())
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}