package com.omkarmarne.groupchat.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user",
    indices = [Index(value = ["user_number"], unique = true)]
)
data class User(
    @PrimaryKey @field:ColumnInfo(name = "user_id") val userId: String,
    @field:ColumnInfo(name = "user_name") val userName: String,
    @field:ColumnInfo(name = "user_number") val userNumber: String,
    @field:ColumnInfo(name = "user_profile_picture") val userProfilePicture: String
)