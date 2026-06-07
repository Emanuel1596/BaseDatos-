package com.example.basedatos

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.basedatos.dao.AppDatabase

class MyApplication : Application() {
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "usuarios-db"
                )
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}