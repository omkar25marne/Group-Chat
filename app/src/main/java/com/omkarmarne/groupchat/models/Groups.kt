package com.omkarmarne.groupchat.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups")
data class Groups(
    @PrimaryKey @field:ColumnInfo(name = "group_id") val groupId: String,
    @field:ColumnInfo(name = "group_name") val groupName: String,
    @field:ColumnInfo(name = "group_profile_image") val groupProfileImage: String
)