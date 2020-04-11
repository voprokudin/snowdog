package dog.snow.androidrecruittest.domain.interactor

import dog.snow.androidrecruittest.data.model.RawAlbum
import dog.snow.androidrecruittest.data.model.RawPhoto
import dog.snow.androidrecruittest.data.model.RawUser
import dog.snow.androidrecruittest.data.rest.AlbumService
import dog.snow.androidrecruittest.data.rest.PhotoService
import dog.snow.androidrecruittest.data.rest.UserService
import dog.snow.androidrecruittest.domain.interactor.base.BaseSingleUseCase
import dog.snow.androidrecruittest.presentation.view.list.model.ListItem
import io.reactivex.Single
import io.reactivex.functions.Function3
import javax.inject.Inject

class GetListItemsUseCase
@Inject constructor(
    private val photoService: PhotoService,
    private val albumService: AlbumService,
    private val userService: UserService
) : BaseSingleUseCase<List<ListItem>, Unit>() {

    companion object {
        private const val PHOTO_LIMIT = 100
    }

    override fun buildUseCaseSingle(params: Unit?): Single<List<ListItem>> =
        Single.zip(
            getPhotos(),
            getAlbums(),
            getUsers(),
            Function3<ArrayList<RawPhoto>, List<RawAlbum>, List<RawUser>, List<ListItem>> { photos, albums, users -> createListItem(photos, albums, users) }
        )

    private fun createListItem(
        photos: ArrayList<RawPhoto>,
        albums: List<RawAlbum>,
        users: List<RawUser>
    ): List<ListItem> {
        val listItems = ArrayList<ListItem>()
        for (photo in photos) {
            val album = albums.find { it.id == photo.albumId }!!
            val user = users.find { it.id == album.userId}!!
            val listItem = ListItem(
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

    private fun getAlbums(): Single<List<RawAlbum>> =
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

    private fun getUsers() : Single<List<RawUser>> =
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