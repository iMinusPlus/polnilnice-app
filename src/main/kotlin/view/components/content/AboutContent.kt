package view.components.content

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun AboutContent() {
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
            Text("ABOUT")
            Text("This go brooom broom.")
            Text(
                modifier = Modifier.width(300.dp).height(500.dp), text = "                                        \n" +
                        "                                        \n" +
                        "                .-======:               \n" +
                        "              -**+++++#++**:            \n" +
                        "           .:*#*******#****#+-:.        \n" +
                        "        -#%%%%%%%%%%%%%%%%%%%%%*-       \n" +
                        "      .=#%@%%@%%%%%%%%%%%%@%%@%%%*      \n" +
                        "      +%@@=.::#@%%%%%%%%@@-::-@@%*      \n" +
                        "         **-:-#:        .#+--=#.        \n" +
                        "          :-=-            :==:          \n" +
                        "                                        \n"
            )
        }
    }
}
