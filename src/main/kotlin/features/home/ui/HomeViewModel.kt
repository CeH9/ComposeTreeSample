package features.home.ui

import androidx.compose.runtime.snapshots.SnapshotStateList
import features.home.ui.models.NodeUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel {

    data class State(
        val root: NodeUiModel = NodeUiModel("Root"),
        val editor: EditorState = EditorState(),
        val selectedNode: SelectedNode? = null
    ) {
//        override fun equals(other: Any?) = this === other
    }

    data class EditorState(
        val isVisible: Boolean = false,
        val initialText: String = "",
        val input: String = initialText,
    )

    data class SelectedNode(
        val node: NodeUiModel,
        val parentList: SnapshotStateList<NodeUiModel>? = null
    )

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state


    fun onEditNodeClicked(node: NodeUiModel, parentList: SnapshotStateList<NodeUiModel>?) {
        val oldState = _state.value

        _state.value = oldState.copy(
            editor = oldState.editor.copy(
                isVisible = true,
                initialText = node.name,
                input = node.name,
            ),
            selectedNode = SelectedNode(node, parentList)
        )
    }

    fun onEditorSaveClicked() {
        val oldState = _state.value
        val node = oldState.selectedNode!!.node
        val list = oldState.selectedNode.parentList
        val input = oldState.editor.input

        val newNode = node.copy(name = input)
        val isRoot = list == null

        if (isRoot) {
            _state.value = oldState.copy(
                root = newNode,
                editor = oldState.editor.copy(isVisible = false),
                selectedNode = null,
            )
        } else {
            val index = list!!.indexOf(node)
            list[index] = newNode

            _state.value = oldState.copy(
                editor = oldState.editor.copy(isVisible = false),
                selectedNode = null,
            )
        }
    }

    fun onEditorCancelClicked() {
        val oldState = _state.value

        _state.value = oldState.copy(
            editor = oldState.editor.copy(isVisible = false),
            selectedNode = null,
        )
    }

    fun onEditorInputChange(value: String) {
        val oldState = _state.value

        _state.value = oldState.copy(
            editor = oldState.editor.copy(input = value)
        )
    }
}