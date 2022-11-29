package com.omkarmarne.groupchat.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.omkarmarne.groupchat.db_handler.ChatRepository
import com.omkarmarne.groupchat.models.Message
import com.omkarmarne.groupchat.models.MessageWithUser

class MessageViewModel(private val repository: ChatRepository) : ViewModel() {

    fun insert(message: Message) {
        repository.insert(message)
    }

    fun getGroupMessages(groupId: String): LiveData<List<MessageWithUser>> {
        return repository.getGroupMessages(groupId)
    }

    fun updateLikesForMessage(messageId: Long, count: Long, likesBy: List<String>) {
        repository.updateLikesForMessage(messageId, count, likesBy)
    }
}