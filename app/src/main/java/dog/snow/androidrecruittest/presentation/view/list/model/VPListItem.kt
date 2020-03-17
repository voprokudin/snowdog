package dog.snow.androidrecruittest.presentation.view.list.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VPListItem(
    val id: Long,
    val userId: Long? = null,
    val title: String? = null,
    val albumTitle: String? = null,
    val username: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val url: String? = null,
    val thumbnailUrl: String? = null
) : Parcelable