package uz.fergana.developer.repository

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import uz.fergana.developer.model.CategoryModel
import uz.fergana.developer.model.RegionModel
import uz.fergana.developer.model.UserModel
import uz.fergana.developer.model.WorkerModel
import uz.fergana.developer.model.response.BaseResponse


class UserRepository : BaseRepository() {
    fun login(
        phone: String?,
        password: String?,
        progress: MutableLiveData<Boolean>,
        error: MutableLiveData<String>,
        success: MutableLiveData<UserModel>
    ) {
        compositeDisposable.add((api.login(phone, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progress.value = false }
            .doOnSubscribe { progress.value = true }
            .subscribeWith(object : DisposableObserver<BaseResponse<UserModel>>() {
                override fun onNext(t: BaseResponse<UserModel>) {
                    if (!t.error) {
                        success.value = t.data
                    } else {
                        error.value = t.message
                    }
                }

                override fun onError(e: Throwable) {
                    error.value = e.localizedMessage
                }

                override fun onComplete() {

                }

            }))
        )
    }

    fun registration(
        phone: String?,
        password: String?,
        userType: String?,
        regionId: Int,
        fullname: String?,
        locationLat: Double,
        locationLon: Double,
        experience: Int,
        category_id: Int,
        image: String?,
        comment: String?,
        progress: MutableLiveData<Boolean>,
        error: MutableLiveData<String>,
        success: MutableLiveData<UserModel>
    ) {
        compositeDisposable.add((api.registration(
            phone,
            password,
            userType,
            regionId,
            fullname,
            locationLat,
            locationLon,
            experience,
            category_id,
            image,
            comment
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progress.value = false }
            .doOnSubscribe { progress.value = true }
            .subscribeWith(object : DisposableObserver<BaseResponse<UserModel>>() {
                override fun onNext(t: BaseResponse<UserModel>) {
                    if (!t.error) {
                        success.value = t.data
                    } else {
                        error.value = t.message
                    }
                }

                override fun onError(e: Throwable) {
                    error.value = e.localizedMessage
                }

                override fun onComplete() {

                }

            }))
        )
    }

    fun getCategories(
        progress: MutableLiveData<Boolean>,
        error: MutableLiveData<String>,
        success: MutableLiveData<List<CategoryModel>>
    ) {
        compositeDisposable.add((api.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progress.value = false }
            .doOnSubscribe { progress.value = true }
            .subscribeWith(object : DisposableObserver<List<CategoryModel>>() {
                override fun onNext(t: List<CategoryModel>) {
                    success.value = t
                }

                override fun onError(e: Throwable) {
                    error.value = e.localizedMessage
                }

                override fun onComplete() {

                }

            }))
        )
    }

    fun getRegions(
        progress: MutableLiveData<Boolean>,
        error: MutableLiveData<String>,
        success: MutableLiveData<List<RegionModel>>
    ) {
        compositeDisposable.add((api.getRegions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progress.value = false }
            .doOnSubscribe { progress.value = true }
            .subscribeWith(object : DisposableObserver<List<RegionModel>>() {
                override fun onNext(t: List<RegionModel>) {
                    success.value = t
                }

                override fun onError(e: Throwable) {
                    error.value = e.localizedMessage
                }

                override fun onComplete() {

                }

            }))
        )
    }

    fun getWorkers(
        progress: MutableLiveData<Boolean>,
        error: MutableLiveData<String>,
        success: MutableLiveData<List<WorkerModel>>
    ) {
        compositeDisposable.add((api.getWorkers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progress.value = false }
            .doOnSubscribe { progress.value = true }
            .subscribeWith(object : DisposableObserver<List<WorkerModel>>() {
                override fun onNext(t: List<WorkerModel>) {
                    success.value = t
                }

                override fun onError(e: Throwable) {
                    error.value = e.localizedMessage
                }

                override fun onComplete() {

                }

            }))
        )
    }
}
