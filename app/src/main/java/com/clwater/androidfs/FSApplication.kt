package com.clwater.androidfs

import android.app.Application
import com.clwater.androidfs.database.DatabaseManager
import com.clwater.androidfs.database.entity.GuaEntity

class FSApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        DatabaseManager.init(this)
//        val guaEntity: GuaEntity = GuaEntity(1, "yao")
//        DatabaseManager.getInstance().insertGua(guaEntity)
    }
}