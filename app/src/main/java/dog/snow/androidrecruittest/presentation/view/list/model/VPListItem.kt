package dog.snow.androidrecruittest.presentation.view.list.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VPListItem(
    val id: Long,
    val userId: Long,
    val title: String,
    val albumTitle: String,
    val username: String,
    val email: String,
    val phone: String,
    val url: String,
    val thumbnailUrl: String
) : Parcelable