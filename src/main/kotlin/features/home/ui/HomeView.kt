package features.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.CompositionKeys
import features.home.ui.components.Editor
import features.home.ui.components.Node

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView(viewModel: HomeViewModel) {
    println("Visit HomeView")

    val state: HomeViewModel.State by viewModel.state.collectAsState()
    //val sheetState = rememberModalBottomSheetState()

    CompositionLocalProvider(CompositionKeys.ViewModel provides viewModel) {
        ModalBottomSheetLayout(
            sheetState = ModalBottomSheetState(state.editor.toModalSheetState()),
            sheetContent = { BottomContent(state) },
            content = { MainContent(state) }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MainContent(state: HomeViewModel.State) {
    println("Visit MainContent")
    val viewModel = CompositionKeys.ViewModel.current

    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier.padding(8.dp)
    ) {
        Button(
            onClick = { viewModel.onDoneClicked() },
        ) {
            Text("Job's Done!")
        }
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Node(
                item = state.root,
                onEdit = { viewModel.onEditNodeClicked(state.root, null) },
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BottomContent(state: HomeViewModel.State) {
    println("Visit BottomContent")
    Editor(state.editor)
}


// ============================== Utils ===============================
@OptIn(ExperimentalMaterialApi::class)
fun HomeViewModel.EditorState.toModalSheetState() = if (isVisible) {
    ModalBottomSheetValue.Expanded
} else {
    ModalBottomSheetValue.Hidden
}