package com.omkarmarne.groupchat.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.omkarmarne.groupchat.db_handler.ChatRepository

class ChatViewModelFactory(private val repository: ChatRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GroupListViewModel::class.java)) {
            return GroupListViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(MessageViewModel::class.java)) {
            return MessageViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(GroupDetailsViewModel::class.java)) {
            return GroupDetailsViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(GroupAddMemberViewModel::class.java)) {
            return GroupAddMemberViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown viewModel class")
    }
}