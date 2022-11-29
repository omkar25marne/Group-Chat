package com.omkarmarne.groupchat.db_handler

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.omkarmarne.groupchat.R
import com.omkarmarne.groupchat.models.GroupUsers
import com.omkarmarne.groupchat.models.Groups
import com.omkarmarne.groupchat.models.Message
import com.omkarmarne.groupchat.models.User
import com.omkarmarne.groupchat.models.converter.TypeConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [Groups::class, Message::class, User::class, GroupUsers::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun databaseDao(): ChatDao

    companion object {
        private var INSTANCE: ChatDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ChatDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChatDatabase::class.java,
                    context.getString(R.string.db_name)
                )
                    .allowMainThreadQueries()
                    .addCallback(AppDatabaseCallback(scope))
                    .addTypeConverter(TypeConverter())
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

    class AppDatabaseCallback(private val scope: CoroutineScope) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    clearDatabase(database.databaseDao())
                    createFakeDatabase(database.databaseDao())
                }
            }
        }

        private fun clearDatabase(groupChatDao: ChatDao) {
            groupChatDao.deleteMessages()
            groupChatDao.deleteGroups()
            groupChatDao.deleteUsers()
            groupChatDao.deleteGroupUsers()
        }

        private fun createFakeDatabase(groupChatDao: ChatDao) {
            FakeDatabase().generateGroups(groupChatDao)
            FakeDatabase().generateUsers(groupChatDao)
            FakeDatabase().addGroupMembers(groupChatDao)
            FakeDatabase().generateMessages(groupChatDao)
        }
    }
}