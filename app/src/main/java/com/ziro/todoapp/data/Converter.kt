package com.ziro.todoapp.data

import androidx.room.TypeConverter
import com.ziro.todoapp.data.Priority

class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority) : String{
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String) : Priority {
        return Priority.valueOf(priority)
    }
}