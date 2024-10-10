package com.faraz.gitfinder.data.db

import androidx.room.TypeConverter
import com.faraz.gitfinder.data.model.Owner
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromOwner(owner: Owner?): String? {
        return Gson().toJson(owner)
    }

    @TypeConverter
    fun toOwner(ownerString: String?): Owner? {
        val ownerType = object : TypeToken<Owner>() {}.type
        return Gson().fromJson(ownerString, ownerType)
    }

    @TypeConverter
    fun fromStringList(value: String?): List<String>? {
        return value?.split(",")?.map { it.trim() }
    }

    @TypeConverter
    fun toStringList(list: List<String>?): String? {
        return list?.joinToString(",")
    }
}
