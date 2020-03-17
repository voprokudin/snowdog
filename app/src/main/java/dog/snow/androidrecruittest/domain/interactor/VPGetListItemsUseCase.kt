package dog.snow.androidrecruittest.domain.interactor

import dog.snow.androidrecruittest.data.model.VPRawAlbum
import dog.snow.androidrecruittest.data.model.VPRawPhoto
import dog.snow.androidrecruittest.data.model.VPRawUser
import dog.snow.androidrecruittest.data.rest.VPAlbumService
import dog.snow.androidrecruittest.data.rest.VPPhotoService
import dog.snow.androidrecruittest.data.rest.VPUserService
import dog.snow.androidrecruittest.domain.interactor.base.VPSingleUseCase
import dog.snow.androidrecruittest.presentation.view.list.model.VPListItem
import io.reactivex.Single
import io.reactivex.functions.Function3
import javax.inject.Inject

class VPGetListItemsUseCase
@Inject constructor(
    private val photoService: VPPhotoService,
    private val albumService: VPAlbumService,
    private val userService: VPUserService
) : VPSingleUseCase<List<VPListItem>, Unit>() {

    companion object {
        private const val PHOTO_LIMIT = 100
    }

    override fun buildUseCaseSingle(params: Unit?): Single<List<VPListItem>> =
        Single.zip(
            getPhotos(),
            getAlbums(),
            getUsers(),
            Function3<ArrayList<VPRawPhoto>, List<VPRawAlbum>, List<VPRawUser>, List<VPListItem>> { photos, albums, users -> createListItem(photos, albums, users) }
        )

    private fun createListItem(
        photos: ArrayList<VPRawPhoto>,
        albums: List<VPRawAlbum>,
        users: List<VPRawUser>
    ): List<VPListItem> {
        val listItems = ArrayList<VPListItem>()
        for (photo in photos) {
            val album = albums.find { it.id == photo.albumId }!!
            val user = users.find { it.id == album.userId}!!
            val listItem = VPListItem(
                id = photo.id,
                userId = album.userId,
                title = photo.title,
                albumTitle = album.title,
                username = user.username,
                email = user.email,
                phone = user.phone,
                url = photo.url,
                thumbnailUrl = photo.thumbnailUrl
            )
            listItems.add(listItem)
        }
        return listItems
    }

    private fun getAlbums(): Single<List<VPRawAlbum>> =
        getUniqueAlbumsIds()
            .toObservable()
            .flatMapIterable { albumId -> albumId }
            .flatMapSingle { albumId ->  getAlbum(albumId)}
            .toList()

    private fun getUniqueAlbumsIds() : Single<List<Long>> =
        getPhotos()
            .toObservable()
            .flatMapIterable { photo -> photo }
            .map { it.albumId }
            .distinct()
            .toList()

    private fun getUsers() : Single<List<VPRawUser>> =
        getUniqueUsersIds()
            .toObservable()
            .flatMapIterable { userId -> userId }
            .flatMapSingle { userId -> getUser(userId) }
            .toList()

    private fun getUniqueUsersIds() : Single<List<Long>> =
        getAlbums()
            .toObservable()
            .flatMapIterable { albumId -> albumId }
            .map { it.userId }
            .distinct()
            .toList()

    private fun getPhotos() = photoService.getPhotos(PHOTO_LIMIT)

    private fun getAlbum(albumId: Long) = albumService.getAlbum(albumId)

    private fun getUser(userId: Long) = userService.getUser(userId)
}