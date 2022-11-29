package com.omkarmarne.groupchat.utility.listeners

import com.omkarmarne.groupchat.models.User

/**
 * Callback interface for user click
 */
interface OnUserClickListener {
    fun onUserClick(userId: String, userName: String, userNumber: String, profileImage: String)

    fun onUserActionClick(user: User)
}