package dog.snow.androidrecruittest.data.model

data class RawPhoto(
    val id: Long,
    val albumId: Long,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)