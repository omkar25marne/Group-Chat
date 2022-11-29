package com.omkarmarne.groupchat.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.omkarmarne.groupchat.db_handler.ChatRepository
import com.omkarmarne.groupchat.models.Groups

class GroupListViewModel(private val repository: ChatRepository) : ViewModel() {

    fun insert(group: Groups) {
        repository.insert(group)
    }

    fun getGroupsOfUser(userId: String): LiveData<List<Groups>> {
        return repository.getGroupsOfUser(userId)
    }
}