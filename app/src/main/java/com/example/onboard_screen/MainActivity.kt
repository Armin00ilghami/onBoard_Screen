package com.example.onboard_screen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import com.example.onboard_screen.ui.theme.Grey300
import com.example.onboard_screen.ui.theme.Grey900
import com.example.onboard_screen.ui.theme.OnBoard_ScreenTheme
import com.example.onboard_screen.ui.theme.RedLight
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState


class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalPagerApi::class)
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OnBoard_ScreenTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    MainFunction()
                }
            }
        }
    }

    @ExperimentalPagerApi
    @Preview(showBackground = true)
    @Composable
    fun PreviewFunction() {
        Surface(modifier = Modifier.fillMaxSize()) {
            MainFunction()
        }
    }


    @ExperimentalPagerApi
    @Composable
    fun MainFunction() {
        val items = ArrayList<OnBoardingData>()

        items.add(
            OnBoardingData(
                R.raw.intro1,
                "Title Test 1",
                "Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface."
            )
        )

        items.add(
            OnBoardingData(
                R.raw.intro2,
                "Title Test 2",
                "Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface."
            )
        )

        items.add(
            OnBoardingData(
                R.raw.intro3,
                "Title Test 3",
                "Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface."
            )
        )

        items.add(
            OnBoardingData(
                R.raw.intro4,
                "Title Test 4",
                "Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface."
            )
        )

        val pagerState = RememberPagerState(
            pageCount = items.size,
            initialOffscreenLimit = 2,
            infiniteLoop = false,
            initialPage = 0
        )

        OnBoardingPager(
            item = items,
            pagerState = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
        )
    }

    @ExperimentalPagerApi
    @Composable
    fun OnBoardingPager(
        item: List<OnBoardingData>,
        pagerState: PagerState,
        modifier: Modifier = Modifier,
    ) {
        Box(modifier = modifier) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalPager(
                    state = pagerState
                ) { page ->
                    Column(
                        modifier = Modifier
                            .padding(60.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        /* Image(
                     painter = painterResource(id = item[page].image),
                     contentDescription = item[page].title,
                     modifier = Modifier
                         .height(250.dp)
                         .fillMaxWidth()
                 ) if you have image for your screens */
                        LoaderIntro(
                            modifier = Modifier
                                .size(200.dp)
                                .fillMaxWidth()
                                .align(alignment = Alignment.CenterHorizontally), item[page].image
                        )
                        Text(
                            text = item[page].title,
                            modifier = Modifier.padding(top = 50.dp),
                            color = Color.Black,
                            style = MaterialTheme.typography.headlineSmall,
                        )

                        Text(
                            text = item[page].desc,
                            modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp),
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                PagerIndicator(item.size, pagerState.currentPage)
            }
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                BottomSection(pagerState.currentPage)
            }
        }
    }

    @ExperimentalPagerApi
    @Composable
    fun RememberPagerState(
        @IntRange(from = 0) pageCount: Int,
        @IntRange(from = 0) initialPage: Int = 0,
        @FloatRange(from = 0.0, to = 1.0) initialPageOffset: Float = 0f,
        @IntRange(from = 1) initialOffscreenLimit: Int = 1,
        infiniteLoop: Boolean = false
    ): PagerState = rememberSaveable(
        saver = PagerState.Saver
    ) {
        PagerState(
            pageCount = pageCount,
            currentPage = initialPage,
            currentPageOffset = initialPageOffset,
            offscreenLimit = initialOffscreenLimit,
            infiniteLoop = infiniteLoop
        )
    }

    @Composable
    fun PagerIndicator(
        size: Int,
        currentPage: Int
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(top = 60.dp)
        ) {
            repeat(size) {
                Indicator(isSelected = it == currentPage)
            }
        }
    }

    @Composable
    fun Indicator(isSelected: Boolean) {
        val width = animateDpAsState(targetValue = if (isSelected) 25.dp else 10.dp)

        Box(
            modifier = Modifier
                .padding(1.dp)
                .height(10.dp)
                .width(width.value)
                .clip(CircleShape)
                .background(
                    if (isSelected) RedLight else Grey300.copy(alpha = 0.5f)
                )
        )
    }

    @Composable
    fun BottomSection(currentPager: Int) {

        val context = LocalContext.current

        Row(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = if (currentPager != 3) Arrangement.SpaceBetween else Arrangement.Center
        ) {
            if (currentPager == 3) {
                OutlinedButton(
                    onClick = {
                        Toast.makeText(context, "successful try :)", Toast.LENGTH_SHORT).show()
                    },
                    shape = RoundedCornerShape(50),
                ) {
                    Text(
                        text = "Get Started",
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 40.dp),
                        color = Grey900
                    )
                }
            } else {
                SkipNextButton(text = "Skip", modifier = Modifier.padding(start = 20.dp))
                SkipNextButton(text = "Next", modifier = Modifier.padding(end = 20.dp))
            }
        }
    }

    @Composable
    fun SkipNextButton(text: String, modifier: Modifier) {
        Text(
            text = text,
            color = Color.Black,
            modifier = modifier,
            fontSize = 18.sp,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }

}
