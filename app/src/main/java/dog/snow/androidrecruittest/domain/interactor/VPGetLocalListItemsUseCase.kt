package dog.snow.androidrecruittest.domain.interactor

import dog.snow.androidrecruittest.domain.interactor.base.VPSingleUseCase
import dog.snow.androidrecruittest.domain.repository.VPListItemsLocalRepository
import dog.snow.androidrecruittest.presentation.view.list.model.VPListItem
import io.reactivex.Single
import javax.inject.Inject

class VPGetLocalListItemsUseCase
@Inject constructor(
    private val listItemsLocalRepository: VPListItemsLocalRepository
) : VPSingleUseCase<List<VPListItem>, Unit>() {

    override fun buildUseCaseSingle(params: Unit?): Single<List<VPListItem>> {
        return listItemsLocalRepository.getAllListItems()
    }
}