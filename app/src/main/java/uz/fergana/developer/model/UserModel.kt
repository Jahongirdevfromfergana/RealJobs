package uz.fergana.developer.model

data class UserModel(
    val id: Int,
    val category_name: String,
    val comment: String,
    val fullname: String,
    val image: String,
    val login: String,
    val region_name: String,
    val user_type: String,
    val location_lat: Double,
    val location_lon: Double,
    val experience: Int,
)