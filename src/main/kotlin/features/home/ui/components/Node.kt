package features.home.ui.components

import features.home.ui.models.NodeUiModel
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.CompositionKeys

@Composable
fun Node(
    item: NodeUiModel,
    onRemove: ((NodeUiModel) -> Unit)? = null,
    onEdit: () -> Unit,
) {
    println("Visit Node ${item.hashCode()}")
    // Item
    Column(
        modifier = Modifier
            .border(BorderStroke(1.dp, Color.White))
            .padding(8.dp)
    ) {
        NodeContent(
            item,
            onRemove = onRemove,
            onEdit = onEdit,
            onAdd = {
                val element = NodeUiModel("Node")
                println("---------------------------")
                println("Add: ${element.hashCode()}")
                item.children.add(item.children.size, element)
            },
        )
        Spacer(Modifier.size(6.dp))
        NodeChildren(item)
    }
}

@Composable
private fun NodeContent(
    item: NodeUiModel,
    onRemove: ((NodeUiModel) -> Unit)?,
    onAdd: () -> Unit,
    onEdit: () -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = item.name,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Spacer(Modifier.size(8.dp))
        Text(
            text = item.hashCode().toString(),
            fontSize = 13.sp,
            color = Color(0xFF888888),
            modifier = Modifier.align(Alignment.CenterVertically),
        )
        if (onRemove != null) {
            Spacer(Modifier.size(6.dp))
            RemoveButton(onRemove, item)
        }
    }
    Spacer(Modifier.size(6.dp))
    AddButton(onAdd)
    Spacer(Modifier.size(6.dp))
    EditButton(onEdit)
}

@Composable
private fun AddButton(onAdd: () -> Unit) {
    Button(
        onClick = { onAdd.invoke() }
    ) {
        Text(text = "Add Node")
    }
}

@Composable
private fun EditButton(onEdit: () -> Unit) {
    Button(
        onClick = { onEdit.invoke() }
    ) {
        Text(text = "Edit")
    }
}

@Composable
private fun RemoveButton(
    onRemove: ((NodeUiModel) -> Unit)?,
    item: NodeUiModel
) {
    Icon(
        imageVector = Icons.Default.Clear,
        contentDescription = "Remove Node",
        tint = Color.Red,
        modifier = Modifier
            .size(32.dp)
            .clickable {
                println("Remove: ${item.hashCode()}")
                onRemove?.invoke(item)
            }
    )
}

@Composable
private fun NodeChildren(item: NodeUiModel) {
    val viewModel = CompositionKeys.ViewModel.current

    Box(modifier = Modifier.padding(start = 16.dp).fillMaxWidth()) {
        Column {
            for (child in item.children) {
                key(child.hashCode()) {
                    Node(
                        item = child,
                        onRemove = { item.children.remove(it) },
                        onEdit = { viewModel.onEditNodeClicked(child, item.children) },
                    )
                }
            }
        }
    }
}