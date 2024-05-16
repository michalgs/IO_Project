package com.example.io_project.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize


@Composable
fun DropDownPicker(
    modifier: Modifier = Modifier,
    argList: List<String>,
    label: String,
) {
    var selectedValue by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val expansionIcon =
        if (expanded) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown

    OutlinedTextField(
        value = selectedValue,
        label = { Text(text = label) },
        onValueChange = { selectedValue = it },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
        ),
        enabled = false,
        trailingIcon = {
            Icon(
                expansionIcon,
                contentDescription = null,
                modifier = Modifier.clickable { expanded = !expanded }
            )
        },
        modifier = Modifier
            .onGloballyPositioned { coordinates ->
                textFieldSize = coordinates.size.toSize()
            }
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier
            .width(with(LocalDensity.current){textFieldSize.width.toDp()})
    ) {
        argList.forEach { itemName ->
            DropdownMenuItem(
                text = { Text(text = itemName) },
                onClick = {
                    selectedValue = itemName
                    expanded = false
                }
            )
        }
    }


}
