package com.alebrije_estudios.metabolique

import android.app.LocaleManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.core.os.LocaleListCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.alebrije_estudios.metabolique.login.ui.HeaderLogo
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.absoluteValue

const val EMAIL = "ayuda@vidasana.com"
const val PHONE = "+52(81) 1234 5678"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        val sharedPref = getSharedPreferences("User",MODE_PRIVATE)
        if(sharedPref.getString("token", "")?.contains(".")!!){
            val newIntent = Intent(this, DashboardActivity::class.java)
            startActivity(newIntent)
            finish()
            return
        }
        val intent = Intent(this, LoginActivity::class.java)
        var show = false
        enableEdgeToEdge()
        setContent {
            localeSelection(LocalContext.current, Locale("es").toLanguageTag())
            //window.navigationBarColor = if(isSystemInDarkTheme()) Color.BLACK else Color.TRANSPARENT
            Scaffold(Modifier.fillMaxSize(), floatingActionButton = {
                FloatingActionButton(onClick = {
                    startActivity(intent)
                    finish()
                },
                    shape = CircleShape
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = "")
                }
            }) { innerPadding ->
                Column(
                    modifier = Modifier.fillMaxWidth().padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                HeaderLogo(modifier = Modifier.padding(top = 64.dp))
                    AnimatedVisibility(
                        visible = show,

                        ) {
                        CarouselScreen()
                    }
                }

            }
        }
        screenSplash.setKeepOnScreenCondition {!show}
        Thread.sleep(1000)
        show = true
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun CarouselScreen() {
        val sliderList = listOf(
            "https://picsum.photos/id/17/720/1152",
            "https://picsum.photos/id/22/720/1152",
            "https://picsum.photos/id/29/720/1152",
            "https://picsum.photos/id/31/720/1152",
            "https://picsum.photos/id/73/720/1152"
        )
        val pagerState = rememberPagerState(initialPage = 2){sliderList.size}
        val fling = PagerDefaults.flingBehavior(
            state = pagerState,
            pagerSnapDistance = PagerSnapDistance.atMost(5)
        )
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 32.dp),
            modifier = Modifier.fillMaxSize(),
            flingBehavior = fling
        ) { page ->
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue
                    lerp(
                        start = 0.50f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
                    alpha = lerp(0.5f, 1f,1f - pageOffset.coerceIn(0f,1f))
                }
            ) {
                AsyncImage(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(sliderList[page])
                        .crossfade(true)
                        .scale(Scale.FIT)
                        .build(),
                    modifier = Modifier.width(350.dp),
                    contentDescription = "",

                    )
            }
//            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
//
//            }
        }
    }
}


fun calculateCurrentOffsetForPage(page: Int, currentPage: Int, currentPageOffset: Float): Float {
    return (currentPage - page) + currentPageOffset
}

fun localeSelection(context: Context, localeTag: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.getSystemService(LocaleManager::class.java).applicationLocales =
            LocaleList.forLanguageTags(localeTag)
    } else {
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(localeTag)
        )
    }
}
