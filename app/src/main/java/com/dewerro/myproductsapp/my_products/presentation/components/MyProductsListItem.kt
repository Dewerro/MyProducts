package com.dewerro.myproductsapp.my_products.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dewerro.myproductsapp.ui.theme.Comfortaa


@ExperimentalMaterialApi
@Composable
fun MyProductsListItem(
    elementName: String,
    elementDescription: String,
    modifier: Modifier
) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180F else 0F
    )

    Card(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(17.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = elementName,
                    modifier = Modifier
                        .width(250.dp)
                        .padding(start = 5.dp),
                    color = MaterialTheme.colors.secondary,
                    fontFamily = Comfortaa,
                    fontWeight = FontWeight.Normal,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Start
                )
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow",
                        tint = MaterialTheme.colors.secondary
                    )
                }
            }
            if (expandedState) {
                Text(
                    text = if (elementDescription.isEmpty()) "Описание" else elementDescription,
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier.padding(start = 5.dp, bottom = 5.dp),
                    fontFamily = Comfortaa,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}