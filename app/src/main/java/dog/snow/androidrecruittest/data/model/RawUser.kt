package dog.snow.androidrecruittest.data.model

data class RawUser(
    val id: Long,
    val name: String,
    val username: String,
    val email: String,
    val address: RawAddress,
    val phone: String,
    val website: String,
    val company: RawCompany
) {
    data class RawAddress(
        val street: String,
        val suite: String,
        val city: String,
        val zipcode: String,
        val geo: RawGeo
    ) {
        data class RawGeo(val lat: String, val lng: String)
    }
    data class RawCompany(
        val name: String,
        val catchPhrase: String,
        val bs: String
    )
}