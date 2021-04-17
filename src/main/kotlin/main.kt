import androidx.compose.desktop.Window
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun main() = Window(
    title = "Compose for Desktop",
    size = IntSize(600, 900),
//    location = IntOffset(1700, 300),
//    centered = false,
) {

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .background(Color(0xff333333))
                    .padding(16.dp)
            ) {
                val root by remember { mutableStateOf(NodeModel("Root")) }
                Node(root)
            }
        }
    }
}

class NodeModel(val name: String) {
    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return this === other
    }
}

@Composable
fun Node(item: NodeModel, onRemove: ((NodeModel) -> Unit)? = null) {
    val children = remember { mutableStateListOf<NodeModel>() }

    // Item
    Column(
        modifier = Modifier
            .border(BorderStroke(1.dp, Color.White))
            .padding(8.dp)
    ) {
        NodeContent(
            item,
            onRemove = onRemove,
            onAdd = {
                val element = NodeModel("Node")
                println("Add: ${element.hashCode()}")
                children.add(children.size, element)
            }
        )
        Spacer(Modifier.size(6.dp))
        NodeChildren(children)
    }
}

@Composable
private fun NodeContent(
    item: NodeModel,
    onRemove: ((NodeModel) -> Unit)?,
    onAdd: () -> Unit,
) {
    Row {
        Text(
            text = "${item.name} ${item.hashCode()}",
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        if (onRemove != null) {
            Spacer(Modifier.size(6.dp))
            RemoveButton(onRemove, item)
        }
    }
    Spacer(Modifier.size(6.dp))
    AddButton(onAdd)
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
private fun RemoveButton(
    onRemove: ((NodeModel) -> Unit)?,
    item: NodeModel
) {
    Text(
        text = "X",
        color = Color.Red,
        fontSize = 30.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .clickable {
                println("Remove: ${item.hashCode()}")
                onRemove?.invoke(item)
            }
    )
}

@Composable
private fun NodeChildren(children: SnapshotStateList<NodeModel>) {
    Box(modifier = Modifier.padding(start = 16.dp).fillMaxWidth()) {
        Column {
            for (child in children) {
                key(child.hashCode()) {
                    Node(child, onRemove = { children.remove(it) })
                }
            }
        }
    }
}