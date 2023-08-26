package com.example.waterbottel

import androidx.annotation.FloatRange
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.waterbottel.ui.theme.WaterBottelTheme
import java.lang.Math.abs

val Blue700 = Color(0xff279EFF)
val Blue500 = Color(0xff40F8FF)

@Composable
fun WaterBottleOld(
    modifier: Modifier = Modifier,
    emptyBottleColor: Color = Color.White,
    darkWavesColor: Color = Blue700,
    lightWavesColor: Color = Blue500,
    lightColor: Boolean = false,
    drinkAmount: Int,
    @FloatRange(from = 0.0, to = 1.0) waterPercentage: Float,
) {
    Box(modifier = modifier, contentAlignment = Alignment.BottomCenter) {
        var animation by remember {
            mutableStateOf(false)
        }

        var text = animateIntAsState(
            targetValue = if (animation) drinkAmount else 0,
            tween(durationMillis = 1000)
        )
        val isPercentageGreaterThan1 = waterPercentage > 1
        val afterMathPercentage =
            animateFloatAsState(
                targetValue =
                if (animation && !isPercentageGreaterThan1) 1 - waterPercentage
                else if (isPercentageGreaterThan1) 0f
                else 1f,

                tween(durationMillis = 1000)
            ).value

        LaunchedEffect(key1 = true) {
            animation = true
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            val bottlePath = Path().apply {
                //Bottle body
                moveTo(width * 0.3f, height * 0.1f)
                lineTo(width * 0.3f, height * 0.2f)
                quadraticBezierTo(
                    0f, height * 0.3f,
                    0f, height * 0.4f
                )
                lineTo(0f, height * 0.95f)
                quadraticBezierTo(
                    0f, height,
                    width * 0.05f, height
                )

                lineTo(width * 0.95f, height)
                quadraticBezierTo(
                    width, height,
                    width, height * 0.95f
                )
                lineTo(width, height * 0.4f)
                quadraticBezierTo(
                    width, height * 0.3f,
                    width * 0.7f, height * 0.2f
                )
                lineTo(width * 0.7f, height * 0.2f)
                lineTo(width * 0.7f, height * 0.1f)

                close()
            }

            drawPath(
                path = bottlePath,
                color = emptyBottleColor
            )

//            Light Water Waves
            if (lightColor)
                clipPath(path = bottlePath) {
                    val point0 = Offset(0f + 35f, height)
                    val point1 = Offset(0f + 35f, height * afterMathPercentage + 10f)
                    val point2 = Offset(width * 0.1f + 38f, height * afterMathPercentage)
                    val point3 = Offset(width * 0.2f + 38f, height * afterMathPercentage + 50f)
                    val point4 = Offset(width * 0.3f + 38f, height * afterMathPercentage + 10f)
                    val point5 = Offset(width * 0.5f + 38f, height * afterMathPercentage + 75f)
                    val point6 = Offset(width * 0.7f + 38f, height * afterMathPercentage - 30f)
                    val point7 = Offset(width * 0.9f + 38f, height * afterMathPercentage + 20)
                    val point8 = Offset(width * 1.1f + 38f, height * afterMathPercentage - 10f)
                    val waterPath = Path().apply {
                        moveTo(point0.x, point0.y)
                        fromToQuadBezier(point0, point1)
                        lineTo(point1.x, point1.y)
                        fromToQuadBezier(point1, point2)
                        fromToQuadBezier(point2, point3)
                        fromToQuadBezier(point3, point4)
                        fromToQuadBezier(point4, point5)
                        fromToQuadBezier(point5, point6)
                        fromToQuadBezier(point6, point7)
                        fromToQuadBezier(point7, point8)
                        close()
                    }
                    drawPath(
                        path = waterPath,
                        color = lightWavesColor,
                    )
                }

            //Dark Water waves
            clipPath(path = bottlePath) {
                val point0 = Offset(0f, height)
                val point1 = Offset(0f, height * afterMathPercentage + 10f)
                val point2 = Offset(width * 0.1f, height * afterMathPercentage)
                val point3 = Offset(width * 0.2f, height * afterMathPercentage + 50f)
                val point4 = Offset(width * 0.3f, height * afterMathPercentage + 10f)
                val point5 = Offset(width * 0.5f, height * afterMathPercentage + 75f)
                val point6 = Offset(width * 0.7f, height * afterMathPercentage - 10f)
                val point7 = Offset(width * 0.9f, height * afterMathPercentage + 20)
                val point8 = Offset(width * 1.1f, height * afterMathPercentage - 10f)
                val waterPath = Path().apply {
                    moveTo(point0.x, point0.y)
                    fromToQuadBezier(point0, point1)
                    lineTo(point1.x, point1.y)
                    fromToQuadBezier(point1, point2)
                    fromToQuadBezier(point2, point3)
                    fromToQuadBezier(point3, point4)
                    fromToQuadBezier(point4, point5)
                    fromToQuadBezier(point5, point6)
                    fromToQuadBezier(point6, point7)
                    fromToQuadBezier(point7, point8)
                    lineTo(width, height)
                    close()
                }
                drawPath(
                    path = waterPath,
                    color = darkWavesColor,
                )
            }

            //Bottle cap
            drawRoundRect(
                color = Color(0xFF70BDF2),
                size = Size(size.width * 0.55f, size.height * 0.13f),
                topLeft = Offset(size.width * 0.23f, size.height * 0.03f),
                cornerRadius = CornerRadius(45f, 45f)
            )

        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text.value.toString(),
                style = MaterialTheme.typography.displayMedium,
                color = if (waterPercentage >= 0.5f) Color.White else darkWavesColor,
                fontSize = 40.sp,
            )

            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "ml",
                style = MaterialTheme.typography.displayMedium,
                color = if (waterPercentage >= 0.5f) Color.White else darkWavesColor,
                fontSize = 22.sp,
                modifier = Modifier
                    .padding(top = 10.dp)
            )
        }
    }
}

fun Path.fromToQuadBezier(from: Offset, to: Offset) {
    quadraticBezierTo(
        from.x, from.y,
        abs(from.x + to.x) / 2f,
        abs(from.y + to.y) / 2
    )
}

@Preview
@Composable
fun PreviewWatterBottle() {
    WaterBottelTheme {
        WaterBottleOld(
            waterPercentage = 0.8f, modifier = Modifier
                .width(136.dp)
                .height(330.dp),
            drinkAmount = 1000
        )
    }

}