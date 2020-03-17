package dog.snow.androidrecruittest

class C {

    interface BundleArgs {
        companion object {

            const val PRODUCT_CODE = "productCode"
        }
    }

    interface RoomDatabase {
        companion object {
            const val VERSION = 1
        }

        interface Data {
            companion object {
                const val TABLE_NAME = "list_items"
                const val ID = "id"
                const val USER_ID = "user_id"
                const val TITLE = "title"
                const val ALBUM_TITLE = "album_title"
                const val URL = "url"
                const val THUMBNAIL_URL = "thumbnail_url"
                const val PHOTO_TITLE = "photo_title"
                const val USERNAME = "username"
                const val EMAIL = "email"
                const val PHONE = "phone"
            }
        }
    }
}