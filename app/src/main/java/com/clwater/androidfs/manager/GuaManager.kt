package com.clwater.androidfs.manager;

import android.util.Log
import com.clwater.androidfs.database.DatabaseManager
import com.clwater.androidfs.model.GuaModel

class GuaManager private constructor() {
    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = GuaManager()
    }

    private val yaoMaps = mutableMapOf<String, Int>()

    fun initQuickYao(){
        val guaList = DatabaseManager.getInstance().getGuaList()
        guaList.forEach{gua ->
            var image = ""
            gua.image.split(",").map { image+=it }
            yaoMaps[image] = gua.id
        }
    }

    fun findYaoIndex(list: List<Int>): Int {
        var image = ""
        list.forEach{
            image += it
        }
        return findYaoIndex(image)
    }

    fun findYaoIndex(image: String): Int {
        return yaoMaps[image]!!
    }

    fun getGua(id: Int): GuaModel{
        val gua: GuaModel

        val guaEntity = DatabaseManager.getInstance().getGuaById(id)
//        var guaImage = guaEntity.image.replace("[", "")
//        guaImage = guaImage.replace("]", "")
//        guaImage = guaImage.replace(" ", "")

        gua = GuaModel(
            id = guaEntity.id,
            name = guaEntity.name,
            desc_group =  guaEntity.desc_group,
            desc_detail = guaEntity.detail,
            desc = guaEntity.desc,
            image = guaEntity.image.split(",").map { it.toInt() },
//            image = listOf(),
            explains = listOf(),
            yao = listOf()
        )

        return gua
    }
}
