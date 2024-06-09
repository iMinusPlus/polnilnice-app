package view.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.dp


@Composable
@Preview
fun CustomTextField(state: MutableState<String>, placeholder: String) {
    TextField(
        value = state.value,
        onValueChange = { state.value = it },
        shape = RoundedCornerShape(12.dp),
        placeholder = { Text(placeholder) })
}
