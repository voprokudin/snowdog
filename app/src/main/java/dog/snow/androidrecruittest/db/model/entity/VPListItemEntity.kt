package dog.snow.androidrecruittest.db.model.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dog.snow.androidrecruittest.C

@Entity
data class VPListItemEntity(
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = C.RoomDatabase.Data.ID) val id: Long,
    @ColumnInfo(name = C.RoomDatabase.Data.USER_ID) val userId: Long? = null,
    @ColumnInfo(name = C.RoomDatabase.Data.TITLE) val title: String? = null,
    @ColumnInfo(name = C.RoomDatabase.Data.ALBUM_TITLE) val albumTitle: String? = null,
    @ColumnInfo(name = C.RoomDatabase.Data.USERNAME) val username: String? = null,
    @ColumnInfo(name = C.RoomDatabase.Data.EMAIL) val email: String? = null,
    @ColumnInfo(name = C.RoomDatabase.Data.PHONE) val phone: String? = null,
    @ColumnInfo(name = C.RoomDatabase.Data.URL) val url: String? = null,
    @ColumnInfo(name = C.RoomDatabase.Data.THUMBNAIL_URL) val thumbnailUrl: String? = null
)