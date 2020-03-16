package dog.snow.androidrecruittest.domain.interactor

import dog.snow.androidrecruittest.data.model.VPRawUser
import dog.snow.androidrecruittest.data.rest.VPUserService
import dog.snow.androidrecruittest.domain.interactor.base.VPSingleUseCase
import dog.snow.androidrecruittest.presentation.view.list.model.VPDetail
import dog.snow.androidrecruittest.presentation.view.list.model.VPListItem
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class VPGetDetailsUseCase
@Inject constructor(
    private val userService: VPUserService
) : VPSingleUseCase<List<VPDetail>, List<VPListItem>>() {

    override fun buildUseCaseSingle(params: List<VPListItem>?): Single<List<VPDetail>> {
        params ?: return Single.error(IllegalStateException("list items must be provided"))

        return Single.zip(
                Single.just(params),
                getUsers(params),
                BiFunction<List<VPListItem>, List<VPRawUser>, List<VPDetail>> { listItems, users -> createDetails(listItems, users) }
        )
    }

    private fun createDetails(
        listItems: List<VPListItem>,
        users: List<VPRawUser>
    ): List<VPDetail> {
        val details = ArrayList<VPDetail>()
        for (listItem in listItems) {
            val user = users.find { it.id == listItem.userId }!!
            val detail = VPDetail(
                photoId = listItem.id,
                photoTitle = listItem.title,
                albumTitle = listItem.albumTitle,
                username = user.username,
                email = user.email,
                phone = user.phone,
                url = listItem.url
            )
            details.add(detail)
        }
        return details
    }

    private fun getUsers(listItems: List<VPListItem>) : Single<List<VPRawUser>> =
        getUniqueUsersIds(listItems)
            .toObservable()
            .flatMapIterable { userId -> userId }
            .flatMapSingle { userId -> getUser(userId) }
            .toList()

    private fun getUniqueUsersIds(listItems: List<VPListItem>) : Single<List<Int>> =
        Single.fromCallable { listItems }
            .toObservable()
            .flatMapIterable { photo -> photo }
            .map { it.userId }
            .distinct()
            .toList()

    private fun getUser(userId: Int) = userService.getUser(userId)
}