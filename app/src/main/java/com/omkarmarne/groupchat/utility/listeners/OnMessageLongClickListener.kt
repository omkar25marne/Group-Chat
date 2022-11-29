package com.omkarmarne.groupchat.utility.listeners

import com.omkarmarne.groupchat.models.Message

/**
 * Callback interface for message long click to like the message
 */
interface OnMessageLongClickListener {
    fun onMessageLongClick(message: Message)
}