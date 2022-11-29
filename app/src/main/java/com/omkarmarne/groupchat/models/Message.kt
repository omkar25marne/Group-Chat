package com.omkarmarne.groupchat.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class Message(
    @field:ColumnInfo(name = "associate_group_id") val groupId: String,
    @field:ColumnInfo(name = "sender_id") val senderId: String,
    @field:ColumnInfo(name = "sender_number") val senderNumber: String,
    @field:ColumnInfo(name = "content") val content: String,
    @field:ColumnInfo(name = "timestamp") val timestamp: Long,
    @field:ColumnInfo(name = "likes") val likes: Long,
    @field:ColumnInfo(name = "likesBy") val likesBy: List<String>
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}