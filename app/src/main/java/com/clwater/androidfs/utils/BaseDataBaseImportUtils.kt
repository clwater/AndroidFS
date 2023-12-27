package com.clwater.androidfs.utils

import android.content.Context
import android.util.Log
import com.clwater.androidfs.database.DatabaseManager
import com.clwater.androidfs.database.entity.ExplainEntity
import com.clwater.androidfs.database.entity.ExplainItemEntity
import com.clwater.androidfs.database.entity.GuaEntity
import com.clwater.androidfs.database.entity.YaoEntity
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
            val gson = Gson()
            val guaModels = gson.fromJson(json, Array<GuaModel>::class.java)

            saveGuas(guaModels)
        }

        private fun saveGuas(guaModels: Array<GuaModel>) {
            val guaTables = mutableListOf<GuaEntity>()
            val yaoTables = mutableListOf<YaoEntity>()
            val explainTables = mutableListOf<ExplainEntity>()
            val explainItemTables = mutableListOf<ExplainItemEntity>()
            guaModels.forEach { guaModel ->
                val images = guaModel.image.toString().replace("[", "").replace("]", "").replace(" ", "")
                val guaEntity = GuaEntity(
                    guaModels.indexOf(guaModel) + 1,
                    guaModel.name,
                    guaModel.desc_group,
                    guaModel.desc,
                    images,
                    guaModel.desc_detail
                )
                guaTables.add(guaEntity)
//                DatabaseManager.getInstance().insertGua(guaEntity)

                guaModel.yao.forEach{yaoModel ->

                    val yaoEntity = YaoEntity(
                        id = guaEntity.id * 64 + guaModel.yao.indexOf(yaoModel) + 1,
                        gua_id = guaEntity.id,
                        index = guaModel.yao.indexOf(yaoModel) + 1,
                        image = yaoModel.image,
                        base = yaoModel.base,
                        explain_0_origin = yaoModel.explain[0].origin,
                        explain_0_explain = yaoModel.explain[0].explain,
                        explain_1_origin = yaoModel.explain[1].origin,
                        explain_1_explain = yaoModel.explain[1].explain,
                        explain_2_origin = yaoModel.explain[2].origin,
                        explain_2_explain = yaoModel.explain[2].explain,
                        philosophy = yaoModel.philosophy
                    )
                    yaoTables.add(yaoEntity)
                }

                guaModel.explains.forEach{explain ->
                    val base =  explain.base ?: ""
                    val explainEntity = ExplainEntity(
                        id = guaEntity.id * 2 + explain.explainType,
                        gua_id = guaEntity.id,
                        explainType = explain.explainType,
                        mainExplain = explain.mainExplain,
                        base = base
                    )
                    explainTables.add(explainEntity)
                    if (!explain.items.isNullOrEmpty()) {
                        explain.items.forEach { explainItem ->
                            val explainItemEntity = ExplainItemEntity(
                                id = explainEntity.id * 20 + explainItem.index,
                                gua_id = guaEntity.id,
                                explain_Id = explainEntity.id,
                                index = explainItem.index,
                                explain = explainItem.explain
                            )
                            explainItemTables.add(explainItemEntity)
                        }
                    }

                }
            }
            DatabaseManager.getInstance().insertGua(*guaTables.toTypedArray())
            DatabaseManager.getInstance().insertYao(*yaoTables.toTypedArray())
            DatabaseManager.getInstance().insertExplain(*explainTables.toTypedArray())
            DatabaseManager.getInstance().insertExplainItem(*explainItemTables.toTypedArray())
        }
    }

}