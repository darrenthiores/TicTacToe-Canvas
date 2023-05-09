package com.dev.pl_tictactoe

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun TicTacToe(
    modifier: Modifier = Modifier,
    ticTacToeSize: Float = 600f
) {
    var center by remember {
        mutableStateOf(Offset.Zero)
    }

    val horizontalPath = Path().apply {
        lineTo(ticTacToeSize, 0f)
    }

    val verticalPath = Path().apply {
        lineTo(0f, ticTacToeSize)
    }

    val circle = Path().apply {
        addOval(
            Rect(
                center = Offset(
                    ticTacToeSize/3f/2f,
                    ticTacToeSize/3f/2f
                ),
                radius = ticTacToeSize/3f/2f,
            )
        )
    }

    val xPath = Path().apply {
        val size = ticTacToeSize/3f

        lineTo(size, size)

        moveTo(0f, size)

        lineTo(size, 0f)
    }

    val filledBlank = remember {
        mutableStateListOf<Type?>(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )
    }

    var rect by remember {
        mutableStateOf<List<Rect>>(emptyList())
    }

    Canvas(
        modifier = modifier
            .pointerInput(true) {
                detectTapGestures { clickOffset ->
                    Log.d("TAP", "TAP")
                    rect.forEachIndexed { index, _rect ->
                        if(_rect.contains(clickOffset)) {
                            Log.d("TAP", "TAP INSIDE")
                            val count = filledBlank.count { it != null }
                            filledBlank[index] = if(count%2 == 0) Type.O else Type.X
                            Log.d("LIST", "${filledBlank.toList()}")
                        }
                    }
                }
            }
    ) {
        center = this.center

        rect = List(9) {
            Rect(
                offset = getDrawLocation(it, center, ticTacToeSize),
                size = Size(ticTacToeSize/3f, ticTacToeSize/3f)
            )
        }

        translate(
            left = center.x - ((ticTacToeSize/3f)/2f),
            top = center.y - ticTacToeSize/2f
        ) {
            drawPath(
                path = verticalPath,
                color = Color.Black,
                style = Stroke(
                    width = 5.dp.toPx()
                )
            )
        }

        translate(
            left = center.x + ((ticTacToeSize/3f) / 2f),
            top = center.y - ticTacToeSize/2f
        ) {
            drawPath(
                path = verticalPath,
                color = Color.Black,
                style = Stroke(
                    width = 5.dp.toPx()
                )
            )
        }

        translate(
            left = center.x - ticTacToeSize/2f,
            top = center.y - ((ticTacToeSize/3f) / 2f)
        ) {
            drawPath(
                path = horizontalPath,
                color = Color.Black,
                style = Stroke(
                    width = 5.dp.toPx()
                )
            )
        }

        translate(
            left = center.x - ticTacToeSize/2f,
            top = center.y + ((ticTacToeSize/3f)/2f)
        ) {
            drawPath(
                path = horizontalPath,
                color = Color.Black,
                style = Stroke(
                    width = 5.dp.toPx()
                )
            )
        }

        filledBlank.forEachIndexed { index, type ->
            Log.d("LIST", "RUN")
            val drawLocation = getDrawLocation(index, center, ticTacToeSize)
            if (drawLocation != Offset.Zero && type!=null) {
                if(type is Type.X) {
                    Log.d("DRAW", "X")
                    translate(
                        left = drawLocation.x,
                        top = drawLocation.y
                    ) {
                        drawPath(
                            path = xPath,
                            color = Color.Green,
                            style = Stroke(
                                width = 5.dp.toPx()
                            )
                        )
                    }
                }

                if(type is Type.O) {
                    Log.d("DRAW", "O")
                    translate(
                        left = drawLocation.x,
                        top = drawLocation.y
                    ) {
                        drawPath(
                            path = circle,
                            color = Color.Red,
                            style = Stroke(
                                width = 5.dp.toPx()
                            )
                        )
                    }
                }
            }
        }
    }
}

private fun getDrawLocation(
    index: Int,
    center: Offset,
    ticTacToeSize: Float
) = when(index + 1) {
        1 -> {
            Offset(
                x = center.x - ticTacToeSize/2f,
                y = center.y - ticTacToeSize/2f
            )
        }
        2 -> {
            Offset(
                x = center.x - ((ticTacToeSize/3f)/2f),
                y = center.y - ticTacToeSize/2f
            )
        }
        3 -> {
            Offset(
                x = center.x + ((ticTacToeSize/3f)/2f),
                y = center.y - ticTacToeSize/2f
            )
        }
        4 -> {
            Offset(
                x = center.x - ticTacToeSize/2f,
                y = center.y - ((ticTacToeSize/3f)/2f)
            )
        }
        5 -> {
            Offset(
                x = center.x - ((ticTacToeSize/3f)/2f),
                y = center.y - ((ticTacToeSize/3f)/2f)
            )
        }
        6 -> {
            Offset(
                x = center.x + ((ticTacToeSize/3f)/2f),
                y = center.y - ((ticTacToeSize/3f)/2f)
            )
        }
        7 -> {
            Offset(
                x = center.x - ticTacToeSize/2f,
                y = center.y + ((ticTacToeSize/3f)/2f)
            )
        }
        8 -> {
            Offset(
                x = center.x - ((ticTacToeSize/3f)/2f),
                y = center.y + ((ticTacToeSize/3f)/2f)
            )
        }
        9 -> {
            Offset(
                x = center.x + ((ticTacToeSize/3f)/2f),
                y = center.y + ((ticTacToeSize/3f)/2f)
            )
        }
        else -> {
            Offset.Zero
        }
    }