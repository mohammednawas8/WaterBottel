package com.example.waterbottel

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WatterBottle(
    modifier: Modifier = Modifier,
    totalWaterAmount: Int,
    unit: String,
    usedWaterAmount: Int,
    waterWavesColor: Color = Color(0xff279EFF),
    bottleColor: Color = Color.White,
    capColor: Color = Color(0xFF0065B9)
) {

    val waterPercentage = animateFloatAsState(
        targetValue = (usedWaterAmount.toFloat() / totalWaterAmount.toFloat()),
        label = "Water Waves animation",
        animationSpec = tween(durationMillis = 1000)
    ).value

    val usedWaterAmountAnimation = animateIntAsState(
        targetValue = usedWaterAmount,
        label = "Used water amount animation",
        animationSpec = tween(durationMillis = 1000)
    ).value

    Box(
        modifier = modifier
            .width(200.dp)
            .height(600.dp)
    ) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            val capWidth = size.width * 0.55f
            val capHeight = size.height * 0.13f

            //Draw the bottle body
            val bodyPath = Path().apply {
                moveTo(width * 0.3f, height * 0.1f)
                lineTo(width * 0.3f, height * 0.2f)
                quadraticBezierTo(
                    0f, height * 0.3f, // The pulling point
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
            clipPath(
                path = bodyPath
            ) {
                // Draw the color of the bottle
                drawRect(
                    color = bottleColor,
                    size = size,
                    topLeft = Offset(0f, 0f)
                )

                //Draw the water waves
                val waterWavesYPosition = (1 - waterPercentage) * size.height

                val wavesPath = Path().apply {
                    moveTo(
                        x = 0f,
                        y = waterWavesYPosition
                    )
                    lineTo(
                        x = size.width,
                        y = waterWavesYPosition
                    )
                    lineTo(
                        x = size.width,
                        y = size.height
                    )
                    lineTo(
                        x = 0f,
                        y = size.height
                    )
                    close()
                }
                drawPath(
                    path = wavesPath,
                    color = waterWavesColor,
                )
            }

            //Draw the bottle cap
            drawRoundRect(
                color = capColor,
                size = Size(capWidth, capHeight),
                topLeft = Offset(size.width / 2 - capWidth / 2f, 0f),
                cornerRadius = CornerRadius(45f, 45f)
            )


        }
        val text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = if (waterPercentage > 0.5f) bottleColor else waterWavesColor,
                    fontSize = 44.sp
                )
            ) {
                append(usedWaterAmountAnimation.toString())
            }
            withStyle(
                style = SpanStyle(
                    color = if (waterPercentage > 0.5f) bottleColor else waterWavesColor,
                    fontSize = 22.sp
                )
            ) {
                append(" ")
                append(unit)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text)
        }
    }
}


@Preview
@Composable
fun WaterBottlePreview() {
    WatterBottle(
        totalWaterAmount = 2500,
        unit = "ml",
        usedWaterAmount = 120
    )
}
