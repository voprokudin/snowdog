package dog.snow.androidrecruittest.domain.interactor

import dog.snow.androidrecruittest.domain.interactor.base.VPSingleUseCase
import dog.snow.androidrecruittest.domain.repository.VPListItemsLocalRepository
import dog.snow.androidrecruittest.presentation.view.list.model.VPListItem
import io.reactivex.Single
import javax.inject.Inject

class VPSaveListItemsUseCase
@Inject constructor(
    private val listItemsLocalRepository: VPListItemsLocalRepository
) : VPSingleUseCase<List<Long>, List<VPListItem>>() {

    override fun buildUseCaseSingle(params: List<VPListItem>?): Single<List<Long>> {
        params ?: return Single.error(IllegalStateException("list items must be provided"))

        return listItemsLocalRepository.addListItems(params)
    }
}