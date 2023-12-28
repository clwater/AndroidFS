package com.clwater.androidfs.manager;

import android.util.Log
import com.clwater.androidfs.database.DatabaseManager
import com.clwater.androidfs.model.Explain
import com.clwater.androidfs.model.ExplainItem
import com.clwater.androidfs.model.GuaModel
import com.clwater.androidfs.model.Yao
import com.clwater.androidfs.model.YaoItem

class GuaManager private constructor() {
    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = GuaManager()
    }

    private val yaoMaps = mutableMapOf<String, Int>()
    val explainMap = mapOf(
        0 to "事业",
        1 to "经商",
        2 to "求名",
        3 to "外出",
        4 to "婚恋",
        5 to "决策",
        6 to "解释",
        7 to "特性",
        8 to "运势",
        9 to "家运",
        10 to "疾病",
        11 to "胎孕",
        12 to "子女",
        13 to "周转",
        14 to "买卖",
        15 to "等人",
        16 to "寻人",
        17 to "失物",
        18 to "考试",
        19 to "诉讼",
        20 to "求事",
        21 to "改行",
        22 to "开业",
        23 to "小凶"
    )

    fun findExplainStr(index: Int): String {
        return explainMap[index]!!
    }

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

        val yaoEntities = DatabaseManager.getInstance().getYaoByGuaId(id)
        val yaoList = mutableListOf<Yao>()




        yaoEntities.forEach{
            val yaoExplainList = mutableListOf<YaoItem>()
            yaoExplainList.add(YaoItem(
                origin = it.explain_0_origin,
                explain = it.explain_0_explain
            ))
            yaoExplainList.add(YaoItem(
                origin = it.explain_1_origin,
                explain = it.explain_1_explain
            ))

            val yao = Yao(
                index = it.index,
                image = it.image,
                base = it.base,
                explain = yaoExplainList,
                philosophy = it.philosophy
            )
            yaoList.add(yao)
        }

        val explains = DatabaseManager.getInstance().getExplainByGuaId(id)
        val explainList = mutableListOf<Explain>()
        explains.forEach{explainIt ->
            val explainItemList = mutableListOf<ExplainItem>()
            val explainItems = DatabaseManager.getInstance().getExplainItemByExplainId(explainIt.id)
            explainItems.forEach{explainItem ->
                explainItemList.add(
                    ExplainItem(
                    index = explainItem.index,
                    explain = explainItem.explain
                )
                )
            }
            val explain = Explain(
                explainType = explainIt.explainType,
                mainExplain = explainIt.mainExplain,
                base = explainIt.base,
                items = explainItemList
            )
            explainList.add(explain)
        }

        gua = GuaModel(
            id = guaEntity.id,
            name = guaEntity.name,
            desc_group =  guaEntity.desc_group,
            desc_detail = guaEntity.detail,
            desc = guaEntity.desc,
            image = guaEntity.image.split(",").map { it.toInt() },
            explains = explainList,
            yao = yaoList.toList()
        )
        return gua
    }
}
