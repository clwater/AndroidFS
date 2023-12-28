package com.clwater.androidfs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.clwater.androidfs.database.dao.ExplainDao
import com.clwater.androidfs.database.dao.ExplainItemDao
import com.clwater.androidfs.database.dao.GuaDao
import com.clwater.androidfs.database.dao.YaoDao
import com.clwater.androidfs.database.entity.ExplainEntity
import com.clwater.androidfs.database.entity.ExplainItemEntity
import com.clwater.androidfs.database.entity.GuaEntity
import com.clwater.androidfs.database.entity.YaoEntity

@Database(
    entities = [GuaEntity::class, YaoEntity::class, ExplainEntity::class, ExplainItemEntity::class],
    version = 1,
)
abstract class DatabaseManager : RoomDatabase() {
    abstract fun guaDao(): GuaDao
    abstract fun yaoDao(): YaoDao
    abstract fun explainDao(): ExplainDao
    abstract fun explainItemDao(): ExplainItemDao

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

    fun deleteAll(){
        guaDao().deleteAll()
        yaoDao().deleteAll()
        explainDao().deleteAll()
        explainItemDao().deleteAll()
    }

    fun getGuaList(): List<GuaEntity> {
        return guaDao().getAll()
    }

    fun insertGua(vararg guaEntity: GuaEntity) {
        guaDao().insertAll(*guaEntity)
    }

    fun  getGuaById(id: Int): GuaEntity {
        return guaDao().getById(id)
    }


    fun insertYao(vararg yaoEntity: YaoEntity) {
        yaoDao().insertAll(*yaoEntity)
    }

    fun getYaoByGuaId(guaId: Int): List<YaoEntity> {
        return yaoDao().getByGuaId(guaId)
    }

    fun insertExplain(vararg explainEntity: ExplainEntity) {
        explainDao().insertAll(*explainEntity)
    }

    fun getExplainByGuaId(guaId: Int): List<ExplainEntity> {
        return explainDao().getByGuaId(guaId)
    }

    fun insertExplainItem(vararg explainItemEntity: ExplainItemEntity) {
        explainItemDao().insertAll(*explainItemEntity)
    }

    fun getExplainItemByExplainId(explainId: Int): List<ExplainItemEntity> {
        return explainItemDao().getByExplainId(explainId)
    }


}