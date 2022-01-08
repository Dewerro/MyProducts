package com.dewerro.myproductsapp.my_products.presentation.product_selected

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.dewerro.myproductsapp.R
import com.dewerro.myproductsapp.my_products.domain.model.Product
import com.dewerro.myproductsapp.my_products.presentation.util.Dimensions
import com.dewerro.myproductsapp.my_products.presentation.util.greaterThan
import com.dewerro.myproductsapp.my_products.presentation.util.mediaQuery
import com.dewerro.myproductsapp.ui.theme.Comfortaa
import com.google.accompanist.insets.LocalWindowInsets
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun ProductSelectedScreen(
    product: Product,
    viewModel: ProductSelectedViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    val defaultName = product.name
    val id by remember { mutableStateOf(product.id) }
    var name by remember { mutableStateOf(product.name) }
    var category by remember { mutableStateOf(product.category) }
    var description by remember { mutableStateOf(product.description) }

    val focusManager = LocalFocusManager.current

    val scope = rememberCoroutineScope()

    // Keyboard
    val insets = LocalWindowInsets.current
    val imeBottom = with(LocalDensity.current) { insets.ime.layoutInsets.bottom.toDp() }

    // Name OutlinedTextField
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(targetValue = if (expandedState) 180F else 0F)
    var textFieldCornersState by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    // Description OutlinedTextField
    var textFieldAlpha by remember { mutableStateOf(0.7F) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, bottom = 50.dp)
    ) {
        Image(
            painter = painterResource(
                if (isSystemInDarkTheme()) R.drawable.app_product_selected_screen_background_dark
                else R.drawable.app_product_selected_screen_background_light
            ),
            contentDescription = "Background squares",
            modifier = Modifier
                .width(315.dp)
                .height(467.dp)
                .constrainAs(createRef()) {
                    centerTo(parent)
                }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .mediaQuery(
                    comparator = Dimensions.Width greaterThan 320.dp,
                    modifier = Modifier.padding(top = 50.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        if (name.isNotEmpty()) {
                            viewModel.onEvent(
                                ProductSelectedEvent.UpdateProduct(
                                    id = id!!,
                                    name = name,
                                    category = category,
                                    description = description
                                )
                            )
                            isError = false
                        } else {
                            viewModel.onEvent(
                                ProductSelectedEvent.UpdateProduct(
                                    id = id!!,
                                    name = defaultName,
                                    category = category,
                                    description = description
                                )
                            )
                            isError = true
                        }
                    },
                    modifier = Modifier.width(300.dp),
                    isError = isError,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = MaterialTheme.colors.background,
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = MaterialTheme.colors.secondary.copy(alpha = 0.24F),
                        cursorColor = MaterialTheme.colors.secondary
                    ),
                    singleLine = true,
                    label = {
                        Text(
                            text = "Название товара",
                            color = MaterialTheme.colors.secondary,
                            fontFamily = Comfortaa,
                            fontWeight = FontWeight.Normal
                        )
                    },
                    shape = if (!textFieldCornersState) RoundedCornerShape(22.dp)
                    else RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
                    trailingIcon = {
                        IconButton(
                            modifier = Modifier.rotate(rotationState),
                            onClick = {
                                expandedState = !expandedState
                                scope.launch {
                                    delay(100)
                                    textFieldCornersState = !textFieldCornersState
                                }
                            }) {
                            Icon(
                                painter = painterResource(R.drawable.app_drop_down_arrow),
                                contentDescription = "Drop-Down Arrow",
                                tint = MaterialTheme.colors.secondary
                            )
                        }
                    }
                )
                MaterialTheme(
                    shapes = MaterialTheme.shapes.copy(
                        medium = RoundedCornerShape(
                            bottomStart = 22.dp,
                            bottomEnd = 22.dp
                        )
                    )
                ) {
                    DropdownMenu(
                        expanded = expandedState,
                        onDismissRequest = {
                            expandedState = false
                            scope.launch {
                                delay(100)
                                textFieldCornersState = false
                            }
                        },
                        modifier = Modifier
                            .width(300.dp)
                            .background(MaterialTheme.colors.background)
                            .border(
                                1.dp,
                                MaterialTheme.colors.secondary,
                                RoundedCornerShape(bottomStart = 22.dp, bottomEnd = 22.dp)
                            )
                    ) {
                        state.categories.forEach { _category ->
                            DropdownMenuItem(onClick = {
                                category = _category.name
                                viewModel.onEvent(
                                    ProductSelectedEvent.UpdateProduct(
                                        id = id!!,
                                        name = name,
                                        category = category,
                                        description = description
                                    )
                                )
                                scope.launch {
                                    delay(150)
                                    expandedState = false
                                    delay(100)
                                    textFieldCornersState = false
                                }
                            }) {
                                Text(
                                    text = _category.name,
                                    fontFamily = Comfortaa,
                                    color = MaterialTheme.colors.secondary
                                )
                            }
                        }
                    }
                }
            }
            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                    viewModel.onEvent(
                        ProductSelectedEvent.UpdateProduct(
                            id = id!!,
                            name = name,
                            category = category,
                            description = description
                        )
                    )
                },
                modifier = Modifier
                    .width(300.dp)
                    .alpha(textFieldAlpha)
                    .padding(bottom = imeBottom)
                    .onFocusChanged {
                        if (it.isFocused) {
                            scope.launch {
                                textFieldAlpha = 1F
                            }
                        }
                    }
                    .focusTarget(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = MaterialTheme.colors.background,
                    focusedBorderColor = MaterialTheme.colors.secondary,
                    unfocusedBorderColor = MaterialTheme.colors.secondary.copy(alpha = 0.24F),
                    cursorColor = MaterialTheme.colors.secondary
                ),
                label = {
                    Text(
                        text = "Описание товара",
                        color = MaterialTheme.colors.secondary,
                        fontFamily = Comfortaa,
                        fontWeight = FontWeight.Normal
                    )
                },
                shape = RoundedCornerShape(22.dp),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                })
            )
        }
    }
}