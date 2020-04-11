package dog.snow.androidrecruittest.domain.repository

import dog.snow.androidrecruittest.presentation.view.list.model.ListItem
import io.reactivex.Single

interface ListItemsLocalRepository {

    fun addListItems(listItems: List<ListItem>): Single<List<Long>>

    fun getAllListItems(): Single<List<ListItem>>
}