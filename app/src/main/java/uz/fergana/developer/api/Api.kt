package uz.fergana.developer.api

import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import uz.fergana.developer.model.CategoryModel
import uz.fergana.developer.model.RegionModel
import uz.fergana.developer.model.UserModel
import uz.fergana.developer.model.WorkerModel
import uz.fergana.developer.model.response.BaseResponse
import java.io.File

interface Api {
    @GET("categories")
    fun getCategories(): Observable<List<CategoryModel>>

    @GET("regions")
    fun getRegions(): Observable<List<RegionModel>>

    @GET("workers")
    fun getWorkers(): Observable<List<WorkerModel>>

    @FormUrlEncoded
    @POST("user/login")
    fun login(
        @Field("login") str: String?,
        @Field("password") str2: String?
    ): Observable<BaseResponse<UserModel>>

    @POST("user/reg")
    fun registration(
        @Body body: RequestBody
    ): Observable<BaseResponse<UserModel>>
}

