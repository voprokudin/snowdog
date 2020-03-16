package dog.snow.androidrecruittest.domain.interactor

import dog.snow.androidrecruittest.data.model.VPRawAlbum
import dog.snow.androidrecruittest.data.model.VPRawPhoto
import dog.snow.androidrecruittest.data.rest.VPAlbumService
import dog.snow.androidrecruittest.data.rest.VPPhotoService
import dog.snow.androidrecruittest.domain.interactor.base.VPSingleUseCase
import dog.snow.androidrecruittest.presentation.view.list.model.VPListItem
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class VPGetListItemsUseCase
@Inject constructor(
    private val photoService: VPPhotoService,
    private val albumService: VPAlbumService
) : VPSingleUseCase<List<VPListItem>, Unit>() {

    companion object {
        private const val PHOTO_LIMIT = 100
    }

    override fun buildUseCaseSingle(params: Unit?): Single<List<VPListItem>> =
        Single.zip(
            getPhotos(),
            getAlbums(),
            BiFunction<ArrayList<VPRawPhoto>, List<VPRawAlbum>, List<VPListItem>> { photos, albums -> createListItem(photos, albums) }
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
                userId = album.userId,
                title = photo.title,
                albumTitle = album.title,
                url = photo.url,
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
}