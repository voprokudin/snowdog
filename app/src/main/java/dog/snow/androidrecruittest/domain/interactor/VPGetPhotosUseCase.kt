package dog.snow.androidrecruittest.domain.interactor

import dog.snow.androidrecruittest.data.model.VPRawAlbum
import dog.snow.androidrecruittest.data.model.VPRawPhoto
import dog.snow.androidrecruittest.data.rest.VPAlbumService
import dog.snow.androidrecruittest.data.rest.VPPhotoService
import dog.snow.androidrecruittest.data.rest.VPUserService
import dog.snow.androidrecruittest.domain.interactor.base.VPSingleUseCase
import dog.snow.androidrecruittest.presentation.view.list.model.VPListItem
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class VPGetPhotosUseCase
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
            BiFunction<ArrayList<VPRawPhoto>, List<VPRawAlbum>, List<VPListItem>> { photo, album -> createListItem(photo, album) }
        )

    private fun getPhotos() = photoService.getPhotos(PHOTO_LIMIT)

    private fun getAlbums(): Single<List<VPRawAlbum>> =
        getUniqueAlbumsIds()
            .toObservable()
            .flatMapIterable { albumId -> albumId }
            .flatMapSingle { albumId ->  getAlbum(albumId)}
            .toList()

    private fun getAlbum(albumId: Int) = albumService.getAlbum(albumId)

    private fun createListItem(
        photos: ArrayList<VPRawPhoto>,
        albums: List<VPRawAlbum>
    ): List<VPListItem> {
        val listItems = ArrayList<VPListItem>()
        for (photo in photos) {
            val album = albums.find { it.id == photo.albumId }!!
            val listItem = VPListItem(
                id = photo.id,
                title = photo.title,
                albumTitle = album.title,
                thumbnailUrl = photo.thumbnailUrl
            )
            listItems.add(listItem)
        }
        return listItems
    }

    private fun getUniqueAlbumsIds() : Single<List<Int>> =
        getPhotos()
            .toObservable()
            .flatMapIterable { photo -> photo }
            .map { it.albumId }
            .distinct()
            .toList()

//    private fun getUniqueUsersIds() : Single<List<Int>> =
//        getUniqueAlbumsIds()
//            .toObservable()
//            .flatMapIterable { albumId -> albumId }
//            .flatMapSingle { albumId ->  getAlbum(albumId)}
//            .map { it.userId }
//            .distinct()
//            .toList()
//
//    private fun getUsers() : Single<List<VPRawUser>> =
//        getUniqueUsersIds()
//            .toObservable()
//            .flatMapIterable { userId -> userId }
//            .flatMapSingle { userId -> getUser(userId) }
//            .toList()

//    private fun getUser(userId: Int) = userService.getUser(userId)
}