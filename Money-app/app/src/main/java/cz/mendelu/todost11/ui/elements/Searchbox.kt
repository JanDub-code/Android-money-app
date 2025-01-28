package cz.mendelu.todost11.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
/*
@Composable
fun SearchableDropDown(
    items: List<String>,
    onItemSelected: (String) -> Unit
) {
    var searchText by remember { mutableStateOf(TextFieldValue()) }
    var expanded by remember { mutableStateOf(false) }

    Box {
        TextField(
            value = searchText.text,
            onValueChange = {
                searchText = TextFieldValue(it)
                expanded = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.White)
                .border(1.dp, Color.Black, MaterialTheme.shapes.small)
                .padding(horizontal = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    expanded = false
                }
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            val filteredItems = items.filter {
                it.contains(searchText.text, ignoreCase = true)
            }

            if (filteredItems.isEmpty()) {
                DropdownMenuItem(onClick = {}) {
                    Text("No matching items found")
                }
            } else {
                filteredItems.forEach { item ->
                    DropdownMenuItem(onClick = {
                        onItemSelected(item)
                        searchText = TextFieldValue()
                        expanded = false
                    }) {
                        Text(item)
                    }
                }
            }
        }
    }
}

@Composable
fun SearchableDropDownExample() {
    val items = listOf("Apple", "Banana", "Orange", "Pineapple", "Grapes")

    var selectedItem by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        SearchableDropDown(
            items = items,
            onItemSelected = { selectedItem = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Selected Item: $selectedItem")
    }
}*/