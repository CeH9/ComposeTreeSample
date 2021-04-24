package features.home.ui.models

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import features.home.data.models.NodeModel

class NodeUiModel(
    val name: String,
    val children: SnapshotStateList<NodeUiModel> = mutableStateListOf()
) {
    override fun equals(other: Any?) = this === other
    // ignore hashcode

    fun copy(
        name: String = this.name,
        children: SnapshotStateList<NodeUiModel> = this.children,
    ) = NodeUiModel(
        name,
        children
    )

    fun toDomainModel(): NodeModel = NodeModel(
        name = name,
        children = children.map {
            it.toDomainModel()
        }
    )
}