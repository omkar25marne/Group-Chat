package com.omkarmarne.groupchat.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.omkarmarne.groupchat.db_handler.ChatRepository
import com.omkarmarne.groupchat.models.GroupUsers
import com.omkarmarne.groupchat.models.User

class GroupDetailsViewModel(private val repository: ChatRepository) : ViewModel() {

    fun getUsersInGroup(groupId: String): LiveData<List<User>> {
        return repository.getUsersInGroup(groupId)
    }

    fun getUser(userId: String): User {
        return repository.getUser(userId)
    }

    fun removeUserFromGroup(groupId: String, userId: String) {
        repository.removeUserFromGroup(groupId, userId)
    }

    fun insert(groupUser: GroupUsers) {
        repository.insert(groupUser)
    }
}