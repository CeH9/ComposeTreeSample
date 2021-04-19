package features.home.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.CompositionKeys
import features.home.ui.HomeViewModel

@Composable
fun Editor(
    state: HomeViewModel.EditorState
) {
    val viewModel = CompositionKeys.ViewModel.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        content = {
            TextField(
                state.input,
                label = { Text("Name") },
                onValueChange = { viewModel.onEditorInputChange(it) }
            )
            Row {
                Text(
                    text = "Cancel",
                    modifier = Modifier
                        .clickable { viewModel.onEditorCancelClicked() }
                        .padding(8.dp)
                )
                Spacer(Modifier.size(8.dp))
                Text(
                    text = "Save",
                    modifier = Modifier
                        .clickable { viewModel.onEditorSaveClicked() }
                        .padding(8.dp)
                )
            }
        }
    )
}