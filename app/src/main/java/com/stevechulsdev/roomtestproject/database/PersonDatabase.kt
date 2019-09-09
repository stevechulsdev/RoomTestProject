package com.stevechulsdev.roomtestproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stevechulsdev.roomtestproject.dao.PersonDao
import com.stevechulsdev.roomtestproject.entity.Person

@Database(entities = arrayOf(Person::class), version = 1)
abstract class PersonDatabase: RoomDatabase() {

    abstract fun getPersonDao(): PersonDao

    companion object {
        private var INSTANCE: PersonDatabase? = null

        fun getInstance(context: Context): PersonDatabase? {
            INSTANCE?: kotlin.run {
                synchronized(PersonDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        PersonDatabase::class.java,
                        "person.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}