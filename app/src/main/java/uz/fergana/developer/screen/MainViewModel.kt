package uz.fergana.developer.screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.fergana.developer.model.CategoryModel
import uz.fergana.developer.model.RegionModel
import uz.fergana.developer.model.UserModel
import uz.fergana.developer.model.WorkerModel
import uz.fergana.developer.repository.UserRepository
import java.io.File
import java.io.InputStream


class MainViewModel : ViewModel() {
    val img = MutableLiveData<WorkerModel>()
    val categoriesData = MutableLiveData<List<CategoryModel>>()
    val error = MutableLiveData<String>()
    val loginData = MutableLiveData<UserModel>()
    val progress = MutableLiveData<Boolean>()
    val regionsData = MutableLiveData<List<RegionModel>>()
    val registrationData = MutableLiveData<UserModel>()
    val repository: UserRepository = UserRepository()
    val tempProgress = MutableLiveData<Boolean>()
    val workersData = MutableLiveData<List<WorkerModel>>()

    fun login(phone: String, password: String) {
        repository.login(phone, password, progress, error, loginData)
    }

    fun registration(
        phone: String,
        password: String?,
        userType: String?,
        regionId: Int,
        fullname: String?,
        locationLat: Double,
        locationLon: Double,
        experience: Int,
        category_id: Int,
        inputStream: InputStream,
        comment: String?
    ) {
        val file = File.createTempFile("avatar", ".tmp")
        file.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }

        repository.registration(

            phone,
            password,
            userType,
            regionId,
            fullname,
            locationLat,
            locationLon,
            experience,
            category_id,
            file,
            comment,
            progress,
            error,
            registrationData
        )
    }

    fun getFilters() {
        repository.getCategories(progress, error, categoriesData)
        repository.getRegions(tempProgress, error, regionsData)
    }

    fun getWorkers() {
        repository.getWorkers(progress, error, workersData)
    }

    fun uploadFile(inputStream: InputStream) {
        val file = File.createTempFile("image", ".jpg")
        file.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }
    }
}