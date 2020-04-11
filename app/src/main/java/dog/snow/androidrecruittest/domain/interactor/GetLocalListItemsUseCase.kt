package dog.snow.androidrecruittest.domain.interactor

import dog.snow.androidrecruittest.domain.interactor.base.SingleUseCase
import dog.snow.androidrecruittest.domain.repository.ListItemsLocalRepository
import dog.snow.androidrecruittest.presentation.view.list.model.ListItem
import io.reactivex.Single
import javax.inject.Inject

class GetLocalListItemsUseCase
@Inject constructor(
    private val listItemsLocalRepository: ListItemsLocalRepository
) : SingleUseCase<List<ListItem>, Unit>() {

    override fun buildUseCaseSingle(params: Unit?): Single<List<ListItem>> {
        return listItemsLocalRepository.getAllListItems()
    }
}