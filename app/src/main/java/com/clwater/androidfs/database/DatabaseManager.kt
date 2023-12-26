package com.clwater.androidfs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.clwater.androidfs.database.dao.YaoDao
import com.clwater.androidfs.database.entity.Yao

@Database(
    entities = [Yao::class],
    version = 1,
)
abstract class DatabaseManager : RoomDatabase() {
    abstract fun yaoDao(): YaoDao

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

    fun getYaoList(): List<Yao> {
        return yaoDao().getAll()
    }

    fun insertYao(vararg yao: Yao) {
        yaoDao().insertAll(*yao)
    }


}