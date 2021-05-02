package features.home.ui.models

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import features.home.data.models.NodeModel

data class NodeUiModel(
    val name: String,
    val children: SnapshotStateList<NodeUiModel> = mutableStateListOf()
) {
    override fun equals(other: Any?) = this === other
    override fun hashCode() = System.identityHashCode(this)

    fun toDomainModel(): NodeModel = NodeModel(
        name = name,
        children = children.map {
            it.toDomainModel()
        }
    )
}