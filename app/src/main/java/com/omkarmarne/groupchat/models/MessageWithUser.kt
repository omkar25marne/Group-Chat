package com.omkarmarne.groupchat.models

import androidx.room.Embedded

data class MessageWithUser(
    @Embedded
    val message: Message,
    @Embedded
    val user: User
)