package com.clwater.androidfs

import android.app.Application
import com.clwater.androidfs.database.DatabaseManager
import com.clwater.androidfs.database.entity.Yao

class FSApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        DatabaseManager.init(this)
        val yao: Yao = Yao(1, "yao")
        DatabaseManager.getInstance().insertYao(yao)
    }
}