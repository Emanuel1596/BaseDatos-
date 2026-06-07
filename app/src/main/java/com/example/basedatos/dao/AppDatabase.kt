package com.example.basedatos.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.basedatos.converters.Converters
import com.example.basedatos.model.Usuario

@Database(entities = [Usuario::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
}