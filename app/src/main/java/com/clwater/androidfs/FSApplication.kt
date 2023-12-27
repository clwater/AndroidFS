package com.clwater.androidfs

import android.app.Application
import com.clwater.androidfs.database.DatabaseManager
import com.clwater.androidfs.database.entity.GuaEntity
import com.clwater.androidfs.utils.BaseDataBaseImportUtils

class FSApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        DatabaseManager.init(this)
        if (DatabaseManager.getInstance().getGuaList().isEmpty()){
            DatabaseManager.getInstance().deleteAll()
            BaseDataBaseImportUtils.INSRANCE.importBaseData(this)
        }
    }
}