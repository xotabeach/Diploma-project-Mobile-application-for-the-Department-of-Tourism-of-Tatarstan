package com.example.mobile_tour.ui.home.kotlin

import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.mobile_tour.R

data class CarouselItem(val title: String, val description: String, val imageResId: Int)

class CarouselMain {
    companion object {
        @JvmStatic
        @OptIn(ExperimentalPagerApi::class)
        @Composable
        fun CarouselCard(parent: ViewGroup) {
            val pagerState = rememberPagerState(initialPage = 2)

            val sliderList = listOf(
                CarouselItem("", "Описание 1", R.drawable.question),
                CarouselItem("", "Описание 2", R.drawable.home),
                CarouselItem("", "Описание 3", R.drawable.home),
                CarouselItem("", "Описание 4", R.drawable.home),
            )

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                HorizontalPager(
                    count = sliderList.size,
                    state = pagerState,
                    contentPadding = PaddingValues(horizontal = 50.dp),
                    modifier = Modifier.height(350.dp)
                ) { page ->
                    val item = sliderList[page]
                    Card(
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(text = item.title)
                            Text(text = item.description)
                            // Здесь вы можете также добавить отображение изображения, используя компонент Image
                        }
                    }
                }
            }
        }
    }
}
