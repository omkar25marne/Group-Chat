package com.omkarmarne.groupchat.utility.listeners

/**
 * Callback interface for group click
 */
interface OnGroupClickListener {
    fun onGroupClick(groupId: String, groupName: String, profileImage: String)
}