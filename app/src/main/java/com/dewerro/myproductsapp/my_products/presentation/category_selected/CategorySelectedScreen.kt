package com.dewerro.myproductsapp.my_products.presentation.category_selected

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dewerro.myproductsapp.R
import com.dewerro.myproductsapp.my_products.domain.model.Category
import com.dewerro.myproductsapp.my_products.presentation.components.MyProductsListItem
import com.dewerro.myproductsapp.my_products.presentation.product_list.components.ProductsDialog
import com.dewerro.myproductsapp.my_products.presentation.util.Dimensions
import com.dewerro.myproductsapp.my_products.presentation.util.Screen
import com.dewerro.myproductsapp.my_products.presentation.util.greaterThan
import com.dewerro.myproductsapp.my_products.presentation.util.mediaQuery
import com.dewerro.myproductsapp.ui.theme.Comfortaa
import com.dewerro.myproductsapp.ui.theme.Grey
import com.dewerro.myproductsapp.ui.theme.SoftPurple
import com.google.accompanist.insets.LocalWindowInsets
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun CategorySelectedScreen(
    navController: NavHostController,
    category: Category,
    viewModel: CategorySelectedViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    val defaultName = category.name
    val id by remember { mutableStateOf(category.id) }
    var name by remember { mutableStateOf(category.name) }
    var description by remember { mutableStateOf(category.description) }

    val focusManager = LocalFocusManager.current

    // Keyboard
    val insets = LocalWindowInsets.current
    val imeBottom = with(LocalDensity.current) { insets.ime.layoutInsets.bottom.toDp() }

    // Snackbar
    var recentlyDeletedProduct by remember { mutableStateOf(state.recentlyDeletedProduct) }
    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    // Name OutlinedTextField
    var isError by remember { mutableStateOf(false) }

    // Description OutlinedTextField
    var textFieldAlpha by remember { mutableStateOf(0.7F) }

    // Dialog
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    ProductsDialog(showDialog, setShowDialog, name)

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, bottom = 50.dp)
    ) {
        Image(
            painter = painterResource(
                if (isSystemInDarkTheme()) R.drawable.app_category_selected_screen_background_dark
                else R.drawable.app_category_selected_screen_background_light
            ),
            contentDescription = "Background",
            modifier = Modifier
                .width(417.dp)
                .height(722.dp)
                .constrainAs(createRef()) {
                    centerTo(parent)
                })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .mediaQuery(
                    comparator = Dimensions.Width greaterThan 320.dp,
                    modifier = Modifier.padding(top = 50.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    if (name.isNotEmpty()) {
                        viewModel.onEvent(
                            CategorySelectedEvent.UpdateCategory(
                                id = id!!,
                                name = name,
                                description = description
                            )
                        )
                        isError = false
                    } else {
                        isError = true
                        viewModel.onEvent(
                            CategorySelectedEvent.UpdateCategory(
                                id = id!!,
                                name = defaultName,
                                description = description
                            )
                        )
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
                        text = "Название категории",
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
            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                    viewModel.onEvent(
                        CategorySelectedEvent.UpdateCategory(
                            id = id!!,
                            name = name,
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
                            textFieldAlpha = 1F
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
                        text = "Описание категории",
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
            LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
                items(
                    state.products.size,
                    { index -> state.products[index].hashCode() }) { index ->
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart) {
                                scope.launch {
                                    recentlyDeletedProduct = state.products[index]
                                    delay(100)
                                    viewModel.onEvent(CategorySelectedEvent.DeleteProduct(state.products[index]))
                                    snackbarHostState.showSnackbar("Товар удален.", "Вернуть")
                                }
                            }
                            true
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        modifier = Modifier
                            .clip(RoundedCornerShape(17.dp))
                            .animateItemPlacement(),
                        background = {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.app_trash_box_icon),
                                    contentDescription = "Trash box icon",
                                    modifier = Modifier
                                        .size(50.dp)
                                        .padding(5.dp)
                                        .alpha(dismissState.progress.fraction * 1.8F),
                                    tint = Color.Red
                                )
                            }
                        },
                        dismissContent = {
                            MyProductsListItem(
                                elementName = state.products[index].name,
                                elementDescription = state.products[index].description,
                                modifier = Modifier
                                    .animateContentSize()
                                    .width(300.dp)
                                    .clickable {
                                        val jsonProduct =
                                            Gson().toJson(state.products[index])
                                        navController.navigate(
                                            Screen.ProductSelectedScreen.route + "/$jsonProduct"
                                        )
                                    }
                            )
                        },
                        directions = setOf(DismissDirection.EndToStart)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        FloatingActionButton(
            onClick = { setShowDialog(true) },
            modifier = Modifier
                .constrainAs(createRef()) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .padding(end = 25.dp, bottom = 75.dp),
            backgroundColor = MaterialTheme.colors.primaryVariant
        ) {
            Icon(
                painter = painterResource(R.drawable.app_cross),
                contentDescription = "Cross",
                tint = MaterialTheme.colors.secondaryVariant
            )
        }

        SnackbarHost(
            modifier = Modifier
                .constrainAs(createRef()) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .padding(bottom = 50.dp),
            hostState = snackbarHostState,
            snackbar = { snackbarData: SnackbarData ->
                Row(
                    modifier = Modifier
                        .width(350.dp)
                        .height(60.dp)
                        .clip(RoundedCornerShape(17.dp))
                        .background(
                            if (isSystemInDarkTheme()) SoftPurple
                            else Grey
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = snackbarData.message,
                        modifier = Modifier.padding(start = 15.dp),
                        color = Color.White,
                        fontFamily = Comfortaa,
                        fontWeight = FontWeight.Normal
                    )
                    Button(
                        onClick = {
                            viewModel.onEvent(
                                CategorySelectedEvent.AddProduct(
                                    recentlyDeletedProduct!!
                                )
                            )
                            snackbarHostState.currentSnackbarData!!.dismiss()
                        },
                        modifier = Modifier.padding(end = 15.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.surface
                        )
                    ) {
                        Text(
                            text = snackbarData.actionLabel!!,
                            color = Color.White,
                            fontFamily = Comfortaa,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        )
    }
}