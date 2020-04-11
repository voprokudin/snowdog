package dog.snow.androidrecruittest

class Constants {

    interface BundleArgs {
        companion object {

            const val LIST_ITEM = "listItem"
            const val TRANSITION = "transition"
        }
    }

    interface RoomDatabase {
        companion object {
            const val VERSION = 1
        }

        interface Data {
            companion object {
                const val ID = "id"
                const val USER_ID = "user_id"
                const val TITLE = "title"
                const val ALBUM_TITLE = "album_title"
                const val URL = "url"
                const val THUMBNAIL_URL = "thumbnail_url"
                const val USERNAME = "username"
                const val EMAIL = "email"
                const val PHONE = "phone"
            }
        }
    }
}