package view.components.content

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun AddingStationContent() {
    var station = remember { mutableStateOf("") }
    var address = remember { mutableStateOf("") }

    //TODO
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(align = Alignment.Center)
        ) {
            Text("ADD CHARGING STATION")
            CustomTextField(station, "Enter station")
            Text("ADD ADDRESS")
            CustomTextField(address, "Enter address")
            //Todo obrazec
        }
    }
}

@Composable
@Preview
fun CustomTextField(state: MutableState<String>, placeholder: String) {
    TextField(
        value = state.value,
        onValueChange = { state.value = it },
        shape = RoundedCornerShape(12.dp),
        placeholder = { Text(placeholder) })
}
