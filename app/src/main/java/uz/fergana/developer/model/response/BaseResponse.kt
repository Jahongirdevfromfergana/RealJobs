package uz.fergana.developer.model.response

data class BaseResponse<T>(
    val error: Boolean,
    val message: String?,
    val data: T
)