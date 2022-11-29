package com.omkarmarne.groupchat.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.omkarmarne.groupchat.db_handler.ChatRepository
import com.omkarmarne.groupchat.models.User

class GroupAddMemberViewModel(private val repository: ChatRepository) : ViewModel() {

    fun getUsersNotInGroup(groupId: String): LiveData<List<User>> {
        return repository.getUsersNotInGroup(groupId)
    }
}