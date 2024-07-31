package com.example.news360.db;

import androidx.room.TypeConverter;
import com.example.news360.data.models.Source;

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}
