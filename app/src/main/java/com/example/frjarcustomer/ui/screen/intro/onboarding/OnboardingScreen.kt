package com.example.frjarcustomer.ui.screen.intro.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.frjarcustomer.R
import com.example.frjarcustomer.appstate.LocalPaddingValues
import com.example.frjarcustomer.appstate.resourceString
import com.example.frjarcustomer.data.remote.model.responseMaper.onboarding.OnboardingData
import com.example.frjarcustomer.data.remote.model.responseMaper.onboarding.OnboardingPage
import com.example.frjarcustomer.image.CoilImage
import com.example.frjarcustomer.ui.components.GenericButton
import com.example.frjarcustomer.ui.components.GenericText
import com.example.frjarcustomer.ui.theme.ButtonPrimary
import com.example.frjarcustomer.ui.theme.ButtonSecondary
import com.example.frjarcustomer.ui.theme.InterFontFamily
import com.example.frjarcustomer.ui.theme.Screen_background_Root
import com.example.frjarcustomer.ui.theme.TextOnAccent
import com.example.frjarcustomer.ui.theme.TextOnLightgray
import com.example.frjarcustomer.ui.theme.TextPrimary
import com.example.frjarcustomer.ui.theme.TextSecondary
import com.example.frjarcustomer.ui.theme.TextTertiary
import com.example.frjarcustomer.ui.theme.White
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import okhttp3.Dispatcher

@Composable
fun OnboardingPageContent(
    pageNumber: Int,
    onboardingPages: OnboardingData
) {
    var animateImage by remember(pageNumber) { mutableStateOf(false) }
    var animateTitle by remember(pageNumber) { mutableStateOf(false) }
    var animateDescription by remember(pageNumber) { mutableStateOf(false) }

    val safeAreaPadding = LocalPaddingValues.current


    LaunchedEffect(pageNumber) {
        withContext(Dispatchers.Main) {
            animateImage = false
            animateTitle = false
            animateDescription = false

            animateImage = true
            delay(500)
            animateTitle = true
            delay(500)
            animateDescription = true
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .animateContentSize()
            .padding(top = safeAreaPadding.calculateTopPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(100.sdp))
        onboardingPages.pages?.getOrNull(pageNumber)?.imageRes?.let {image->
            AnimatedVisibility(
                visible = animateImage,
                enter = slideInVertically(
                    animationSpec = spring(stiffness = Spring.StiffnessVeryLow),
                    initialOffsetY = { -it }
                ) + fadeIn(tween(1000))
            ) {
                CoilImage(
                    url = image,

                    contentDescription = null,
                    modifier = Modifier
                        .height(200.sdp)
                        .padding(horizontal = 12.sdp)
                )
            }

        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(50.sdp))
            onboardingPages.pages?.getOrNull(pageNumber)?.title?.let { title ->

                AnimatedVisibility(
                    visible = animateTitle,
                    enter = slideInHorizontally(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessVeryLow
                        ),
                        initialOffsetX = { -it }
                    ) + fadeIn(tween(1000))
                ) {
                    GenericText(
                        text = title,
                        color = TextPrimary,
                        fontSize = 18.ssp,
                        fontWeight = FontWeight.W600,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.sdp)
                    )


                }
            }
            Spacer(modifier = Modifier.height(12.sdp))
            onboardingPages.pages?.getOrNull(pageNumber)?.description?.let { description ->
                AnimatedVisibility(
                    visible = animateDescription,
                    enter = slideInHorizontally(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessVeryLow
                        ),
                        initialOffsetX = { -it }
                    ) + fadeIn(tween(1000))
                ) {

                    GenericText(
                        text = description,
                        color = TextSecondary,
                        fontSize = 11.ssp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(horizontal = 40.sdp)
                            .fillMaxWidth()
                    )
                }
            }

        }
    }
}

@Composable
fun CarouselScreen(
    modifier: Modifier = Modifier,
    onboardingPages: OnboardingData,
    onFinish: () -> Unit = {}
) {
    val pagerState = rememberPagerState(pageCount = { 3 })

    val safeAreaPadding = LocalPaddingValues.current

    val isLastPage = pagerState.currentPage == onboardingPages.pages?.lastIndex
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(White)
            .padding(bottom = safeAreaPadding.calculateBottomPadding())
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .fillMaxSize(),
            pageSize = PageSize.Fill
        ) { page ->
            OnboardingPageContent(page, onboardingPages)
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .systemBarsPadding(),
//                .padding(horizontal = 24.sdp, vertical = 20.sdp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            GenericButton(
                onClick = {
//                    if (isLastPage)
                    onFinish()
//                    else scope.launch {
//                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
//                    }
                },
                backgroundColor = if (isLastPage) ButtonPrimary else Color.Transparent,
                contentColor = TextOnAccent,
                elevation = 0.dp,
                modifier = Modifier
                    .padding(horizontal = 68.sdp)
                    .fillMaxWidth()
                    .height(35.sdp)
            ) {
                GenericText(
                    text = if (isLastPage) resourceString(R.string.get_started) else resourceString(
                        R.string.skip
                    ),
                    color = if (isLastPage) TextPrimary else TextTertiary,
                    fontSize = 12.ssp,
                    fontWeight = if (isLastPage) FontWeight.W500 else null,
                )
            }
            Spacer(modifier = Modifier.height(36.sdp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                onboardingPages.pages?.forEachIndexed { index, _ ->

                    val indicatorColor = remember(pagerState.currentPage) {
                        if (pagerState.currentPage == index)
                            ButtonPrimary
                        else
                            TextTertiary.copy(0.20f)
                    }
                    key(index) {
                        Box(
                            modifier = Modifier
                                .padding(end = 6.sdp)
                                .size(9.sdp)
                                .drawBehind {
                                    drawCircle(
                                        color = indicatorColor,
                                        radius = size.minDimension / 2
                                    )
                                }
                        )

                    }

                }
            }


        }
    }
}

