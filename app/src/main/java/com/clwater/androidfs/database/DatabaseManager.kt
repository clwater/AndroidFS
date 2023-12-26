package com.clwater.androidfs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.clwater.androidfs.database.dao.GuaDao
import com.clwater.androidfs.database.entity.GuaEntity

@Database(
    entities = [GuaEntity::class],
    version = 1,
)
abstract class DatabaseManager : RoomDatabase() {
    abstract fun guaDao(): GuaDao

    companion object {
        private var INSTANCE: DatabaseManager? = null

        //Application 调用
        fun init(context: Context): DatabaseManager? {
            if (INSTANCE == null) {
                INSTANCE =
                    Room.databaseBuilder(context, DatabaseManager::class.java, "androidfs.db")
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE
        }

        //使用者调用
        fun getInstance(): DatabaseManager {
            return INSTANCE!!
        }
    }

    fun getGuaList(): List<GuaEntity> {
        return guaDao().getAll()
    }

    fun insertGua(vararg guaEntity: GuaEntity) {
        guaDao().insertAll(*guaEntity)
    }


}