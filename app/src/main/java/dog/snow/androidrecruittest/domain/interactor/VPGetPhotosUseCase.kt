package dog.snow.androidrecruittest.domain.interactor

import dog.snow.androidrecruittest.data.model.VPRawUser
import dog.snow.androidrecruittest.data.rest.VPAlbumService
import dog.snow.androidrecruittest.data.rest.VPPhotoService
import dog.snow.androidrecruittest.data.rest.VPUserService
import dog.snow.androidrecruittest.domain.interactor.base.VPSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class VPGetPhotosUseCase
@Inject constructor(
    private val photoService: VPPhotoService,
    private val albumService: VPAlbumService,
    private val userService: VPUserService
) : VPSingleUseCase<List<VPRawUser>, Unit>() {

    companion object {
        private const val PHOTO_LIMIT = 100
    }

    override fun buildUseCaseSingle(params: Unit?): Single<List<VPRawUser>> = getUsers()

    private fun getUniqueAlbumsIds() : Single<List<Int>> =
        getPhotos()
            .toObservable()
            .flatMapIterable { photo -> photo }
            .map { it.albumId }
            .distinct()
            .toList()

    private fun getUniqueUsersIds() : Single<List<Int>> =
        getUniqueAlbumsIds()
            .toObservable()
            .flatMapIterable { albumId -> albumId }
            .flatMapSingle { albumId ->  getAlbum(albumId)}
            .map { it.userId }
            .distinct()
            .toList()

    private fun getUsers() : Single<List<VPRawUser>> =
        getUniqueUsersIds()
            .toObservable()
            .flatMapIterable { userId -> userId }
            .flatMapSingle { userId -> getUser(userId) }
            .toList()

    private fun getPhotos() = photoService.getPhotos(PHOTO_LIMIT)

    private fun getAlbum(albumId: Int) = albumService.getAlbum(albumId)

    private fun getUser(userId: Int) = userService.getUser(userId)
}