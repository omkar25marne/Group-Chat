package com.omkarmarne.groupchat.models.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class TypeConverter {
    @TypeConverter
    fun saveListOfSenderIds(senderIds: List<String>): String {
        return Gson().toJson(senderIds)
    }

    @TypeConverter
    fun restoreListOfSenderIds(senderIds: String): List<String> {
        return Gson().fromJson(senderIds, object : TypeToken<List<String?>?>() {}.type)
    }
}