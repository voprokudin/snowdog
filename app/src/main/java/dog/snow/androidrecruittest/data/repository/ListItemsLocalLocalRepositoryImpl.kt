package dog.snow.androidrecruittest.data.repository

import dog.snow.androidrecruittest.data.mapper.ListItemEntityMapper
import dog.snow.androidrecruittest.db.RoomDatabase
import dog.snow.androidrecruittest.domain.repository.ListItemsLocalRepository
import dog.snow.androidrecruittest.presentation.view.list.model.ListItem
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListItemsLocalLocalRepositoryImpl
@Inject constructor(
    private val database: RoomDatabase,
    private val listItemEntityMapper: ListItemEntityMapper
) : ListItemsLocalRepository {

    override fun addListItems(listItems: List<ListItem>): Single<List<Long>> {
        val listItemEntities = listItemEntityMapper.transformToData(listItems)
        return database
            .listItemDao()
            .insert(listItemEntities)
    }

    override fun getAllListItems(): Single<List<ListItem>> {
        return database
            .listItemDao()
            .getAll()
            .map { listItemEntityMapper.transformToDomain(it) }
    }
}