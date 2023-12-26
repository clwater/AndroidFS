package com.clwater.androidfs.utils

import android.content.Context
import android.util.Log
import com.clwater.androidfs.database.DatabaseManager
import com.clwater.androidfs.database.entity.ExplainEntity
import com.clwater.androidfs.database.entity.GuaEntity
import com.clwater.androidfs.model.GuaModel
import com.google.gson.Gson

class BaseDataBaseImportUtils {
    object INSRANCE {
        fun importBaseData(context: Context) {
            val jsonFile = context.resources.assets.open("gua.json")
            val size = jsonFile.available()
            val buffer = ByteArray(size)
            jsonFile.read(buffer)
            jsonFile.close()
            val json = String(buffer)
            Log.d("gzb", "json = $json")
            val gson = Gson()
            val guaModels = gson.fromJson(json, Array<GuaModel>::class.java)

            Log.d("gzb", "guaModels.size = ${guaModels.size}")
            Log.d("gzb", "guaModels = $guaModels")
            saveGuas(guaModels)
        }

        private fun saveGuas(guaModels: Array<GuaModel>?) {
            guaModels?.forEach {
                val guaEntity = GuaEntity(
                    it.id,
                    it.name,
                    it.descGroup,
                    it.desc,
                    it.image.toString(),
                )
                DatabaseManager.getInstance().insertGua(guaEntity)
                
            }
        }
    }

}