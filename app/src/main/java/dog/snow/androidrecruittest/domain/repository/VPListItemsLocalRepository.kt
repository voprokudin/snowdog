package dog.snow.androidrecruittest.domain.repository

import dog.snow.androidrecruittest.presentation.view.list.model.VPListItem
import io.reactivex.Single

interface VPListItemsLocalRepository {

    fun addListItems(listItems: List<VPListItem>): Single<List<Long>>

    fun getAllListItems(): Single<List<VPListItem>>
}