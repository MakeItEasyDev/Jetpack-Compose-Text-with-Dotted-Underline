package com.jetpack.textarounddottedshape

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.jetpack.textarounddottedshape.ui.theme.TextAroundDottedShapeTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var layout by remember { mutableStateOf<TextLayoutResult?>(null) }
            TextAroundDottedShapeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Text with Dotted Shape",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        )

                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Dotted Line",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Box(
                                modifier = Modifier
                                    .height(3.dp)
                                    .fillMaxWidth()
                                    .background(Color.Green, shape = DottedShape(step = 20.dp))
                            )
                            Spacer(modifier = Modifier.height(50.dp))
                            Text(
                                text = "Text with dotted underline",
                                style = MaterialTheme.typography.h3,
                                color = Color.Black,
                                onTextLayout = {
                                    layout = it
                                },
                                modifier = Modifier.drawBehind {
                                    layout?.let {
                                        val thickness = 5f
                                        val dashPath = 15f
                                        val spacingExtra = 4f
                                        val offsetY = 6f

                                        for (i in 0 until it.lineCount) {
                                            drawPath(
                                                path = Path().apply {
                                                    moveTo(it.getLineLeft(i), it.getLineBottom(i) - spacingExtra + offsetY)
                                                    lineTo(it.getLineRight(i), it.getLineBottom(i) - spacingExtra + offsetY)
                                                },
                                                Color.Red,
                                                style = Stroke(
                                                    width = thickness,
                                                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashPath, dashPath), 0f)
                                                )
                                            )
                                        }
                                    }
                                },
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

private data class DottedShape(
    val step: Dp
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ) = Outline.Generic(Path().apply {
        val stepPx = with(density) { step.toPx() }
        val stepCount = (size.width / stepPx).roundToInt()
        val actualStep = size.width / stepCount
        val dotSize = Size(width = actualStep / 2, height = size.height)
        for (i in 0 until stepCount) {
            addRect(
                Rect(
                    offset = Offset(x = i * actualStep, y = 0f),
                    size = dotSize
                )
            )
        }
        close()
    })
}




















