package com.omkarmarne.groupchat.db_handler

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.omkarmarne.groupchat.models.*

@Dao
interface ChatDao {
    // region - Insert into db
    @Insert
    fun insert(groups: Groups)

    @Insert
    fun insert(message: Message)

    @Insert
    fun insert(user: User)

    @Insert
    fun insert(groupUsers: GroupUsers)
    // endregion

    // region - Fetch data
    @Query("SELECT * FROM groups WHERE group_id=:groupId")
    fun getGroupById(groupId: String): Groups

    @Query("SELECT * FROM message WHERE associate_group_id=:groupId ORDER BY timestamp ASC")
    fun getGroupMessages(groupId: String): LiveData<List<Message>>

    @Query(
        "SELECT * FROM user " +
                "INNER JOIN groupUsers ON groupUsers.user_id=user.user_id " +
                "WHERE groupUsers.group_id=:groupId ORDER BY user_name ASC"
    )
    fun getUsersInGroup(groupId: String): LiveData<List<User>>

    @Query(
        "SELECT * FROM user " +
                "WHERE user_id NOT IN " +
                "(SELECT DISTINCT user_id from groupUsers WHERE group_id=:groupId) " +
                "ORDER BY user_name"
    )
    fun getUsersNotInGroup(groupId: String): LiveData<List<User>>

    @Query(
        "SELECT * FROM groups " +
                "INNER JOIN groupUsers ON groupUsers.group_id = groups.group_id " +
                "WHERE user_id=:userId ORDER BY group_name ASC"
    )
    fun getGroupsOfUser(userId: String): LiveData<List<Groups>>

    @Query("SELECT * FROM user WHERE user_id=:userId")
    fun getUserById(userId: String): User

    @Query(
        "SELECT * FROM message, user " +
                "WHERE message.associate_group_id=:groupId AND message.sender_id=user.user_id"
    )
    fun getGroupMessageWithUser(groupId: String): LiveData<List<MessageWithUser>>
    // endregion

    // region - update data
    @Query("UPDATE message SET likes=:count, likesBy=:likesBy WHERE id=:messageId")
    fun updateLikesForMessage(messageId: Long, count: Long, likesBy: List<String>)
    // endregion

    // region - Manage Users in Group
    @Query("DELETE FROM groupUsers WHERE group_id=:groupId AND user_id=:userId")
    fun removeUserFromGroup(groupId: String, userId: String)
    // endregion

    // region - Clear DB
    @Query("DELETE FROM message")
    fun deleteMessages()

    @Query("DELETE FROM groups")
    fun deleteGroups()

    @Query("DELETE FROM user")
    fun deleteUsers()

    @Query("DELETE FROM groupUsers")
    fun deleteGroupUsers()
    // endregion
}