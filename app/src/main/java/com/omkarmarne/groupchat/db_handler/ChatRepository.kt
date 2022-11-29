package com.omkarmarne.groupchat.db_handler

import androidx.lifecycle.LiveData
import com.omkarmarne.groupchat.models.*

class ChatRepository(private val chatDao: ChatDao) {

    // region - User
    fun insert(user: User) {
        chatDao.insert(user)
    }

    fun getUser(userId: String): User {
        return chatDao.getUserById(userId)
    }
    // endregion

    // region - Group
    fun insert(group: Groups) {
        chatDao.insert(group)
    }

    fun getGroupsOfUser(userId: String): LiveData<List<Groups>> {
        return chatDao.getGroupsOfUser(userId)
    }

    fun getUsersInGroup(groupId: String): LiveData<List<User>> {
        return chatDao.getUsersInGroup(groupId)
    }

    fun getUsersNotInGroup(groupId: String): LiveData<List<User>> {
        return chatDao.getUsersNotInGroup(groupId)
    }

    fun removeUserFromGroup(groupId: String, userId: String) {
        return chatDao.removeUserFromGroup(groupId, userId)
    }

    fun insert(groupUser: GroupUsers) {
        return chatDao.insert(groupUser)
    }
    // endregion

    // region - Messages
    fun insert(message: Message) {
        chatDao.insert(message)
    }

    fun getGroupMessages(groupId: String): LiveData<List<MessageWithUser>> {
        return chatDao.getGroupMessageWithUser(groupId)
    }

    fun updateLikesForMessage(messageId: Long, count: Long, likesBy: List<String>) {
        chatDao.updateLikesForMessage(messageId, count, likesBy)
    }
    // endregion

}