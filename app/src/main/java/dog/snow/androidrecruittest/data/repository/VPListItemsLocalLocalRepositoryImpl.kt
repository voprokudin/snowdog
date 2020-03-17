package dog.snow.androidrecruittest.data.repository

import dog.snow.androidrecruittest.data.mapper.VPListItemEntityMapper
import dog.snow.androidrecruittest.db.VPRoomDatabase
import dog.snow.androidrecruittest.domain.repository.VPListItemsLocalRepository
import dog.snow.androidrecruittest.presentation.view.list.model.VPListItem
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VPListItemsLocalLocalRepositoryImpl
@Inject constructor(
    private val database: VPRoomDatabase,
    private val listItemEntityMapper: VPListItemEntityMapper
) : VPListItemsLocalRepository {

    override fun addListItems(listItems: List<VPListItem>): Single<List<Long>> {
        val listItemEntities = listItemEntityMapper.transformToData(listItems)
        return database
            .listItemDao()
            .insert(listItemEntities)
    }

    override fun getAllListItems(): Single<List<VPListItem>> {
        return database
            .listItemDao()
            .getAll()
            .map { listItemEntityMapper.transformToDomain(it) }
    }
}