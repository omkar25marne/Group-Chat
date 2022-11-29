package com.omkarmarne.groupchat

import android.app.Application
import com.omkarmarne.groupchat.db_handler.ChatDatabase
import com.omkarmarne.groupchat.db_handler.ChatRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class GroupChatApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy {
        ChatDatabase.getDatabase(this, applicationScope)
    }

    val repository by lazy {
        ChatRepository(database.databaseDao())
    }
}