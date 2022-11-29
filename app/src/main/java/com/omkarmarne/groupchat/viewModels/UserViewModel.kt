package com.omkarmarne.groupchat.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.omkarmarne.groupchat.db_handler.ChatRepository
import com.omkarmarne.groupchat.models.Groups
import com.omkarmarne.groupchat.models.User

class UserViewModel(private val repository: ChatRepository) : ViewModel() {

    fun insert(user: User) {
        repository.insert(user)
    }

    fun getGroupsOfUser(userId: String): LiveData<List<Groups>> {
        return repository.getGroupsOfUser(userId)
    }
}