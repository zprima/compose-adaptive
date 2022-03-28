package com.example.screensizelayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.screensizelayout.ui.theme.ScreenSizeLayoutTheme
import com.example.screensizelayout.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreenSizeLayoutTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val windowSizeClass = rememberWindowSizeClass()

                    if (windowSizeClass == WindowSizeClass.Compact) {
                        OnePanel()
                    } else {
                        TwoPanel()
                    }
                }
            }
        }
    }
}

@Composable
fun OnePanel() {
    LazyColumn(modifier = Modifier.fillMaxSize()) {

        items(20) {
            Text(
                "L1: $it",
                fontSize = 26.sp,
                modifier = Modifier
                    .background(Color.Green)
                    .fillMaxWidth()
            )
        }

        items(20) {
            Text(
                "L2: $it",
                fontSize = 26.sp,
                modifier = Modifier
                    .background(Color.Blue)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun TwoPanel() {
    Row(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier
            .fillMaxHeight()
            .weight(1f)) {

            items(20) {
                Text(
                    "L1: $it",
                    fontSize = 26.sp,
                    modifier = Modifier
                        .background(Color.Green)
                        .fillMaxWidth()
                )
            }
        }

        LazyColumn(modifier = Modifier
            .fillMaxHeight()
            .weight(1f)) {

            items(20) {
                Text(
                    "L2: $it",
                    fontSize = 26.sp,
                    modifier = Modifier
                        .background(Color.Blue)
                        .fillMaxWidth()
                )
            }
        }
    }
}
