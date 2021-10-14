package com.example.satriakurniawan.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.satriakurniawan.data.entities.Character
import com.example.satriakurniawan.data.entities.MyCharacter

@Database(entities = [Character::class,MyCharacter::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "pokemon.db")
                .fallbackToDestructiveMigration().allowMainThreadQueries()
                .build()
    }

}