package com.dewerro.myproductsapp.my_products.presentation.select_option

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.dewerro.myproductsapp.R
import com.dewerro.myproductsapp.my_products.presentation.util.Dimensions
import com.dewerro.myproductsapp.my_products.presentation.util.Screen
import com.dewerro.myproductsapp.my_products.presentation.util.greaterThan
import com.dewerro.myproductsapp.my_products.presentation.util.mediaQuery
import com.dewerro.myproductsapp.ui.theme.*

@Composable
fun SelectOptionScreen(
    navController: NavHostController
) {
    val isSystemInDarkTheme = isSystemInDarkTheme()

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
            horizontalAlignment = CenterHorizontally
        ) {
            Column {
                Row(
                    modifier = Modifier.align(CenterHorizontally),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.app_select_option_screen_shopping_bag_icon),
                        contentDescription = "Shopping bag icon",
                        modifier = Modifier
                            .size(42.dp)
                            .padding(end = 5.dp),
                        tint = MaterialTheme.colors.secondary
                    )
                    Text(
                        text = "МоиТовары",
                        color = MaterialTheme.colors.secondary,
                        fontFamily = Comfortaa,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp
                    )
                }
                Text(
                    text = "Все возможности управления бизнесом",
                    color = MaterialTheme.colors.secondary,
                    fontFamily = Comfortaa,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    fontSize = 17.sp
                )
                Button(
                    onClick = { navController.navigate(Screen.CategoriesListScreen.route) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (isSystemInDarkTheme) SoftPurple else LightBlue
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .width(300.dp)
                        .height(95.dp)
                        .align(CenterHorizontally)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.app_select_option_screen_categories_icon),
                            contentDescription = "Add category icon",
                            modifier = Modifier.size(25.dp),
                            tint = MaterialTheme.colors.secondary
                        )
                        Column(
                            modifier = Modifier.padding(start = 2.dp)
                        ) {
                            Text(
                                text = "Добавить категорию",
                                color = MaterialTheme.colors.secondary,
                                fontFamily = Comfortaa,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,
                                fontSize = 15.sp,
                            )
                            Text(
                                text = "Возможность добавления и удаления категорий",
                                color = MaterialTheme.colors.secondary,
                                fontFamily = Comfortaa,
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.Normal,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
                Button(
                    onClick = { navController.navigate(Screen.ProductsListScreen.route) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (isSystemInDarkTheme) SoftPurple else LightPink
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .width(300.dp)
                        .height(95.dp)
                        .align(CenterHorizontally)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.app_select_option_screen_products_icon),
                            contentDescription = "Add products icon",
                            modifier = Modifier.size(25.dp),
                            tint = MaterialTheme.colors.secondary
                        )
                        Column(
                            modifier = Modifier.padding(start = 2.dp)
                        ) {
                            Text(
                                text = "Добавить товар",
                                color = MaterialTheme.colors.secondary,
                                fontFamily = Comfortaa,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,
                                fontSize = 15.sp,
                            )
                            Text(
                                text = "Возможность добавления и удаления товаров",
                                color = MaterialTheme.colors.secondary,
                                fontFamily = Comfortaa,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Start,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
                Image(
                    painter = painterResource(
                        if (isSystemInDarkTheme) R.drawable.app_select_option_screen_box_with_tools_dark
                        else R.drawable.app_select_option_screen_box_with_tools_light
                    ),
                    contentDescription = "Image in bottom",
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .align(CenterHorizontally)
                )
            }
        }
    }
}