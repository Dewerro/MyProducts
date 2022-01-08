package com.dewerro.myproductsapp.my_products.presentation.categories_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dewerro.myproductsapp.my_products.domain.model.Category
import com.dewerro.myproductsapp.my_products.presentation.categories_list.CategoriesListEvent
import com.dewerro.myproductsapp.my_products.presentation.categories_list.CategoriesListViewModel
import com.dewerro.myproductsapp.ui.theme.Comfortaa

@Composable
fun CategoriesDialog(
    showDialog: Boolean,
    setShowDialog: (Boolean) -> Unit,
    viewModel: CategoriesListViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }

    var isError by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { setShowDialog(false) },
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colors.secondary.copy(alpha = 0.7F),
                    shape = RoundedCornerShape(17.dp)
                ),
            shape = RoundedCornerShape(17.dp),
            buttons = {
                val focusManager = LocalFocusManager.current
                Column(
                    modifier = Modifier.background(MaterialTheme.colors.background)
                ) {
                    Text(
                        text = "Добавить категорию",
                        modifier = Modifier.padding(start = 15.dp, top = 15.dp),
                        fontFamily = Comfortaa,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colors.secondary
                    )
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                            isError = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
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
                                text = "Название",
                                color = MaterialTheme.colors.secondary,
                                fontFamily = Comfortaa,
                                fontWeight = FontWeight.Normal
                            )
                        },
                        shape = RoundedCornerShape(11.dp),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                        }),
                    )
                    Text(
                        text = if (isError) "Название не может быть пустым" else "",
                        modifier = Modifier.padding(start = 15.dp),
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.background),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { setShowDialog(false) },
                        modifier = Modifier.padding(bottom = 10.dp, end = 10.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                    ) {
                        Text(
                            text = "нет",
                            color = MaterialTheme.colors.secondary,
                            textAlign = TextAlign.Center,
                            fontFamily = Comfortaa,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    Button(
                        onClick = {
                            if (name.isNotEmpty()) {
                                viewModel.onEvent(
                                    CategoriesListEvent.AddCategory(
                                        Category(null, name, "")
                                    )
                                )
                                setShowDialog(false)
                            } else {
                                isError = true
                            }
                        },
                        modifier = Modifier.padding(bottom = 10.dp, end = 10.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                    ) {
                        Text(
                            text = "да",
                            color = MaterialTheme.colors.secondary,
                            textAlign = TextAlign.Center,
                            fontFamily = Comfortaa,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        )
    }
}