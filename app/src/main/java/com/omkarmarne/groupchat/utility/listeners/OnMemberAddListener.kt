package com.omkarmarne.groupchat.utility.listeners

import com.omkarmarne.groupchat.models.User

/**
 * Callback interface for add member click
 */
interface OnMemberAddListener {
    fun onMemberAddClick(user: User)
}