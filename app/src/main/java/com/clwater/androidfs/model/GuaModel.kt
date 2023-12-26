package com.clwater.androidfs.model

data class GuaModel(
    val id: Int,
    val name: String,
    val descGroup: String,
    val desc: String,
    val image: List<Int>,
    val explains: List<Explain>
)
data class Explain(
    val explainType: Int,
    val mainExplain: String,
    val base: String,
    val items: List<ExplainItem>,
)

data class ExplainItem(
    val index: Int,
    val explain: String
)

data class Yao(
    val index: Int,
    val image: String,
    val base: String,
    val explain: List<YaoItem>,
    val philosophy: String
)

data class YaoItem(
    val origin: String,
    val explain: String
)