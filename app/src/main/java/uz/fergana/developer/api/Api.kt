package uz.fergana.developer.api

import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import uz.fergana.developer.model.CategoryModel
import uz.fergana.developer.model.RegionModel
import uz.fergana.developer.model.UserModel
import uz.fergana.developer.model.WorkerModel
import uz.fergana.developer.model.response.BaseResponse

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

    @FormUrlEncoded
    @POST("user/reg")
    fun registration(
        @Field("login") str: String?,
        @Field("password") str2: String?,
        @Field("user_type") str3: String?,
        @Field("region_id") i: Int,
        @Field("fullname") str4: String?,
        @Field("location_lat") d: Double,
        @Field("location_lon") d2: Double,
        @Field("experience") i2: Int,
        @Field("category_id") i3: Int,
        @Field("image") str5: String?,
        @Field("comment") str6: String?
    ): Observable<BaseResponse<UserModel>>
}

