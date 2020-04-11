package dog.snow.androidrecruittest.domain.interactor

import dog.snow.androidrecruittest.domain.interactor.base.SingleUseCase
import dog.snow.androidrecruittest.domain.repository.ListItemsLocalRepository
import dog.snow.androidrecruittest.presentation.view.list.model.ListItem
import io.reactivex.Single
import javax.inject.Inject

class SaveListItemsUseCase
@Inject constructor(
    private val listItemsLocalRepository: ListItemsLocalRepository
) : SingleUseCase<List<Long>, List<ListItem>>() {

    override fun buildUseCaseSingle(params: List<ListItem>?): Single<List<Long>> {
        params ?: return Single.error(IllegalStateException("list items must be provided"))

        return listItemsLocalRepository.addListItems(params)
    }
}