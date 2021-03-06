package com.dewerro.myproductsapp.my_products.presentation.product_list

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dewerro.myproductsapp.R
import com.dewerro.myproductsapp.my_products.presentation.components.MyProductsListItem
import com.dewerro.myproductsapp.my_products.presentation.product_list.components.ProductsDialog
import com.dewerro.myproductsapp.my_products.presentation.util.Dimensions
import com.dewerro.myproductsapp.my_products.presentation.util.Screen
import com.dewerro.myproductsapp.my_products.presentation.util.greaterThan
import com.dewerro.myproductsapp.my_products.presentation.util.mediaQuery
import com.dewerro.myproductsapp.ui.theme.*
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun ProductsListScreen(
    navController: NavHostController,
    viewModel: ProductsListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    val scope = rememberCoroutineScope()

    val isSystemInDarkTheme = isSystemInDarkTheme()

    // Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    var recentlyDeletedProduct by remember { mutableStateOf(state.recentlyDeletedProduct) }

    // Dialog
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    ProductsDialog(showDialog, setShowDialog, "")

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, bottom = 50.dp)
    ) {
        Canvas(
            modifier = Modifier
                .constrainAs(createRef()) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            onDraw = {
                drawCircle(
                    color = if (isSystemInDarkTheme) DarkPurple else LightBlue,
                    radius = 90.dp.toPx(),
                    alpha = if (isSystemInDarkTheme) 1F else 0.3F
                )
            }
        )
        Canvas(
            modifier = Modifier
                .constrainAs(createRef()) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top, margin = 130.dp)
                },
            onDraw = {
                drawCircle(
                    color = if (isSystemInDarkTheme) DarkPurple else LightPink,
                    radius = 90.dp.toPx(),
                    alpha = if (isSystemInDarkTheme) 1F else 0.8F
                )
            }
        )
        Canvas(
            modifier = Modifier
                .constrainAs(createRef()) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                },
            onDraw = {
                drawCircle(
                    color = if (isSystemInDarkTheme) DarkPurple else LightBlue,
                    radius = 115.dp.toPx(),
                    alpha = if (isSystemInDarkTheme) 1F else 0.3F
                )
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
            Row {
                Icon(
                    painter = painterResource(R.drawable.app_products_list_products_icon),
                    contentDescription = "Shopping cart icon",
                    modifier = Modifier
                        .size(42.dp)
                        .padding(end = 5.dp),
                    tint = MaterialTheme.colors.secondary
                )
                Text(
                    text = "????????????",
                    color = MaterialTheme.colors.secondary,
                    fontFamily = Comfortaa,
                    fontWeight = FontWeight.Normal,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )
            }
            LazyColumn {
                items(state.products.size,
                    { index -> state.products[index].hashCode() }) { index ->
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart) {
                                scope.launch {
                                    recentlyDeletedProduct = state.products[index]
                                    delay(100)
                                    viewModel.onEvent(ProductsListEvent.DeleteProduct(state.products[index]))
                                    snackbarHostState.showSnackbar("?????????? ????????????.", "??????????????")
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
                                        val jsonProduct = Gson().toJson(state.products[index])
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
                            if (isSystemInDarkTheme) SoftPurple
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
                                ProductsListEvent.AddProduct(
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