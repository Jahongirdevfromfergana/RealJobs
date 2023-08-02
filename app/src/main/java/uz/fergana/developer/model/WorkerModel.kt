package uz.fergana.developer.model


data class WorkerModel(
    val id: Int,
    val user_type: String,
    val fullname: String,
    val login: String,
    val location_lat: Double,
    val location_lon: Double,
    val region_id: Int,
    val region_name: String,
    val experience: Int,
    val image:String,
    val category_name: String,
    val category_id: Int,
    val comment: String,
)