package dog.snow.androidrecruittest.data.mapper

import dog.snow.androidrecruittest.db.model.entity.VPListItemEntity
import dog.snow.androidrecruittest.presentation.view.list.model.VPListItem
import javax.inject.Inject

class VPListItemEntityMapper
@Inject constructor() {

    fun transformToData(listItems: List<VPListItem>): List<VPListItemEntity> = listItems.map {
        transform(it)
    }

    fun transformToDomain(listItemsEntity: List<VPListItemEntity>): List<VPListItem> = listItemsEntity.map {
        transform(it)
    }

    private fun transform(listItem: VPListItem): VPListItemEntity = listItem.let {
        VPListItemEntity(
            id = it.id,
            userId = it.userId,
            title = it.title,
            albumTitle = it.albumTitle,
            username = it.username,
            email = it.email,
            phone = it.phone,
            url = it.url,
            thumbnailUrl = it.thumbnailUrl
        )
    }

    private fun transform(listItemEntity: VPListItemEntity): VPListItem = listItemEntity.let {
        VPListItem(
            id = it.id,
            userId = it.userId,
            title = it.title,
            albumTitle = it.albumTitle,
            username = it.username,
            email = it.email,
            phone = it.phone,
            url = it.url,
            thumbnailUrl = it.thumbnailUrl
        )
    }
}