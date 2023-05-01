package uz.fergana.developer.model

data class CategoryModel(
    val id: Int,
    val title: String,
    var checked: Boolean = false,
)
