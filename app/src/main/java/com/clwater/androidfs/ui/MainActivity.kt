package com.clwater.androidfs.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.clwater.androidfs.manager.GuaManager
import com.clwater.androidfs.model.ExplainItem
import com.clwater.androidfs.model.GuaModel
import com.clwater.androidfs.model.Yao
import com.clwater.androidfs.ui.theme.AndroidFSTheme

class MainActivity : ComponentActivity() {
    companion object {
        val YaoHeight = 30.dp
        val TitleColor = Color(0xFF105150)
        val BackgroundColor = Color(0xFF468E8D)
        val NameColor = Color(0xFF66a09f)
        val ArrowColor = Color(0x80FFFFFF)

        val TitleColorYao = Color(0xFF231C66)
        val BackgroundColorYao = Color(0xFF827AC9)
        val BackgroundColorUnChooseYao = Color(0xBF695DCF)
        val NameColorYao = Color(0xFF9D94EE)

        val TitleColoExplain = Color(0xFF46183E)
        val BackgroundColorExplain = Color(0xFF96568B)
        val NameColorExplain = Color(0xFFBE74B2)


        val ExplainItemBackgroundColor_0 = Color(0xFFF44336)
        val ExplainItemBackgroundColor_1 = Color(0xFF009688)
    }

    class MainViewModel : ViewModel() {
        val mCurrentIndex = mutableStateOf(1)
        val mYaoImages = mutableStateListOf(1, 1, 1, 1, 1, 1)

        val mCurrentTitle = mutableStateOf("")
        val mSubTitle = mutableStateOf("")
        val mCurrentName = mutableStateOf("")

        val mYaoIndex = mutableStateOf(0)
        val mYaoList = mutableStateListOf<Pair<Int, Int>>()
        val mYaoBase = mutableStateOf("")
        val mYaoExplains = mutableStateListOf<Pair<String, String>>()
        val mYaoPhilosophy = mutableStateOf("")

        val mExplainList = mutableStateMapOf<Int, Pair<String, String?>>()
        val mExplainItemList = mutableStateListOf<Pair<Int, Pair<String, String>>>()


        private val yaoModels = mutableStateListOf<Yao>()


        fun changeIndex() {
            var image = ""
            yaoModels.clear()

            mYaoList.clear()
            mYaoImages.forEach {
                image += it.toString()
            }
            mCurrentIndex.value = GuaManager.instance.findYaoIndex(image)
            val guaModel = GuaManager.instance.getGua(mCurrentIndex.value)
            mCurrentTitle.value = "" + guaModel.id + "." + guaModel.desc_group
            mSubTitle.value = guaModel.desc_detail
            mCurrentName.value = guaModel.name

            guaModel.yao.forEach {
                yaoModels.add(it)
                mYaoList.add(Pair(it.index, it.image.toInt()))
            }

            changeYaoIndex(1)
//            mYaoBase.value = yaoModels[mYaoIndex.value].base

            mExplainList.clear()
            mExplainItemList.clear()
            guaModel.explains.forEach { explain ->
                mExplainList[explain.explainType] = Pair(explain.mainExplain, explain.base)
                explain.items?.forEach { item ->
                    mExplainItemList.add(
                        Pair(
                            explain.explainType,
                            Pair(GuaManager.instance.findExplainStr(item.index), item.explain)
                        )
                    )
                }
            }

        }


        fun changeYaoIndex(index: Int) {
            mYaoIndex.value = index
            val yao = yaoModels[mYaoIndex.value - 1]
            mYaoBase.value = yao.base
            mYaoExplains.clear()
            mYaoExplains.add(Pair(yao.explain[0].origin, yao.explain[0].explain))
            mYaoExplains.add(Pair(yao.explain[1].origin, yao.explain[1].explain))
            mYaoPhilosophy.value = yao.philosophy
        }
    }

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.changeIndex()
        setContent {
            AndroidFSTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    FS()
                }
            }
        }
    }

    @Composable
    fun FSMain(pageChange: FSPagerChange) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BackgroundColor)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = viewModel.mCurrentName.value,
                    modifier = Modifier,
                    fontWeight = FontWeight.Bold,
                    fontSize = if (viewModel.mCurrentName.value.length > 1) 200.sp else 300.sp,
                    color = NameColor,
                    maxLines = 1,
                )
            }

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    ) {
                        for (i in 0..5) {

                            Row(modifier = Modifier
                                .padding(
                                    top = YaoHeight / 2,
                                    bottom = if (i == 2) YaoHeight else YaoHeight / 2
                                )
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.mYaoImages[i] =
                                        if (viewModel.mYaoImages[i] == 0) 1 else 0
                                    viewModel.changeIndex()
                                }) {
                                Box(
                                    modifier = Modifier
                                        .height(YaoHeight)
                                        .weight(1f)
                                        .background(color = Color.White)
                                )


                                Box(
                                    modifier = Modifier
                                        .height(YaoHeight)
                                        .width(YaoHeight)
                                        .background(color = if (viewModel.mYaoImages[i] == 1) Color.White else Color.Transparent)
                                )

                                Box(
                                    modifier = Modifier
                                        .height(YaoHeight)
                                        .weight(1f)
                                        .background(color = Color.White)
                                )
                            }

                        }

                    }

                    Column(
                        modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                            color = TitleColor,
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            text = viewModel.mCurrentTitle.value
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                            color = TitleColor,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            text = viewModel.mSubTitle.value
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(Modifier.clickable { pageChange.change(0) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier
                                .height(24.dp)
                                .width(24.dp),
                            tint = ArrowColor
                        )

                        Text(
                            text = "六爻", modifier = Modifier, fontSize = 16.sp, color = ArrowColor
                        )
                    }

                    Row(Modifier.clickable { pageChange.change(2) }) {
                        Text(
                            text = "释意", modifier = Modifier, fontSize = 16.sp, color = ArrowColor
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier
                                .height(24.dp)
                                .width(24.dp),
                            tint = ArrowColor
                        )

                    }
                }
            }
        }
    }


    @Composable
    fun FSYao(pageChange: FSPagerChange) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BackgroundColorYao)
        ) {
            // 背景文字
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = viewModel.mCurrentName.value,
                    modifier = Modifier,
                    fontWeight = FontWeight.Bold,
                    fontSize = if (viewModel.mCurrentName.value.length > 1) 200.sp else 300.sp,
                    color = NameColorYao,
                    maxLines = 1,
                )
            }

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // 中心内容
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        // 左侧菜单
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            for (i in viewModel.mYaoList.lastIndex downTo 0) {
                                val yao = viewModel.mYaoList[i]
                                Column(modifier = Modifier
                                    .weight(1f)
                                    .align(Alignment.CenterHorizontally)
                                    .clickable {
                                        viewModel.changeYaoIndex(yao.first)
                                    }
                                    .background(color = if (viewModel.mYaoIndex.value == yao.first) Color.Transparent else BackgroundColorUnChooseYao),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                )
                                {
                                    Row(
                                        modifier = Modifier
                                            .padding(8.dp),
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .height(YaoHeight / 10)
                                                .weight(1f)
                                                .background(color = Color.White)
                                        )

                                        Box(
                                            modifier = Modifier
                                                .height(YaoHeight / 10)
                                                .width(YaoHeight / 5)
                                                .background(color = if (yao.second == 1) Color.White else Color.Transparent)
                                        )

                                        Box(
                                            modifier = Modifier
                                                .height(YaoHeight / 10)
                                                .weight(1f)
                                                .background(color = Color.White)
                                        )
                                    }
                                    val yaoItemNameYao = if (yao.second == 1) "九" else "六"
                                    val yaoItemName = when (i) {
                                        0 -> "初$yaoItemNameYao"
                                        1 -> yaoItemNameYao + "二"
                                        2 -> yaoItemNameYao + "三"
                                        3 -> yaoItemNameYao + "四"
                                        4 -> yaoItemNameYao + "五"
                                        5 -> "上$yaoItemNameYao"
                                        else -> ""
                                    }
                                    Text(
                                        modifier = Modifier,
                                        color = TitleColorYao,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        text = "" + yaoItemName
                                    )

                                }

                            }
                        }
                        Column(
                            modifier = Modifier
                                .weight(4f)
                                .padding(8.dp)
                                .verticalScroll(rememberScrollState())
                        ) {

                            Text(
                                color = TitleColorYao,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                text = viewModel.mYaoBase.value
                            )
                            viewModel.mYaoExplains.forEach {
                                Column(
                                    modifier = Modifier.padding(vertical = 4.dp)
                                ) {
                                    Text(
                                        color = Color.White,
                                        text = "【" + it.first + "】"
                                    )
                                    Text(
                                        fontSize = 18.sp,
                                        color = TitleColorYao,
                                        text = it.second
                                    )
                                }
                            }
                            Text(
                                color = Color.White,
                                text = "【解读】"
                            )
                            Text(
                                fontSize = 18.sp,
                                color = TitleColorYao,
                                text = viewModel.mYaoPhilosophy.value
                            )
                        }
                    }

                }
                // 底部提示
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {

                    Row(Modifier.clickable { pageChange.change(1) }) {
                        Text(
                            text = "卦", modifier = Modifier, fontSize = 16.sp, color = ArrowColor
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier
                                .height(24.dp)
                                .width(24.dp),
                            tint = ArrowColor
                        )

                    }
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun FS() {
        var currentIndex by remember {
            mutableStateOf(0)
        }
        val pagerState = rememberPagerState(
            pageCount = { 3 },
            initialPage = 1,
        )

        LaunchedEffect(pagerState.currentPage) {
            currentIndex = pagerState.currentPage
        }
        LaunchedEffect(currentIndex) {
            pagerState.animateScrollToPage(currentIndex)
        }

        val fsPagerChange = object : FSPagerChange {
            override fun change(index: Int) {
                currentIndex = index
            }
        }

        HorizontalPager(
            state = pagerState,
        ) { page ->
            when (page) {
                0 -> FSYao(fsPagerChange)
                1 -> FSMain(fsPagerChange)
                2 -> FsExplain(fsPagerChange)
            }

        }

    }

    @Composable
    fun FsExplain(pageChange: FSPagerChange) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BackgroundColorExplain)
        ) {
            // 背景文字
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = viewModel.mCurrentName.value,
                    modifier = Modifier,
                    fontWeight = FontWeight.Bold,
                    fontSize = if (viewModel.mCurrentName.value.length > 1) 200.sp else 300.sp,
                    color = NameColorExplain,
                    maxLines = 1,
                )
            }

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // 中心内容
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Column {
                        viewModel.mExplainList.forEach { explain ->
                            Column(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth()
                                    .border(
                                        1.dp,
                                        if (explain.key == 0) ExplainItemBackgroundColor_0 else ExplainItemBackgroundColor_1
                                    )
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = explain.value.first,
                                    color = TitleColoExplain,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = explain.value.second ?: "",
                                    color = TitleColoExplain,
                                    fontSize = 16.sp,
                                )
                            }
                        }
                        val scrollState = rememberScrollState()
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                                .padding(8.dp)
                                .verticalScroll(scrollState)
                        ) {
                            viewModel.mExplainItemList.forEach { explainItem ->
                                Column(
                                    modifier = Modifier.padding(vertical = 4.dp)
                                ) {
                                    Text(
                                        color = if (explainItem.first == 0) ExplainItemBackgroundColor_0 else ExplainItemBackgroundColor_1,
                                        text = "【" + explainItem.second.first + "】"
                                    )
                                    Text(
                                        fontSize = 18.sp,
                                        color = TitleColoExplain,
                                        text = explainItem.second.second
                                    )
                                }
                            }
                        }
                    }

                }
                // 底部提示
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Row(Modifier.clickable { pageChange.change(1) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier
                                .height(24.dp)
                                .width(24.dp),
                            tint = ArrowColor
                        )
                        Text(
                            text = "卦", modifier = Modifier, fontSize = 16.sp, color = ArrowColor
                        )

                    }
                }
            }
        }
    }

    interface FSPagerChange {
        fun change(index: Int)
    }
}



