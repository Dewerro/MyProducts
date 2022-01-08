package com.dewerro.myproductsapp.my_products.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed class Dimensions {
    object Width : Dimensions()
    object Height : Dimensions()

    sealed class DimensionsOperator {
        object LessThan : DimensionsOperator()
        object GreaterThan : DimensionsOperator()
    }

    class DimensionComparator(
        val operator: DimensionsOperator,
        private val dimension: Dimensions,
        val value: Dp
    ) {
        fun compare(screenWidth: Dp, screenHeight: Dp): Boolean {
            return when (dimension) {
                is Width -> {
                    when (operator) {
                        is DimensionsOperator.LessThan -> screenWidth < value
                        is DimensionsOperator.GreaterThan -> screenWidth > value
                    }
                }
                is Height -> {
                    when (operator) {
                        DimensionsOperator.LessThan -> screenHeight < value
                        DimensionsOperator.GreaterThan -> screenHeight > value
                    }
                }
            }
        }
    }
}

@Composable
fun MediaQuery(comparator: Dimensions.DimensionComparator, content: @Composable () -> Unit) {
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels.dp /
            LocalDensity.current.density
    val screenHeight = LocalContext.current.resources.displayMetrics.heightPixels.dp /
            LocalDensity.current.density
    if (comparator.compare(screenWidth, screenHeight)) {
        content()
    }
}

infix fun Dimensions.lessThan(value: Dp): Dimensions.DimensionComparator {
    return Dimensions.DimensionComparator(
        operator = Dimensions.DimensionsOperator.LessThan,
        dimension = this,
        value = value
    )
}

infix fun Dimensions.greaterThan(value: Dp): Dimensions.DimensionComparator {
    return Dimensions.DimensionComparator(
        operator = Dimensions.DimensionsOperator.GreaterThan,
        dimension = this,
        value = value
    )
}