package com.omkarmarne.groupchat.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "groupUsers",
    foreignKeys = [
        ForeignKey(
            entity = Groups::class,
            childColumns = ["group_id"],
            parentColumns = ["group_id"]
        ),
        ForeignKey(
            entity = User::class,
            childColumns = ["user_id"],
            parentColumns = ["user_id"]
        )
    ]
)
data class GroupUsers(
    @field:ColumnInfo(name = "user_id") val userId: String,
    @field:ColumnInfo(name = "group_id") val groupId: String,
    @field:ColumnInfo(name = "is_admin") val isAdmin: Int,
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}